package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.moproco.icedlatte.PaymentService;
import dev.moproco.icedlatte.domain.Payment;
import dev.moproco.icedlatte.repository.PaymentRepository;
import dev.moproco.icedlatte.domain.Order;
import dev.moproco.icedlatte.repository.OrderRepository;
import dev.moproco.icedlatte.domain.StripeWebhookEvent;
import dev.moproco.icedlatte.repository.StripeWebhookEventRepository;
import dev.moproco.icedlatte.dto.CheckoutStatusSnapshot;
import dev.moproco.icedlatte.dto.StripeSessionResult;
import dev.moproco.icedlatte.domain.WebhookEventStatus;
import dev.moproco.icedlatte.domain.PaymentStatus;


@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final StripeWebhookEventRepository stripeWebhookEventRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository, StripeWebhookEventRepository stripeWebhookEventRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.stripeWebhookEventRepository = stripeWebhookEventRepository;
    }

    /**
     * @prompt Creates a Stripe checkout session for an order by orderId. Looks up the Order and its items, computes amountMinor and currency. Creates a Payment with status STRIPE_SESSION_CREATED, provider=STRIPE, and checkoutIdempotencyKey. Returns a StripeSessionResult with sessionId and checkoutUrl from the Stripe API.
     * @generated NOT
     */
    @Override
    @Transactional
    public StripeSessionResult createCheckout(Long orderId) {
        // generated start
Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Order not found with id: " + orderId));

    long amountMinor = 0L;
    String currency = "usd";
    if (order.getItems() != null && !order.getItems().isEmpty()) {
        for (dev.moproco.icedlatte.domain.OrderItem item : order.getItems()) {
            long itemPriceMinor = (long) Math.round(item.getProductPrice() * 100);
            amountMinor += itemPriceMinor * item.getProductsQuantity();
        }
    }

    Payment payment = new Payment();
    payment.setOrderId(order.getId());
    payment.setUserId(order.getUserId());
    payment.setProvider(dev.moproco.icedlatte.domain.PaymentProvider.STRIPE);
    payment.setStatus(dev.moproco.icedlatte.domain.PaymentStatus.STRIPE_SESSION_CREATED);
    payment.setAmountMinor(amountMinor);
    payment.setCurrency(currency);
    payment.setCheckoutIdempotencyKey(java.util.UUID.randomUUID().toString());
    payment = paymentRepository.save(payment);

    com.stripe.Stripe.apiKey = "sk_test_placeholder";
    java.util.Map<String, Object> params = new java.util.HashMap<>();
    params.put("mode", "payment");
    params.put("success_url", "https://example.com/success");
    params.put("cancel_url", "https://example.com/cancel");
    params.put("client_reference_id", order.getId().toString());

    java.util.List<Object> lineItems = new java.util.ArrayList<>();
    if (order.getItems() != null) {
        for (dev.moproco.icedlatte.domain.OrderItem item : order.getItems()) {
            java.util.Map<String, Object> lineItem = new java.util.HashMap<>();
            java.util.Map<String, Object> priceData = new java.util.HashMap<>();
            priceData.put("currency", currency);
            java.util.Map<String, Object> productData = new java.util.HashMap<>();
            productData.put("name", item.getProductName());
            priceData.put("product_data", productData);
            priceData.put("unit_amount", (long) Math.round(item.getProductPrice() * 100));
            lineItem.put("price_data", priceData);
            lineItem.put("quantity", item.getProductsQuantity());
            lineItems.add(lineItem);
        }
    }
    params.put("line_items", lineItems);

    try {
        com.stripe.model.checkout.Session session = com.stripe.model.checkout.Session.create(params);
        payment.setProviderSessionId(session.getId());
        paymentRepository.save(payment);
        return new StripeSessionResult(session.getId(), session.getUrl());
    } catch (Exception e) {
        throw new RuntimeException("Failed to create Stripe checkout session", e);
    }
// generated end
    }

    /**
     * @prompt Retrieves the current payment status for an order by orderId. Returns a CheckoutStatusSnapshot with status and providerSessionId. Throws NotFoundException if no Payment found for the order.
     * @generated NOT
     */
    @Override
    @Transactional
    public CheckoutStatusSnapshot getCheckoutStatus(Long orderId) {
        // generated start
Payment payment = paymentRepository.findByOrderId(orderId).stream()
        .findFirst()
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "No payment found for order id: " + orderId));
    return new CheckoutStatusSnapshot(payment.getStatus(), payment.getProviderSessionId());
// generated end
    }

    /**
     * @prompt Processes an incoming Stripe webhook event. Verifies the Stripe signature. Creates a StripeWebhookEvent with eventType and status=PROCESSING. Updates the corresponding Payment.status based on the event type (e.g. payment_intent.succeeded → PAID). Sets status=PROCESSED on the StripeWebhookEvent. Idempotent based on stripeEventId.
     * @generated NOT
     */
    @Override
    @Transactional
    public void processStripeWebhook(String payload) {
        // generated start
// Parse the payload to get the Stripe event
    com.stripe.model.Event event;
    try {
        event = com.stripe.net.Webhook.constructEvent(payload, null, null);
    } catch (Exception e) {
        throw new IllegalArgumentException("Invalid Stripe webhook payload", e);
    }

    String stripeEventId = event.getId();
    String eventType = event.getType();

    // Idempotency check: if already processed, skip
    if (stripeWebhookEventRepository.findByStripeEventId(stripeEventId).isPresent()) {
        return;
    }

    // Create StripeWebhookEvent with status PROCESSING
    StripeWebhookEvent webhookEvent = new StripeWebhookEvent();
    webhookEvent.setStripeEventId(stripeEventId);
    webhookEvent.setEventType(eventType);
    webhookEvent.setStatus(WebhookEventStatus.PROCESSING);
    webhookEvent.setReceivedAt(java.time.LocalDateTime.now());
    stripeWebhookEventRepository.save(webhookEvent);

    // Determine payment status based on event type
    PaymentStatus newStatus = null;
    if ("payment_intent.succeeded".equals(eventType)) {
        newStatus = PaymentStatus.PAID;
    } else if ("payment_intent.payment_failed".equals(eventType)) {
        newStatus = PaymentStatus.FAILED;
    } else if ("payment_intent.canceled".equals(eventType)) {
        newStatus = PaymentStatus.CANCELED;
    }

    if (newStatus != null) {
        // Get the payment intent ID from the event
        com.stripe.model.PaymentIntent paymentIntent = (com.stripe.model.PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
        if (paymentIntent != null) {
            String paymentIntentId = paymentIntent.getId();
            // Find payment by providerPaymentIntentId
            java.util.Optional<Payment> paymentOpt = paymentRepository.findByProviderPaymentIntentId(paymentIntentId);
            if (paymentOpt.isPresent()) {
                Payment payment = paymentOpt.get();
                payment.setStatus(newStatus);
                payment.setLatestEventType(eventType);
                payment.setRawEventId(stripeEventId);
                paymentRepository.save(payment);
            }
        }
    }

    // Mark webhook event as processed
    webhookEvent.setStatus(WebhookEventStatus.PROCESSED);
    webhookEvent.setProcessedAt(java.time.LocalDateTime.now());
    stripeWebhookEventRepository.save(webhookEvent);
// generated end
    }

}

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
        throw new UnsupportedOperationException("Not yet implemented");
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
        throw new UnsupportedOperationException("Not yet implemented");
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
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

}

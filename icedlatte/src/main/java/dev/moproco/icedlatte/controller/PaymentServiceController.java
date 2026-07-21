package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.PaymentService;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import dev.moproco.icedlatte.dto.CheckoutStatusSnapshot;
import dev.moproco.icedlatte.dto.StripeSessionResult;

@RestController
@RequestMapping("/api/v1/payment")
@Tag(name = "/api/v1/payment")
public class PaymentServiceController {

    private final PaymentService paymentService;

    public PaymentServiceController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Create Checkout", description = "Creates a Stripe checkout session for an order by orderId. Looks up the Order and its items, computes amountMinor and currency. Creates a Payment with status STRIPE_SESSION_CREATED, provider=STRIPE, and checkoutIdempotencyKey. Returns a StripeSessionResult with sessionId and checkoutUrl from the Stripe API.")
    public ResponseEntity<StripeSessionResult> createCheckout(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.createCheckout(orderId));
    }

    @GetMapping("/getCheckoutStatus/{orderId}")
    @Operation(summary = "Get Checkout Status", description = "Retrieves the current payment status for an order by orderId. Returns a CheckoutStatusSnapshot with status and providerSessionId. Throws NotFoundException if no Payment found for the order.")
    public ResponseEntity<CheckoutStatusSnapshot> getCheckoutStatus(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getCheckoutStatus(orderId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Process Stripe Webhook", description = "Processes an incoming Stripe webhook event. Verifies the Stripe signature. Creates a StripeWebhookEvent with eventType and status=PROCESSING. Updates the corresponding Payment.status based on the event type (e.g. payment_intent.succeeded → PAID). Sets status=PROCESSED on the StripeWebhookEvent. Idempotent based on stripeEventId.")
    public ResponseEntity<Void> processStripeWebhook(@PathVariable String payload) {
        paymentService.processStripeWebhook(payload);
        return ResponseEntity.noContent().build();
    }

}

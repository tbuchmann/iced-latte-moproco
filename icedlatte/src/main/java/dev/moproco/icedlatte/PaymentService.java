package dev.moproco.icedlatte;

import dev.moproco.icedlatte.dto.CheckoutStatusSnapshot;
import dev.moproco.icedlatte.dto.StripeSessionResult;

public interface PaymentService {

    StripeSessionResult createCheckout(Long orderId);
    CheckoutStatusSnapshot getCheckoutStatus(Long orderId);
    void processStripeWebhook(String payload);

}

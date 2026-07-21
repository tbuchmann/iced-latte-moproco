package dev.moproco.icedlatte.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "payment")
public class Payment extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "order_id")
    private Long orderId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private PaymentProvider provider;

    @Column(name = "provider_session_id")
    private String providerSessionId;

    @Column(name = "provider_payment_intent_id")
    private String providerPaymentIntentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @NotNull
    @Column(name = "amount_minor")
    private Long amountMinor;

    @NotNull
    @Column(name = "currency")
    private String currency;

    @Column(name = "raw_event_id")
    private String rawEventId;

    @Column(name = "latest_event_type")
    private String latestEventType;

    @Column(name = "checkout_idempotency_key")
    private String checkoutIdempotencyKey;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PaymentProvider getProvider() {
        return this.provider;
    }

    public void setProvider(PaymentProvider provider) {
        this.provider = provider;
    }

    public String getProviderSessionId() {
        return this.providerSessionId;
    }

    public void setProviderSessionId(String providerSessionId) {
        this.providerSessionId = providerSessionId;
    }

    public String getProviderPaymentIntentId() {
        return this.providerPaymentIntentId;
    }

    public void setProviderPaymentIntentId(String providerPaymentIntentId) {
        this.providerPaymentIntentId = providerPaymentIntentId;
    }

    public PaymentStatus getStatus() {
        return this.status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Long getAmountMinor() {
        return this.amountMinor;
    }

    public void setAmountMinor(Long amountMinor) {
        this.amountMinor = amountMinor;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRawEventId() {
        return this.rawEventId;
    }

    public void setRawEventId(String rawEventId) {
        this.rawEventId = rawEventId;
    }

    public String getLatestEventType() {
        return this.latestEventType;
    }

    public void setLatestEventType(String latestEventType) {
        this.latestEventType = latestEventType;
    }

    public String getCheckoutIdempotencyKey() {
        return this.checkoutIdempotencyKey;
    }

    public void setCheckoutIdempotencyKey(String checkoutIdempotencyKey) {
        this.checkoutIdempotencyKey = checkoutIdempotencyKey;
    }

}

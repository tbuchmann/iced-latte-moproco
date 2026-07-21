package dev.moproco.icedlatte.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "support_message_entity")
public class SupportMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "conversation_id")
    private Long conversationId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sender_type")
    private SupportMessageSenderType senderType;

    @Column(name = "sender_user_id")
    private Long senderUserId;

    @Column(name = "client_message_id")
    private String clientMessageId;

    @NotNull
    @Column(name = "body")
    private String body;

    @NotNull
    @Column(name = "normalized_body")
    private String normalizedBody;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private SupportMessageDeliveryStatus deliveryStatus;

    @NotNull
    @Column(name = "operator_inspection_required")
    private Boolean operatorInspectionRequired;

    @NotNull
    @Column(name = "visible_to_customer")
    private Boolean visibleToCustomer;

    @Column(name = "telegram_update_id")
    private Long telegramUpdateId;

    @Column(name = "telegram_message_id")
    private Long telegramMessageId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "conversation_id", insertable = false, updatable = false)
    private SupportConversationEntity conversation;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConversationId() {
        return this.conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public SupportMessageSenderType getSenderType() {
        return this.senderType;
    }

    public void setSenderType(SupportMessageSenderType senderType) {
        this.senderType = senderType;
    }

    public Long getSenderUserId() {
        return this.senderUserId;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getClientMessageId() {
        return this.clientMessageId;
    }

    public void setClientMessageId(String clientMessageId) {
        this.clientMessageId = clientMessageId;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNormalizedBody() {
        return this.normalizedBody;
    }

    public void setNormalizedBody(String normalizedBody) {
        this.normalizedBody = normalizedBody;
    }

    public SupportMessageDeliveryStatus getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public void setDeliveryStatus(SupportMessageDeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Boolean getOperatorInspectionRequired() {
        return this.operatorInspectionRequired;
    }

    public void setOperatorInspectionRequired(Boolean operatorInspectionRequired) {
        this.operatorInspectionRequired = operatorInspectionRequired;
    }

    public Boolean getVisibleToCustomer() {
        return this.visibleToCustomer;
    }

    public void setVisibleToCustomer(Boolean visibleToCustomer) {
        this.visibleToCustomer = visibleToCustomer;
    }

    public Long getTelegramUpdateId() {
        return this.telegramUpdateId;
    }

    public void setTelegramUpdateId(Long telegramUpdateId) {
        this.telegramUpdateId = telegramUpdateId;
    }

    public Long getTelegramMessageId() {
        return this.telegramMessageId;
    }

    public void setTelegramMessageId(Long telegramMessageId) {
        this.telegramMessageId = telegramMessageId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public SupportConversationEntity getConversation() {
        return this.conversation;
    }

    public void setConversation(SupportConversationEntity conversation) {
        this.conversation = conversation;
    }

}

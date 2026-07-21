package dev.moproco.icedlatte.domain;

import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "support_conversation_entity")
public class SupportConversationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;

    @Column(name = "telegram_message_thread_id")
    private Long telegramMessageThreadId;

    @Column(name = "telegram_fallback_message_id")
    private Long telegramFallbackMessageId;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupportMessageEntity> messages;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastMessageAt() {
        return this.lastMessageAt;
    }

    public void setLastMessageAt(LocalDateTime lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
    }

    public Long getTelegramMessageThreadId() {
        return this.telegramMessageThreadId;
    }

    public void setTelegramMessageThreadId(Long telegramMessageThreadId) {
        this.telegramMessageThreadId = telegramMessageThreadId;
    }

    public Long getTelegramFallbackMessageId() {
        return this.telegramFallbackMessageId;
    }

    public void setTelegramFallbackMessageId(Long telegramFallbackMessageId) {
        this.telegramFallbackMessageId = telegramFallbackMessageId;
    }

    public List<SupportMessageEntity> getMessages() {
        return this.messages;
    }

    public void setMessages(List<SupportMessageEntity> messages) {
        this.messages = messages;
    }

}

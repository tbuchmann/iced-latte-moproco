package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import dev.moproco.icedlatte.SupportChatService;
import dev.moproco.icedlatte.domain.SupportConversationEntity;
import dev.moproco.icedlatte.repository.SupportConversationEntityRepository;
import dev.moproco.icedlatte.domain.SupportMessageEntity;
import dev.moproco.icedlatte.repository.SupportMessageEntityRepository;
import dev.moproco.icedlatte.dto.SupportChatStatusSnapshot;
import dev.moproco.icedlatte.dto.SupportConversationSnapshot;
import dev.moproco.icedlatte.dto.SupportMessageSnapshot;


@Service
public class SupportChatServiceImpl implements SupportChatService {

    private final SupportConversationEntityRepository supportConversationEntityRepository;
    private final SupportMessageEntityRepository supportMessageEntityRepository;

    public SupportChatServiceImpl(SupportConversationEntityRepository supportConversationEntityRepository, SupportMessageEntityRepository supportMessageEntityRepository) {
        this.supportConversationEntityRepository = supportConversationEntityRepository;
        this.supportMessageEntityRepository = supportMessageEntityRepository;
    }

    /**
     * @prompt Retrieves the availability status of support chat for the user by userId. Returns a SupportChatStatusSnapshot with available=true and queuePosition if applicable.
     * @generated NOT
     */
    @Override
    @Transactional
    public SupportChatStatusSnapshot getSupportChatStatus(Long userId) {
        // generated start
// Check if the user has an existing conversation
    java.util.List<SupportConversationEntity> conversations = supportConversationEntityRepository.findByUserId(userId);
    if (!conversations.isEmpty()) {
        // User has a conversation, so support is available
        return new SupportChatStatusSnapshot(true, null);
    }
    // No conversation found; determine queue position based on pending conversations
    // Count all conversations (as a proxy for queue) - in a real system you'd filter by status
    long queuePosition = supportConversationEntityRepository.count() + 1;
    return new SupportChatStatusSnapshot(true, (int) queuePosition);
// generated end
    }

    /**
     * @prompt Retrieves the current support conversation for the user by userId. Returns a SupportConversationSnapshot with id, userId, createdAt, lastMessageAt. Returns null if no conversation exists.
     * @generated NOT
     */
    @Override
    @Transactional
    public SupportConversationSnapshot getCurrentSupportChatConversation(Long userId) {
        // generated start
java.util.List<SupportConversationEntity> conversations = supportConversationEntityRepository.findByUserId(userId);
    if (conversations.isEmpty()) {
        return null;
    }
    SupportConversationEntity conversation = conversations.get(0);
    return new SupportConversationSnapshot(conversation.getUserId(), conversation.getCreatedAt(), conversation.getLastMessageAt());
// generated end
    }

    /**
     * @prompt Creates a WebSocket connection ticket for real-time chat by userId. Returns a unique ticket string that can be used to authenticate the WebSocket connection.
     * @generated NOT
     */
    @Override
    @Transactional
    public String createSupportChatWebSocketTicket(Long userId) {
        // generated start
// Generate a unique ticket string using UUID
    String ticket = java.util.UUID.randomUUID().toString();
    // Store the ticket in the conversation entity for later validation
    java.util.List<SupportConversationEntity> conversations = supportConversationEntityRepository.findByUserId(userId);
    if (!conversations.isEmpty()) {
        SupportConversationEntity conversation = conversations.get(0);
        conversation.setTelegramMessageThreadId((long) ticket.hashCode());
        supportConversationEntityRepository.save(conversation);
    }
    return ticket;
// generated end
    }

    /**
     * @prompt Retrieves messages for a support conversation by conversationId. Returns a list of SupportMessageSnapshot with id, conversationId, senderType, body, deliveryStatus, createdAt. Sorted by createdAt ascending.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<SupportMessageSnapshot> getSupportChatMessages(Long conversationId) {
        // generated start
// Retrieve messages for the given conversationId, sorted by createdAt ascending
    List<SupportMessageEntity> messages = supportMessageEntityRepository.findByConversationId(conversationId);
    messages.sort((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt()));
    return messages.stream()
            .map(msg -> new SupportMessageSnapshot(
                    msg.getConversationId(),
                    msg.getSenderType(),
                    msg.getBody(),
                    msg.getDeliveryStatus(),
                    msg.getCreatedAt()))
            .collect(java.util.stream.Collectors.toList());
// generated end
    }

    /**
     * @prompt Sends a new message in a support conversation by conversationId. The 'conversationId' parameter is the conversation ID, 'message' is the message body. Creates a SupportMessageEntity with senderType=CUSTOMER, body=message, normalizedBody=message, deliveryStatus=PENDING. Updates the SupportConversationEntity.lastMessageAt. Returns a SupportMessageSnapshot with id, conversationId, senderType, body, deliveryStatus, createdAt.
     * @generated NOT
     */
    @Override
    @Transactional
    public SupportMessageSnapshot sendSupportChatMessage(Long conversationId, String message) {
        // generated start
SupportConversationEntity conversation = supportConversationEntityRepository.findById(conversationId)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND, "Conversation not found"));
    SupportMessageEntity messageEntity = new SupportMessageEntity();
    messageEntity.setConversationId(conversationId);
    messageEntity.setSenderType(dev.moproco.icedlatte.domain.SupportMessageSenderType.CUSTOMER);
    messageEntity.setBody(message);
    messageEntity.setNormalizedBody(message);
    messageEntity.setDeliveryStatus(dev.moproco.icedlatte.domain.SupportMessageDeliveryStatus.PENDING);
    messageEntity.setOperatorInspectionRequired(false);
    messageEntity.setVisibleToCustomer(true);
    messageEntity.setCreatedAt(java.time.LocalDateTime.now());
    messageEntity = supportMessageEntityRepository.save(messageEntity);
    conversation.setLastMessageAt(java.time.LocalDateTime.now());
    supportConversationEntityRepository.save(conversation);
    return new SupportMessageSnapshot(messageEntity.getConversationId(), messageEntity.getSenderType(), messageEntity.getBody(), messageEntity.getDeliveryStatus(), messageEntity.getCreatedAt());
// generated end
    }

}

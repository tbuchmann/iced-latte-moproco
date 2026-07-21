package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.SupportChatService;
import org.springframework.http.ResponseEntity;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import dev.moproco.icedlatte.dto.SupportChatStatusSnapshot;
import dev.moproco.icedlatte.dto.SupportConversationSnapshot;
import dev.moproco.icedlatte.dto.SupportMessageSnapshot;

@RestController
@RequestMapping("/api/v1/support-chat")
@Tag(name = "/api/v1/support-chat")
public class SupportChatServiceController {

    private final SupportChatService supportChatService;

    public SupportChatServiceController(SupportChatService supportChatService) {
        this.supportChatService = supportChatService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Support Chat Status", description = "Retrieves the availability status of support chat for the user by userId. Returns a SupportChatStatusSnapshot with available=true and queuePosition if applicable.")
    public ResponseEntity<SupportChatStatusSnapshot> getSupportChatStatus(@PathVariable Long userId) {
        return ResponseEntity.ok(supportChatService.getSupportChatStatus(userId));
    }

    @GetMapping("/getCurrentSupportChatConversation/{userId}")
    @Operation(summary = "Get Current Support Chat Conversation", description = "Retrieves the current support conversation for the user by userId. Returns a SupportConversationSnapshot with id, userId, createdAt, lastMessageAt. Returns null if no conversation exists.")
    public ResponseEntity<SupportConversationSnapshot> getCurrentSupportChatConversation(@PathVariable Long userId) {
        return ResponseEntity.ok(supportChatService.getCurrentSupportChatConversation(userId));
    }

    @GetMapping("/createSupportChatWebSocketTicket/{userId}")
    @Operation(summary = "Create Support Chat Web Socket Ticket", description = "Creates a WebSocket connection ticket for real-time chat by userId. Returns a unique ticket string that can be used to authenticate the WebSocket connection.")
    public ResponseEntity<String> createSupportChatWebSocketTicket(@PathVariable Long userId) {
        return ResponseEntity.ok(supportChatService.createSupportChatWebSocketTicket(userId));
    }

    @GetMapping("/getSupportChatMessages/{conversationId}")
    @Operation(summary = "Get Support Chat Messages", description = "Retrieves messages for a support conversation by conversationId. Returns a list of SupportMessageSnapshot with id, conversationId, senderType, body, deliveryStatus, createdAt. Sorted by createdAt ascending.")
    public List<SupportMessageSnapshot> getSupportChatMessages(@PathVariable Long conversationId) {
        return supportChatService.getSupportChatMessages(conversationId);
    }

    @GetMapping("/sendSupportChatMessage")
    @Operation(summary = "Send Support Chat Message")
    public SupportMessageSnapshot sendSupportChatMessage(Long conversationId, String message) {
        return supportChatService.sendSupportChatMessage(conversationId, message);
    }

}

package dev.moproco.icedlatte;

import java.util.List;
import dev.moproco.icedlatte.dto.SupportChatStatusSnapshot;
import dev.moproco.icedlatte.dto.SupportConversationSnapshot;
import dev.moproco.icedlatte.dto.SupportMessageSnapshot;

public interface SupportChatService {

    SupportChatStatusSnapshot getSupportChatStatus(Long userId);
    SupportConversationSnapshot getCurrentSupportChatConversation(Long userId);
    String createSupportChatWebSocketTicket(Long userId);
    List<SupportMessageSnapshot> getSupportChatMessages(Long conversationId);
    SupportMessageSnapshot sendSupportChatMessage(Long conversationId, String message);

}

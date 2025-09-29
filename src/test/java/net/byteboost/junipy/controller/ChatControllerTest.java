package net.byteboost.junipy.controller;

import net.byteboost.junipy.dto.ChatMessage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChatControllerTest {

    @Test
    void handleChat_shouldEchoMessage() {
        ChatController controller = new ChatController();
        ChatMessage input = new ChatMessage("user", "Hello!");
        ChatMessage result = controller.handleChat(input);

        assertNotNull(result);
        assertEquals("assistant", result.getRole());
        assertEquals("Echo: Hello!", result.getMessage());
    }
}
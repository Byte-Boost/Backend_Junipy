package net.byteboost.junipy.websocket;

import org.junit.jupiter.api.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;

class ChatWebSocketHandlerTest {
    @Test
    void testHandleTextMessageEcho() throws Exception {
        ChatWebSocketHandler handler = new ChatWebSocketHandler();
        WebSocketSession session = mock(WebSocketSession.class);
        TextMessage inputMessage = new TextMessage("Hello");

        handler.handleTextMessage(session, inputMessage);

        verify(session, times(1)).sendMessage(new TextMessage("Echo: Hello"));
    }
}

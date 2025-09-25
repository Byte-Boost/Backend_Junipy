package net.byteboost.junipy.websocket;

import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Echo the received message
        session.sendMessage(new TextMessage("Echo: " + message.getPayload()));
        // After the AI service api is ready change this to call the AI service and return the response
    }
}
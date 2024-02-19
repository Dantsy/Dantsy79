package chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Root implements Serializable {
    @JsonProperty("chat_sessions")
    private List<ChatSession> chatSessions;

    public List<ChatSession> getChatSessions() {
        return chatSessions;
    }

    public void setChatSessions(List<ChatSession> chatSessions) {
        this.chatSessions = chatSessions;
    }
}
package Chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class ChatSession {
    @JsonProperty("chat_id")
    private int chatId;
    @JsonProperty("chat_identifier")
    private String chatIdentifier;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("is_deleted")
    private int isDeleted;
    @JsonProperty("members")
    private List<Member> members;
    @JsonProperty("messages")
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @JsonProperty("chat_identifier")
    public String getChatIdentifier() {
        return chatIdentifier;
    }

    @JsonProperty("members")
    public List<Member> getMembers() {
        return members; //
    }

    // getters and setters
}
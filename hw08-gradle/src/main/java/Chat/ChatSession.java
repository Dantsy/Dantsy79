package chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ChatSession implements Serializable {
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

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getChatIdentifier() {
        return chatIdentifier;
    }

    public void setChatIdentifier(String chatIdentifier) {
        this.chatIdentifier = chatIdentifier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
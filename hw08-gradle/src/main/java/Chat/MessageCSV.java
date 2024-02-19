package chat;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"belongNumber", "sendDate", "text"})
public class MessageCSV {
    private String belongNumber;
    private String sendDate;
    private String text;

    public MessageCSV(String belongNumber, String sendDate, String text) {
        this.belongNumber = belongNumber;
        this.sendDate = sendDate;
        this.text = text;
    }

    public String getBelongNumber() {
        return belongNumber;
    }

    public void setBelongNumber(String belongNumber) {
        this.belongNumber = belongNumber;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
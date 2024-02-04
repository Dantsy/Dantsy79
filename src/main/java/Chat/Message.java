package Chat;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Message {
    private int ROWID;
    @JsonProperty("attributedBody")
    private String attributedBody;
    @JsonProperty("belong_number")
    private String belongNumber;
    private long date;
    @JsonProperty("date_read")
    private long dateRead;
    private String guid;
    @JsonProperty("handle_id")
    private int handleId;
    @JsonProperty("has_dd_results")
    private int hasDdResults;
    @JsonProperty("is_deleted")
    private int isDeleted;
    @JsonProperty("is_from_me")
    private int isFromMe;
    @JsonProperty("send_date")
    private String sendDate;
    @JsonProperty("send_status")
    private int sendStatus;
    private String service;
    private String text;

    @JsonProperty("belong_number")
    public String getBelongNumber() {
        return belongNumber;
    }

    @JsonProperty("send_date")
    public String getSendDate() {
        return sendDate; //
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    // getters and setters
}

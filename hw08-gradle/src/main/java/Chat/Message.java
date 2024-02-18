package Chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Message implements Serializable {
    @JsonProperty("ROWID")
    private int rowId;
    private String attributedBody;
    @JsonProperty("belong_number")
    private String belongNumber;
    private long date;
    private long dateRead;
    private String guid;
    private int handleId;
    private int hasDdResults;
    private int isDeleted;
    private int isFromMe;
    @JsonProperty("send_date")
    private String sendDate;
    private int sendStatus;
    private String service;
    private String text;

    @JsonProperty("ROWID")
    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    @JsonProperty("attributedBody")
    public String getAttributedBody() {
        return attributedBody;
    }

    public void setAttributedBody(String attributedBody) {
        this.attributedBody = attributedBody;
    }

    @JsonProperty("belong_number")
    public String getBelongNumber() {
        return belongNumber;
    }

    public void setBelongNumber(String belongNumber) {
        this.belongNumber = belongNumber;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @JsonProperty("date_read")
    public long getDateRead() {
        return dateRead;
    }

    public void setDateRead(long dateRead) {
        this.dateRead = dateRead;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @JsonProperty("handle_id")
    public int getHandleId() {
        return handleId;
    }

    public void setHandleId(int handleId) {
        this.handleId = handleId;
    }

    @JsonProperty("has_dd_results")
    public int getHasDdResults() {
        return hasDdResults;
    }

    public void setHasDdResults(int hasDdResults) {
        this.hasDdResults = hasDdResults;
    }

    @JsonProperty("is_deleted")
    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    @JsonProperty("is_from_me")
    public int getIsFromMe() {
        return isFromMe;
    }

    public void setIsFromMe(int isFromMe) {
        this.isFromMe = isFromMe;
    }

    @JsonProperty("send_date")
    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    @JsonProperty("send_status")
    public int getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
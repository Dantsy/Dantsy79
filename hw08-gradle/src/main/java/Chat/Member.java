package Chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Member implements Serializable {
    private String first;
    private int handleId;
    private String imagePath;
    private String last;
    private String middle;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String service;
    private String thumbPath;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    @JsonProperty("handle_id")
    public int getHandleId() {
        return handleId;
    }

    public void setHandleId(int handleId) {
        this.handleId = handleId;
    }

    @JsonProperty("image_path")
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    @JsonProperty("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @JsonProperty("thumb_path")
    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }
}
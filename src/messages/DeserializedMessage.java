package messages;

public class DeserializedMessage {
    public String senderName;
    public String message;
    public DeserializedMessage(String senderName, String message) {
        this.senderName = senderName;
        this.message = message;
    }
}

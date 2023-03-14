/*
Author: Filip Hellgren

The DeserializedMessage class responsible for containing information about messages having been deserialized after being sent through the socket.
 */


package messages;

public class DeserializedMessage {
    public String senderName;
    public String message;
    public Boolean isServer;
    public DeserializedMessage(String senderName, String message, boolean isServer) {
        this.senderName = senderName;
        this.message = message;
        this.isServer = isServer;
    }
}

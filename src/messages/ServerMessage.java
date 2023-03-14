/*
Author: Filip Hellgren

The ServerMessage class responsible for describing messages being sent from a server.
 */


package messages;

public class ServerMessage extends Message {
    public ServerMessage(String message) {
        this.message = message;
    }
    @Override
    public String formatMessage() {
        // Formats the message as a single string in the format of a ServerMessage in order to send it and its related information through the socket.
        return "Server" + Message.SERIALIZATION_SEPARATOR + message;
    }
}

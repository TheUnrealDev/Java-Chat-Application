/*
Author: Filip Hellgren

The ClientMessage class responsible for describing messages being sent from a client.
 */

package messages;

import mainPack.ConnectionHandler;

public class ClientMessage extends Message {
    private final ConnectionHandler sender;

    public ClientMessage(ConnectionHandler sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    @Override
    public String formatMessage() {
        // Formats the message as a single string in the format of a ClientMessage in order to send it and its related information through the socket.
        String screenName = this.sender.getScreenName();
        return "Client" + Message.SERIALIZATION_SEPARATOR +
                screenName.length() +
                Message.SERIALIZATION_SEPARATOR + screenName +
                Message.SERIALIZATION_SEPARATOR + this.message;
    }

    public String getSenderName() {
        return sender.getScreenName();
    }
}

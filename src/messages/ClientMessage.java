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

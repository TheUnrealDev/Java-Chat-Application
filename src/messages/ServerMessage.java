package messages;

public class ServerMessage extends Message {
    public ServerMessage(String message) {
        this.message = message;
    }

    @Override
    public String formatMessage() {
        return "Server" + Message.SERIALIZATION_SEPARATOR + message;
    }
}

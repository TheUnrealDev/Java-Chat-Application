package messages;

public abstract class Message {
    public static final String SERIALIZATION_SEPARATOR = ":";
    protected String message;

    public Message() {}

    public abstract String formatMessage();

    public String getMessage() {
        return this.message;
    }
}

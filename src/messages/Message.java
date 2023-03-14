/*
Author: Filip Hellgren

The abstract Message class responsible for describing common attributes of the messages being sent from both the server and the clients.
 */

package messages;

public abstract class Message {
    public static final String SERIALIZATION_SEPARATOR = ":"; //The character or set of character to be used to separate the different pieces of information contained in an encoded message.
    protected String message;

    public Message() {}

    public abstract String formatMessage();

    public String getMessage() {
        return this.message;
    }
}

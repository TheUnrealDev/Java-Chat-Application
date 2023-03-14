/*
Author: Filip Hellgren

The MessageDeserializer class responsible for deserializing messages having been sent as a String through a socket.
 */


package messages;

public class MessageDeserializer {

    public MessageDeserializer() {}

    private static DeserializedMessage deserializeServerMessage(String message) {
        //Separates the messages content from the encoded String that is based on a ServerMessage.
        //Creates and returns a new DeserializedMessage containing the message information.
        String[] messageData = message.split(Message.SERIALIZATION_SEPARATOR, 2);

        String content = messageData[1];
        return new DeserializedMessage("Server", content, true);
    }

    private static DeserializedMessage deserializeClientMessage(String message) {
        //Separates the messages content and sender name from the encoded String that is based on a ClientMessage and creates a new DeserializedMessage with this information.
        String[] messageData = message.split(Message.SERIALIZATION_SEPARATOR, 3); //Gets only the part of the message containing the sender name and message content.

        int nameLength = Integer.parseInt(messageData[1]); //Gets the length of the username, this is used in order to skip past the name when searching the string for the separation character that separates the name from the message content.
        //This is done to allow the separation character to appear in the screen name without disturbing the deserialization.

        int nameLengthNumDigits = (int)(Math.log10(nameLength) + 1); //Gets the number of characters being occupied by the number containing the length of the screen name.

        int nameStartIndex = 6 + 2 * Message.SERIALIZATION_SEPARATOR.length() + nameLengthNumDigits; //Calculates the index at which the screen name starts.

        String name = message.substring(nameStartIndex, nameStartIndex + nameLength); //Gets the name from the encoded string.
        String content = message.substring(nameStartIndex + nameLength + Message.SERIALIZATION_SEPARATOR.length()); //Gets the message content from the encoded string.

        return new DeserializedMessage(name, content, false);
    }
    public static DeserializedMessage deserializeMessage(String message) {
        DeserializedMessage deserializedMessage;

        if(message.startsWith("Server" + Message.SERIALIZATION_SEPARATOR)) {
            //The message is a Server message and should be deserialized accordingly.
            deserializedMessage = deserializeServerMessage(message);
        } else {
            //The message is a Client message and should be deserialized accordingly.
            deserializedMessage = deserializeClientMessage(message);
        }
        return deserializedMessage;
    }
}

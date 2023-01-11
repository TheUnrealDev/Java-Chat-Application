package messages;

public class MessageDeserializer {

    public MessageDeserializer() {}

    private static DeserializedMessage deserializeServerMessage(String message) {
        String[] messageData = message.split(Message.SERIALIZATION_SEPARATOR, 2);

        String content = messageData[1];
        return new DeserializedMessage("Server", content);
    }

    private static DeserializedMessage deserializeClientMessage(String message) {
        String[] messageData = message.split(Message.SERIALIZATION_SEPARATOR, 3);

        int nameLength = Integer.parseInt(messageData[1]);
        int nameLengthNumDigits = (int)(Math.log10(nameLength) + 1);

        int nameStartIndex = 6 + 2 * Message.SERIALIZATION_SEPARATOR.length() + nameLengthNumDigits;

        String name = message.substring(nameStartIndex, nameStartIndex + nameLength);
        String content = message.substring(nameStartIndex + nameLength + Message.SERIALIZATION_SEPARATOR.length());

        return new DeserializedMessage(name, content);
    }
    public static DeserializedMessage deserializeMessage(String message) {

        System.out.println(message);
        //ERROR HANDLING
        DeserializedMessage deserializedMessage;

        if(message.startsWith("Server" + Message.SERIALIZATION_SEPARATOR)) {
            deserializedMessage = deserializeServerMessage(message);
        } else {
            deserializedMessage = deserializeClientMessage(message);
        }
        return deserializedMessage;
    }
}

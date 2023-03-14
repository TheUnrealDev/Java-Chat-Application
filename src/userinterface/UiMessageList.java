/*
Author: Filip Hellgren

The UiMessageList class responsible for containing the messages that are displayed to the user.
 */

package userInterface;

import messages.DeserializedMessage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UiMessageList extends JPanel {
    private final ArrayList<UiMessage> recentMessages = new ArrayList<>();
    private JScrollPane scrollableArea;
    private Box messageArea;
    public UiMessageList() {
        CreateMessageList();
    }
    private void CreateMessageList() {
        //Initializes the message list by setting its size and creating the scrollable area that will contain the messages.

        messageArea = new Box(BoxLayout.PAGE_AXIS);
        messageArea.setPreferredSize(new Dimension(1350, 650));
        messageArea.setBackground(Color.darkGray);

        scrollableArea = new JScrollPane(messageArea);
        scrollableArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableArea.getVerticalScrollBar().setUnitIncrement(16);
        scrollableArea.setPreferredSize(new Dimension(1350, 650));
        this.add(scrollableArea);
    }

    public void addMessage(DeserializedMessage deserializedMessage) {
        //Adds a message to the list.
        if (deserializedMessage.message.isEmpty()) {
            return;
        }
        UiMessage message = new UiMessage(deserializedMessage); // Create a new UiMessage that displays the information from the deserialized message.

        int messageAreaSizeY = 650 + ((recentMessages.size()) * 75); //Increases the size of the window based on message size,
        // this increases how far the chat can be scrolled vertically.
        messageArea.setPreferredSize(new Dimension(1350, messageAreaSizeY));
        messageArea.add(message, SwingConstants.CENTER, 0);
        scrollableArea.revalidate(); //Updates the scrollable area to display the newly added message.

        recentMessages.add(message);
    }
}

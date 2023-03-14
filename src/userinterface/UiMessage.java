/*
Author: Filip Hellgren

The UiMessage class responsible for displaying the content and other information related to a message.
 */

package userInterface;

import messages.DeserializedMessage;

import javax.swing.*;
import java.awt.*;

public class UiMessage extends JPanel {
    final String senderName;
    final String message;
    final boolean isServerMessage;
    final Font senderFont = new Font("Calibri", Font.BOLD, 24);
    final Font messageFont = new Font("Calibri", Font.PLAIN, 20);
    final Color serverMessageColor = Color.red;
    JPanel messageContainer;
    JLabel senderLabel;
    JLabel messageLabel;

    public UiMessage(DeserializedMessage deserializedMessage) {
        // Creates a new UiMessage with the information provided from the deserialized message.
        this.senderName = deserializedMessage.senderName;
        this.message = deserializedMessage.message;
        this.isServerMessage = deserializedMessage.isServer;

        createMessage();
    }
    private void setAbsoluteSize(JComponent label, Dimension newSize) {
        // Sets the size of the label to be exactly the provided dimension.
        label.setPreferredSize(newSize);
        label.setMinimumSize(newSize);
        label.setMaximumSize(newSize);
    }
    private void createMessage() {
        // Creates the label that displays the information about the message.
        messageContainer = new JPanel();

        setAbsoluteSize(messageContainer, new Dimension(1350, 75));
        messageContainer.setBackground(Color.LIGHT_GRAY);
        messageContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        senderLabel = new JLabel("<" + this.senderName + ">:");
        messageLabel = new JLabel(this.message);

        senderLabel.setFont(senderFont);
        if (this.isServerMessage) {
            // If the message is sent from the server change the color to reflect this.
            senderLabel.setForeground(serverMessageColor);
        }

        FontMetrics senderFontInfo = senderLabel.getFontMetrics(senderFont);
        int senderLabelWidth = 25 + senderFontInfo.stringWidth(senderLabel.getText()); // Changes the width of the label depending on the width of the username string.
        setAbsoluteSize(senderLabel, new Dimension(senderLabelWidth, 75));

        messageLabel.setFont(messageFont);
        setAbsoluteSize(messageLabel, new Dimension(1200 - senderLabelWidth, 75));

        messageContainer.add(senderLabel);
        messageContainer.add(messageLabel);

        this.add(messageContainer);
        this.setBackground(Color.darkGray);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setVisible(true);
    }
}

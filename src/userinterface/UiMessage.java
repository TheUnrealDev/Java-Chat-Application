package userInterface;

import javax.swing.*;
import java.awt.*;

public class UiMessage extends JPanel {
    final String senderName;
    final String message;
    final Font senderFont = new Font("Calibri", Font.BOLD, 24);
    final Font messageFont = new Font("Calibri", Font.PLAIN, 20);
    JPanel messageContainer;
    JLabel senderLabel;
    JLabel messageLabel;
    public UiMessage(String senderName, String message) {
        this.senderName = senderName;
        this.message = message;

        createMessage();
    }
    private void setAbsoluteSize(JComponent label, Dimension newSize) {
        label.setPreferredSize(newSize);
        label.setMinimumSize(newSize);
        label.setMaximumSize(newSize);
    }
    private void createMessage() {
        messageContainer = new JPanel();

        setAbsoluteSize(messageContainer, new Dimension(1350, 75));
        messageContainer.setOpaque(true);
        messageContainer.setBackground(Color.LIGHT_GRAY);
        messageContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        senderLabel = new JLabel("<" + this.senderName + ">:");
        messageLabel = new JLabel(this.message);

        senderLabel.setFont(senderFont);

        FontMetrics senderFontInfo = senderLabel.getFontMetrics(senderFont);

        int senderLabelWidth = 25 + senderFontInfo.stringWidth(senderLabel.getText());
        setAbsoluteSize(senderLabel, new Dimension(senderLabelWidth, 75));

        messageLabel.setFont(messageFont);
        setAbsoluteSize(messageLabel, new Dimension(1000, 75));

        messageContainer.add(senderLabel);
        messageContainer.add(messageLabel);

        this.add(messageContainer);
        this.setBackground(Color.darkGray);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setVisible(true);
    }
}

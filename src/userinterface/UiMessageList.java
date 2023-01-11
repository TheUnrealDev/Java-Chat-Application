package userInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UiMessageList extends JPanel {
    private final int MAX_MESSAGES = 3;
    private final ArrayList<UiMessage> recentMessages = new ArrayList<>();
    private JScrollPane scrollableArea;
    private Box messageArea;
    public UiMessageList() {
        CreateMessageList();
    }
    private void CreateMessageList() {
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

    public void addMessage(String senderName, String messageText) {
        if (messageText.isEmpty()) {
            return;
        }
        UiMessage message = new UiMessage(senderName, messageText);
        messageArea.add(message, SwingConstants.CENTER, 0);
        messageArea.setPreferredSize(new Dimension(1350, 650 + ((recentMessages.size()) * 75)));
        recentMessages.add(message);
        messageArea.repaint();

        scrollableArea.revalidate();
    }
}

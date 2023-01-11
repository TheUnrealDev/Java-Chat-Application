package userInterface;

import mainPack.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Gui {
    private final Client client;
    private JFrame window;
    private JPanel content;
    private UiMessageList messageDisplay;
    private UiInputArea inputField;

    public Gui(Client client) {
        this.client = client;
        initializeUI();
    }

    private void initializeUI() {
        createUi();

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Shut down the client after the close button is clicked, this also closes the window.
                client.shutdown();
            }
        });
    }

    private void createUi() {
        window = new JFrame("Bästa chatten");
        window.setSize(1500, 800);
        window.setBackground(Color.yellow);

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        messageDisplay = new UiMessageList();
        content.add(messageDisplay);

        inputField = new UiInputArea(this);
        content.add(inputField);

        window.add(content);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void addMessage(String sender, String message) {
        messageDisplay.addMessage(sender, message);
    }

    protected void sendMessage(String message) {
        client.sendMessage(message);
    }
    public void closeGui() {
        window.dispose();
        System.exit(0);
    }
}

/*
Author: Filip Hellgren

The Gui class responsible for opening a new window and displaying the chat interface that can be used to send messages.
 */

package userInterface;

import mainPack.Client;
import messages.DeserializedMessage;

import javax.swing.*;
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
        //Initialize a new window and create the UI components inside it

        window = new JFrame("Bästa chatten");
        window.setSize(1500, 800);

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        messageDisplay = new UiMessageList();
        content.add(messageDisplay);

        inputField = new UiInputArea(this);
        content.add(inputField);

        window.add(content);
        window.setLocationRelativeTo(null); // Centers the window on the screen.
        window.setVisible(true);
    }

    public void addMessage(DeserializedMessage deserializedMessage) {
        // Create a new message label inside the chat display.
        messageDisplay.addMessage(deserializedMessage);
    }

    protected void sendMessage(String message) {
        client.sendMessage(message);
    }
    public void closeGui() {
        window.dispose();
        System.exit(0);
    }
}

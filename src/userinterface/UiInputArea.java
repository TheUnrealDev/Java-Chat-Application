/*
Author: Filip Hellgren

The UiInputArea class responsible for displaying the input field where new messages are created and sent from,
as well as a submit button which sends the written string to the server.
 */

package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class UiInputArea extends JPanel {
    private final String PLACEHOLDER_TEXT = "Type here...";
    private final Font inputFont = new Font("Calibri", Font.PLAIN, 22);
    private final Gui GUI;
    JTextArea inputArea;
    JButton submitButton;
    public UiInputArea(Gui gui) {
        this.GUI = gui;
        createInputArea();
    }

    private void createInputArea() {
        // Creates and initializes the input field and the submit button.
        this.setLayout(new FlowLayout());

        inputArea = new JTextArea();
        inputArea.setPreferredSize(new Dimension(600, 70));
        inputArea.setBackground(Color.green);
        inputArea.addFocusListener(new FocusListener()
        {
            // Adds a FocusListener to monitor and assign functionality to
            // when the user starts interacting with the input field.
            @Override
            public void focusGained(FocusEvent e) {
                inputArea.setText("");
            } // Clear previous message when the user selects the text area.

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        inputArea.setFont(inputFont);
        inputArea.setText(PLACEHOLDER_TEXT);

        this.add(inputArea);

        submitButton = new JButton("Send");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //When the submit button is pressed send the text in the input field as a Client Message.
                sendMessage(inputArea.getText());
            }
        });
        submitButton.setPreferredSize(new Dimension(150, 70));
        this.add(submitButton);
    }

    private void sendMessage(String message) {
        // Verify that the message content is not empty before sending it to the server.
        if (message.trim().isEmpty()) { return; }
        if (message.equals(PLACEHOLDER_TEXT)) { return; }

        GUI.sendMessage(message);

        inputArea.setText(PLACEHOLDER_TEXT); // Resets string in the text area.
    }
}

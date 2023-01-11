package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class UiInputArea extends JPanel {
    private final String PLACEHOLDER_TEXT = "Type here...";
    private final Gui GUI;
    JTextArea inputArea;
    JButton submitButton;
    public UiInputArea(Gui gui) {
        this.GUI = gui;
        createInputArea();
    }

    private void createInputArea() {
        this.setLayout(new FlowLayout());

        inputArea = new JTextArea();
        inputArea.setPreferredSize(new Dimension(600, 70));
        inputArea.setBackground(Color.green);
        inputArea.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) {
                inputArea.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        this.add(inputArea);

        submitButton = new JButton("Send");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputArea.getText());
            }
        });
        submitButton.setPreferredSize(new Dimension(150, 70));
        this.add(submitButton);
    }

    private void sendMessage(String message) {
        if (message.trim().isEmpty()) { return; }
        if (message.equals(PLACEHOLDER_TEXT)) { return; }

        GUI.sendMessage(message);

        inputArea.setText("");
    }
}

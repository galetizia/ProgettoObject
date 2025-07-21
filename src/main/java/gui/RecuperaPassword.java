package gui;

import controller.ControllerRecuperoPassword;
import javax.swing.*;
import java.awt.*;

public class RecuperaPassword {
    private JButton confermaButton;
    private JTextField usernameInput;
    private JButton logoutButton;
    private JTextField emailInput;
    private JPanel mainPanel;
    private JLabel area;

    private static final String SEGOEUI = "Segoe UI";

    public RecuperaPassword(ControllerRecuperoPassword controller) {

        mainPanel.setPreferredSize(new Dimension(500, 400));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        confermaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confermaButton.addActionListener(ignored -> {
            String email = emailInput.getText();
            String username = usernameInput.getText();
            controller.recuperoPassword(email, username);
        });

        logoutButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.addActionListener(ignored -> controller.logout());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

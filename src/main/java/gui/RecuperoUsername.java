package gui;

import controller.ControllerRecuperoUsername;
import controller.MainController;
import model.*;
import javax.swing.*;
import java.awt.*;

public class RecuperoUsername {
    private JPanel mainPanel;
    private JButton confermaButton;
    private JTextField emailInput;
    private JPasswordField passwordInput;
    private JButton logoutButton;

    public RecuperoUsername(ControllerRecuperoUsername controller) {

        mainPanel.setPreferredSize(new Dimension(400, 300));
        confermaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confermaButton.addActionListener(e -> {
            String email = emailInput.getText();
            String password = passwordInput.getText();
            controller.recuperoUsername(email, password);
        });

        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> controller.logout());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

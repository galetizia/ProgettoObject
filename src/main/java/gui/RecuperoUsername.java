package gui;

import controller.ControllerRecuperoUsername;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class RecuperoUsername {
    private JPanel mainPanel;
    private JButton confermaButton;
    private JTextField emailInput;
    private JPasswordField passwordInput;
    private JButton logoutButton;
    private JLabel area;

    private static final String SEGOEUI = "Segoe UI";

    public RecuperoUsername(ControllerRecuperoUsername controller) {

        mainPanel.setPreferredSize(new Dimension(500, 400));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        confermaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confermaButton.addActionListener(ignored -> {
            String email = emailInput.getText();
            char[] passwordChars = passwordInput.getPassword(); //più sicuro, più controllo sulla password in memoria, non si rischia di esporla involontariamente
            String password = new String(passwordChars);
            controller.recuperoUsername(email, password);
            Arrays.fill(passwordChars, '0'); //azzera contenuto array passwordChars
        });

        logoutButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.addActionListener(ignored -> controller.logout());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

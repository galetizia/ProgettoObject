package gui;

import controller.ControllerRecuperoPassword;

import javax.swing.*;
import java.awt.*;

/**
 * Classe GUI per la schermata di recupero password.
 * Consente il recupero della password inserendo username ed email associate tra loro.
 * <p>
 * Questa classe interagisce con {@link ControllerRecuperoPassword} per delegare la logica applicativa.
 * </p>
 */

public class RecuperaPassword {

    /** Tutte le componenti di design */
    private JButton confermaButton;
    private JTextField usernameInput;
    private JButton indietroButton;
    private JTextField emailInput;
    private JPanel mainPanel;
    private JLabel area;

    /** Stringa che contiene il nome del font utilizzato */
    private static final String SEGOEUI = "Segoe UI";

    /**
     * Costruttore della schermata recupero password.
     *
     * @param controller Controller associato alla schermata.
     */
    public RecuperaPassword(ControllerRecuperoPassword controller) {

        mainPanel.setPreferredSize(new Dimension(500, 400));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        /* Bottone per confermare i dati inseriti,
        chiama il metodo del controller per la logica di recupero */
        confermaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confermaButton.addActionListener(ignored -> {
            String email = emailInput.getText();
            String username = usernameInput.getText();
            controller.recuperoPassword(email, username);
        });

        /* Bottone che porta alla schermata di login */
        indietroButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        indietroButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> controller.indietro());
    }

    /**
     * Restituisce il pannello principale della schermata.
     *
     * @return Il {@link JPanel} principale della schermata.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}

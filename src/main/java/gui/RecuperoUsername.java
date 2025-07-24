package gui;

import controller.ControllerRecuperoUsername;
import javax.swing.*;
import java.awt.*;


/**
 * Classe GUI per la schermata di recupero username.
 * Consente il recupero della username inserendo email e password associate tra loro.
 * <p>
 * Questa classe interagisce con {@link ControllerRecuperoUsername} per delegare la logica applicativa.
 * </p>
 */
public class RecuperoUsername {

    /** Tutte le componenti di design */
    private JPanel mainPanel;
    private JButton confermaButton;
    private JTextField emailInput;
    private JPasswordField passwordInput;
    private JButton indietroButton;
    private JLabel area;

    /** Stringa che contiene il nome del font utilizzato */
    private static final String SEGOEUI = "Segoe UI";
    /**
     * Costruttore della schermata recupero username.
     *
     * @param controller Controller associato alla schermata.
     */
    public RecuperoUsername(ControllerRecuperoUsername controller) {

        mainPanel.setPreferredSize(new Dimension(500, 400));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        /* Bottone per confermare i dati inseriti,
        chiama il metodo del controller per la logica di recupero */
        confermaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confermaButton.addActionListener(ignored -> {
            String email = emailInput.getText();
            String password = new String(passwordInput.getPassword());
            controller.recuperoUsername(email, password);
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

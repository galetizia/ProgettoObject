package gui;

import controller.ControllerSignIn;
import model.*;

import javax.swing.*;
import java.awt.*;

/**
 * Classe GUI per la schermata SignIn dedicata alla registrazione di un utente/organizzatore.
 * <p>
 * Questa classe interagisce con {@link ControllerSignIn} per delegare la logica applicativa.
 * </p>
 */
public class SignIn {

    /** Tutte le componenti di design */
    private JPanel mainPanel;
    private JTextField inpName;
    private JTextField inpSurname;
    private JTextField inpEmail;
    private JTextField inpUsername;
    private JPasswordField inpPassword;
    private JPasswordField inpPassConfirm;
    private JButton registerButton;
    private JButton backButton;
    private JCheckBox utenteCheckBox;
    private JCheckBox organizzatoreCheckBox;
    private JLabel area;

    /** Stringa che contiene il nome del font utilizzato */
    private static final String SEGOEUI = "Segoe UI";

    /**
     * Costruttore che inizializza tutti i componenti della GUI e imposta i listener per i vari pulsanti.
     *
     * @param controller Il controller associato alla schermata.
     */
    public SignIn(ControllerSignIn controller) {

        mainPanel.setPreferredSize(new Dimension(600, 400));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        /*Solo una delle due CheckBox può essere selezionata */
        utenteCheckBox.addActionListener(ignored -> {
            if (utenteCheckBox.isSelected()) organizzatoreCheckBox.setSelected(false);
        });

        organizzatoreCheckBox.addActionListener(ignored -> {
            if (organizzatoreCheckBox.isSelected()) utenteCheckBox.setSelected(false);
        });

        /*Bottone che chiama il metodo signIn del controller, dove controlla se la registrazione
        * ha avuto un riscontro positivo o meno*/
        registerButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        registerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registerButton.addActionListener(ignored -> {
            String username = inpUsername.getText();
            String email = inpEmail.getText();
            String name = inpName.getText();
            String surname = inpSurname.getText();
            String password = new String(inpPassword.getPassword());
            String confirmPassword = new String(inpPassConfirm.getPassword());

            Utente u = new Utente(name,surname,email,username,password);

            controller.signIn(u,confirmPassword,utenteCheckBox.isSelected(), organizzatoreCheckBox.isSelected());
        });

        /*Bottone per tornare alla schermata di login*/
        backButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.addActionListener(ignored -> controller.showLogin());
    }

    /**
     * Restituisce il pannello principale della schermata.
     *
     * @return Il pannello principale {@link JPanel}.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}


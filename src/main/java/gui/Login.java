package gui;

import controller.ControllerLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe GUI per la schermata Login.
 * Gestisce il login consentendo l'inserimento delle credenziali e
 * la selezione del ruolo utente (Utente, Organizzatore o Giudice).
 * <p>
 * Questa classe interagisce con {@link ControllerLogin} per delegare la logica applicativa.
 * </p>
 */
public class Login {

    /** Tutte le componenti di design*/
    private JPanel mainPanel;
    private JTextField inpUsername;
    private JPasswordField inpPassword;
    private JButton loginButton;
    private JButton signInButton;
    private JLabel forgotpass;
    private JCheckBox organizzatoreCheck;
    private JCheckBox utenteCheck;
    private JLabel forgotUser;
    private JCheckBox giudiceCheckBox;
    private JLabel area;

    /** Stringa che contiene il nome del font utilizzato */
    private static final String SEGOEUI = "Segoe UI";

    /**
     * 
     * Costruttore della GUI di login.
     *
     * @param controller Il controller associato alla schermata.
     */
    public Login(ControllerLogin controller) {

        mainPanel.setPreferredSize(new Dimension(400, 300));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));
        loginButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        /* Fa si che ci sia solo una casella selezionata */
        utenteCheck.addActionListener(ignored -> {
            if (utenteCheck.isSelected()) {
                organizzatoreCheck.setSelected(false); giudiceCheckBox.setSelected(false);}
        });

        /* Fa si che ci sia solo una casella selezionata */
        organizzatoreCheck.addActionListener(ignored -> {
            if (organizzatoreCheck.isSelected()) {
                utenteCheck.setSelected(false); giudiceCheckBox.setSelected(false);}
        });

        /* Fa si che ci sia solo una casella selezionata */
        giudiceCheckBox.addActionListener(ignored -> {
            if (giudiceCheckBox.isSelected()) {
                utenteCheck.setSelected(false); organizzatoreCheck.setSelected(false);}
        });

        /* Bottone di conferma che chiama il metodo contenente la logica per il controllo delle credenziali */
        loginButton.addActionListener(ignored -> {
            String username = inpUsername.getText();
            String password = new String(inpPassword.getPassword());
            inpUsername.setText("");
            inpPassword.setText("");
            controller.login(username, password, utenteCheck.isSelected(), organizzatoreCheck.isSelected(), giudiceCheckBox.isSelected());
        });

        /* Bottone che porta alla schermata sign-in */
        signInButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        signInButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signInButton.addActionListener(ignored -> controller.showSignIn());

        /* Simula un link cliccabile, porta a una schermata di recupero username */
        forgotUser.setText("<html><a href=''>Ho dimenticato il mio username</a></html>");
        forgotUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        forgotUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.showRecuperoUsername();
            }
        });

        /* Simula un link cliccabile, porta a una schermata di recupero password */
        forgotpass.setText("<html><a href=''>Ho dimenticato la mia password</a></html>");
        forgotpass.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        forgotpass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.showRecuperoPassword();
            }
        });
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

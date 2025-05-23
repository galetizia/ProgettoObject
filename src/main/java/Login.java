import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Login {
    private JPanel mainPanel;
    private JLabel username;
    private JTextField inpUsername;
    private JLabel password;
    private JPasswordField inpPassword;
    private JButton loginButton;
    private JButton signInButton;
    private JLabel forgotLabel;
    private JCheckBox organizzatoreCheck;
    private JCheckBox utenteCheck;
    ArrayList<Organizzatore> listaOrganizzatori= new ArrayList<>();
    ArrayList<Utente> listaUtenti= new ArrayList<>();
    private Organizzatore loggedOrganizzatore;
    private Utente loggedUtente;

    public Login(JFrame parentFrame, ArrayList<Utente> listaUtenti, ArrayList<Organizzatore> listaOrganizzatori) {

        this.listaOrganizzatori = listaOrganizzatori;
        this.listaUtenti = listaUtenti;
        mainPanel.setPreferredSize(new Dimension(400, 300));

        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> {
            String username = inpUsername.getText();
            String password = new String(inpPassword.getPassword());

            if(!organizzatoreCheck.isSelected() && !utenteCheck.isSelected()) {
                JOptionPane.showMessageDialog(mainPanel, "Inserire un ruolo");
                return;
            }
            // Cerca tra gli organizzatori
            if(organizzatoreCheck.isSelected()) {
                for (Organizzatore org : listaOrganizzatori) {
                    if (org.username.equals(username) && org.password.equals(password)) {
                        JOptionPane.showMessageDialog(mainPanel, "Login effettuato come Organizzatore!");
                        loggedOrganizzatore = org;
                        parentFrame.dispose();
                        showSchermataOrganizzatore(parentFrame);
                        return; // esce dal metodo
                    }
                }
                JOptionPane.showMessageDialog(mainPanel, "Credenziali errate.");

            }
            // Cerca tra gli utenti
            if(utenteCheck.isSelected()) {
                for (Utente utente : listaUtenti) {
                    if (utente.username.equals(username) && utente.password.equals(password)) {
                        JOptionPane.showMessageDialog(mainPanel, "Login effettuato come Utente!");
                        loggedUtente = utente;
                        // Qui puoi mostrare la schermata utente (da implementare)
                        return;
                    }
                }
                JOptionPane.showMessageDialog(mainPanel, "Credenziali errate.");
            }
        });
        signInButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signInButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signInButton.addActionListener(e -> {
            parentFrame.dispose(); // Chiude la finestra di login
            showSignInForm(parentFrame); // Apre la finestra di registrazione
        });

        // Simula link cliccabile
        forgotLabel.setText("<html><a href=''>Ho dimenticato la mia password/username?</a></html>");
        forgotLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        forgotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(mainPanel, "Recupero password in arrivo!");
            } //idem qua dobbiamo poi fare una cosa a parte
        });
    }

    private void showSignInForm(JFrame parentFrame) {
        parentFrame.dispose(); // Chiude la finestra di login PRIMA di aprirne una nuova
        JFrame frame = new JFrame("Registrazione");
        frame.setContentPane(new SignIn(frame,listaOrganizzatori,listaUtenti).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showSchermataOrganizzatore(JFrame parentFrame) {
        parentFrame.dispose();
        JFrame frame = new JFrame("Schermata Organizzatore");
        frame.setContentPane(new SchermataOrganizzatore(frame, listaOrganizzatori, listaUtenti, loggedOrganizzatore).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            ArrayList<Organizzatore> listaOrganizzatori = new ArrayList<>();
            ArrayList<Utente> listaUtenti = new ArrayList<>();

            Login login = new Login(frame,listaUtenti,listaOrganizzatori);
            frame.setContentPane(login.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

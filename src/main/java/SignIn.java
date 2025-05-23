import model.Organizzatore;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SignIn {
    private JPanel mainPanel;
    private JTextField inpName;
    private JTextField inpSurname;
    private JTextField inpEmail;
    private JTextField inpUsername;
    private JPasswordField inpPassword;
    private JPasswordField inpPassConfirm;
    private JButton registerButton;
    private JLabel name;
    private JLabel surname;
    private JLabel email;
    private JLabel username;
    private JButton backButton;
    private JCheckBox utenteCheckBox;
    private JCheckBox organizzatoreCheckBox;
    private ArrayList<Organizzatore> listaOrganizzatori = new ArrayList<>();
    private ArrayList<Utente> listaUtenti = new ArrayList<>();

    public SignIn(JFrame parentFrame, ArrayList<Organizzatore> listaOrganizzatori, ArrayList<Utente> listaUtenti) {
        // Logica per la registrazione
        mainPanel.setPreferredSize(new Dimension(600, 400));

        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registerButton.addActionListener(e -> {
            String username = inpUsername.getText();
            String email = inpEmail.getText();
            String name = inpName.getText();
            String surname = inpSurname.getText();
            String password = new String(inpPassword.getPassword());
            String confirmPassword = new String(inpPassConfirm.getPassword());
            this.listaOrganizzatori = listaOrganizzatori;
            this.listaUtenti = listaUtenti;

            if (password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty() || email.isEmpty() || name.isEmpty() || surname.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Compilare tutti i campi");
            }else if(!email.contains("@")) {
                JOptionPane.showMessageDialog(parentFrame, "Formato Email non valido");
            }else if (password.equals(confirmPassword)) {
                if(utenteCheckBox.isSelected()) {
                    Utente utente = new Utente(name,surname,email, username, password);
                    listaUtenti.add(utente);
                    JOptionPane.showMessageDialog(mainPanel, "Registrazione completata!");
                    parentFrame.dispose(); // Dopo la registrazione, torna al login
                    showLoginForm();
                } else if (organizzatoreCheckBox.isSelected()) {
                    Organizzatore organizzatore= new Organizzatore(name,surname,email, username, password);
                    listaOrganizzatori.add(organizzatore);
                    JOptionPane.showMessageDialog(mainPanel, "Registrazione completata!");
                    parentFrame.dispose(); // Dopo la registrazione, torna al login
                    showLoginForm();
                } else{
                    JOptionPane.showMessageDialog(parentFrame, "Inserire un ruolo");
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Le password non coincidono!");
            }
        });

        // Pulsante indietro
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            parentFrame.dispose(); // Chiude la finestra corrente
            showLoginForm();       // Apre direttamente una nuova finestra di login
        });
    }

    private void showLoginForm() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setContentPane(new Login(loginFrame, listaUtenti,listaOrganizzatori).getMainPanel());
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setResizable(false);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}


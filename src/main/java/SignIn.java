import model.Organizzatore;
import model.Utente;

import javax.swing.*;

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

    public SignIn(JFrame parentFrame) {
        // Logica per la registrazione
        registerButton.addActionListener(e -> {
            String username = inpUsername.getText();
            String email = inpEmail.getText();
            String name = inpName.getText();
            String surname = inpSurname.getText();
            String password = new String(inpPassword.getPassword());
            String confirmPassword = new String(inpPassConfirm.getPassword());

            if (password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty() || email.isEmpty() || name.isEmpty() || surname.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Compilare tutti i campi");
            }else if(!email.contains("@")) {
                JOptionPane.showMessageDialog(parentFrame, "Formato Email non valido");
            }else if (password.equals(confirmPassword)) {
                if(utenteCheckBox.isSelected()) {
                    Utente utente = new Utente(name,surname,email, username, password);
                    JOptionPane.showMessageDialog(mainPanel, "Registrazione completata!");
                    parentFrame.dispose(); // Dopo la registrazione, torna al login
                    showLoginForm();
                } else if (organizzatoreCheckBox.isSelected()) {
                    Organizzatore organizzatore= new Organizzatore(name,surname,email, username, password);
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
        backButton.addActionListener(e -> {
            parentFrame.dispose(); // Chiude la finestra corrente
            showLoginForm();       // Apre direttamente una nuova finestra di login
        });
    }

    private void showLoginForm() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setContentPane(new Login(loginFrame).getMainPanel());
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


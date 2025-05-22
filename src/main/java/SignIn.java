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
    private JLabel Email;
    private JLabel username;
    private JButton backButton;

    public SignIn(JFrame parentFrame) {
        // Logica per la registrazione
        registerButton.addActionListener(e -> {
            String password = new String(inpPassword.getPassword());
            String confirmPassword = new String(inpPassConfirm.getPassword());

            if (password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Password and confirm password are empty");
            } else if (password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(mainPanel, "Registrazione completata!");
                parentFrame.dispose(); // Dopo la registrazione, torna al login
                showLoginForm();
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


import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {
    private JPanel mainPanel;
    private JLabel username;
    private JTextField inpUsername;
    private JLabel password;
    private JPasswordField inpPassword;
    private JButton loginButton;
    private JButton signInButton;
    private JLabel forgotLabel;

    public Login(JFrame parentFrame) {
        // Evento "Login"
        loginButton.addActionListener(e -> {
            String username = inpUsername.getText();
            String password = new String(inpPassword.getPassword());
            if (username.equals("admin") && password.equals("1234")) {
                JOptionPane.showMessageDialog(mainPanel, "Login effettuato!");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Credenziali errate.");
            }
        });

        // Evento "Sign-In"
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
        frame.setContentPane(new SignIn(frame).getMainPanel());
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
            Login login = new Login(frame);
            frame.setContentPane(login.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

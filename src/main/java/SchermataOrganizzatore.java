import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SchermataOrganizzatore {
    private JButton organizzaHackathonButton;
    private JButton hackathonAttualeButton;
    private JButton informazioniPersonaliButton;
    private JPanel informazioniPersonaliPanel;
    private JLabel name;
    private JLabel surname;
    private JLabel email;
    private JLabel username;
    private JPanel mainPanel;
    private JButton logOutButton;
    ArrayList<Organizzatore> listaOrganizzatori= new ArrayList<>();
    ArrayList<Utente> listaUtenti= new ArrayList<>();

    public SchermataOrganizzatore(JFrame frame, ArrayList<Organizzatore> organizzatori, ArrayList<Utente> utenti, Organizzatore organizzatore) {
        this.listaUtenti = utenti;
        this.listaOrganizzatori = organizzatori;
        mainPanel.setPreferredSize(new Dimension(600, 400));


        informazioniPersonaliButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        informazioniPersonaliButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        informazioniPersonaliButton.addActionListener(e -> {
            name.setText("Nome: "+organizzatore.getNome());
            surname.setText("Cognome: "+organizzatore.getCognome());
            email.setText("Email: "+organizzatore.email);
            username.setText("Username: "+organizzatore.username);
        });

        hackathonAttualeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hackathonAttualeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hackathonAttualeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        organizzaHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        organizzaHackathonButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        organizzaHackathonButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });
        logOutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logOutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutButton.addActionListener(e -> {
            frame.dispose();
            showLoginForm();
        });

    }

    public JPanel getMainPanel(){
        return mainPanel;
    }

    private void showLoginForm(){
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setContentPane(new Login(loginFrame, listaUtenti, listaOrganizzatori).getMainPanel());
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setResizable(false);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

}


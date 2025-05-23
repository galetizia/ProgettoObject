import model.*;
import javax.swing.*;
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


        informazioniPersonaliButton.addActionListener(e -> {
            name.setText(organizzatore.getNome());
            surname.setText(organizzatore.getCognome());
            email.setText(organizzatore.email);
            username.setText(organizzatore.username);
        });

        hackathonAttualeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        organizzaHackathonButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

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


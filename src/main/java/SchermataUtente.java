import model.Organizzatore;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SchermataUtente {
    private JButton iscrizionebutton;
    private JButton attualebutton;
    private JButton teambutton;
    private JButton informazioniPersonaliButton;
    private JButton problemabutton;
    private JButton logoutButton;
    private JButton caricaAggiornamentoButton;
    private JLabel name;
    private JLabel surname;
    private JLabel username;
    private JLabel email;
    private JPanel mainPanel;
    ArrayList<Organizzatore> listaOrganizzatori= new ArrayList<>();
    ArrayList<Utente> listaUtenti= new ArrayList<>();


    public SchermataUtente(JFrame frame, ArrayList<Organizzatore> organizzatori, ArrayList<Utente> utenti, Utente utente){
        this.listaOrganizzatori = organizzatori;
        this.listaUtenti = utenti;
        mainPanel.setPreferredSize(new Dimension(600, 400));


        informazioniPersonaliButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        informazioniPersonaliButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        informazioniPersonaliButton.addActionListener(e -> {
            name.setText("Nome: "+utente.getNome());
            surname.setText("Cognome: "+utente.getCognome());
            email.setText("Email: "+utente.email);
            username.setText("Username: "+utente.username);
        });

        attualebutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        attualebutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        attualebutton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        iscrizionebutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        iscrizionebutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iscrizionebutton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        teambutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        teambutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teambutton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        problemabutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        problemabutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        problemabutton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        caricaAggiornamentoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        caricaAggiornamentoButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        caricaAggiornamentoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> {
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

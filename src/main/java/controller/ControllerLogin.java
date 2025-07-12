package controller;

import gui.Login;
import implementazionepostgresdao.OrganizzatoreDAO;
import implementazionepostgresdao.UtenteDAO;
import model.Organizzatore;
import model.Utente;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ControllerLogin {
    private final Login loginGui;
    private List<Organizzatore> listaOrganizzatori;
    private List<Utente> listaUtenti;
    OrganizzatoreDAO odao = new OrganizzatoreDAO();
    UtenteDAO udao = new UtenteDAO();

    private final MainController maincontroller;

    public ControllerLogin(List<Utente> utenti, List<Organizzatore> organizzatori , MainController maincontroller) {
        this.maincontroller = maincontroller;
        this.listaOrganizzatori = organizzatori;
        this.listaUtenti = utenti;
        this.loginGui = new Login (this);
    }

    public JPanel getLogin() {
        return loginGui.getMainPanel();
    }

    public void login(String username, String password, boolean isUtente, boolean isOrganizzatore) {
        if(!isUtente && !isOrganizzatore){
            JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Inserire un ruolo");
            return;
        }

    if(isOrganizzatore){
        Organizzatore o = odao.login(username, password);
        if(o!=null){
            JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Login effettuato come Organizzatore!");
            maincontroller.showSchermataOrganizzatore(o);
            return;
        }
    }

    if(isUtente){
        Utente u = udao.login(username, password);
        if(u!=null){
            JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Login effettuato come Utente!");
            maincontroller.showSchermataUtente(u);
            return;
        }
    }
    JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Credenziali errate.");
    }
    public void showSignIn() {
        maincontroller.showSignIn();
    }

    public void showRecuperoUsername() {
        maincontroller.showRecuperoUsername();
    }

    public void showRecuperoPassword() {
        maincontroller.showRecuperoPassword();
    }
}

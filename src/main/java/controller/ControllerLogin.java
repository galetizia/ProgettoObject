package controller;

import gui.Login;
import model.Organizzatore;
import model.Utente;

import javax.swing.*;
import java.util.ArrayList;

public class ControllerLogin {
    private final Login loginGui;
    private ArrayList<Organizzatore> listaOrganizzatori;
    private ArrayList<Utente> listaUtenti;

    private final MainController maincontroller;

    public ControllerLogin(ArrayList<Utente> utenti, ArrayList<Organizzatore> organizzatori , MainController maincontroller) {
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
        for (Organizzatore org : listaOrganizzatori) {
            if (org.username.equals(username) && org.password.equals(password)) {
                JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Login effettuato come Organizzatore!");
                maincontroller.showSchermataOrganizzatore(org);
                return; // esce dal metodo
            }
        }
    }

    if(isUtente){
        for (Utente u : listaUtenti) {
            if (u.username.equals(username) && u.password.equals(password)) {
                JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Login effettuato come Utente!");
                maincontroller.showSchermataUtente(u);
                return;
            }
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

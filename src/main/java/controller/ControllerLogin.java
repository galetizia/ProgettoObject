package controller;

import gui.Login;
import implementazionepostgresdao.*;
import model.*;
import javax.swing.*;

public class ControllerLogin {
    private final Login loginGui;
    OrganizzatoreDAO odao = new OrganizzatoreDAO();
    UtenteDAO udao = new UtenteDAO();
    GiudiceDAO gdao = new GiudiceDAO();

    private final MainController maincontroller;

    public ControllerLogin(MainController maincontroller) {
        this.maincontroller = maincontroller;
        this.loginGui = new Login (this);
    }

    public JPanel getLogin() {
        return loginGui.getMainPanel();
    }

    public void login(String username, String password, boolean isUtente, boolean isOrganizzatore, boolean isGiudice) {
        if(!isUtente && !isOrganizzatore && !isGiudice) {
            JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Inserire un ruolo");
            return;
        }

        if(isUtente){
            Utente u = udao.login(username, password);
            if(u!=null){
                JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Login effettuato come Utente!");
                maincontroller.showSchermataUtente(u);
                return;
            }
        }

        if(isGiudice) {
            Giudice g = gdao.login(username, password);
            if(g != null) {
                JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Login effettuato come Giudice!");
                maincontroller.showSchermataGiudice(g);
                return;
            }
        }

        if(isOrganizzatore){
            Organizzatore o = odao.login(username, password);
            if(o!=null){
                JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Login effettuato come Organizzatore!");
                maincontroller.showSchermataOrganizzatore(o);
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

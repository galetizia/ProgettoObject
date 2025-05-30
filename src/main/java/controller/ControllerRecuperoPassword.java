package controller;

import gui.RecuperaPassword;
import model.*;

import javax.swing.*;
import java.util.ArrayList;

public class ControllerRecuperoPassword {
    private final RecuperaPassword recuperaPassword;
    private ArrayList<Utente> utenti;
    private ArrayList<Organizzatore> organizzatori;

    private final MainController mainController;

    public ControllerRecuperoPassword(ArrayList<Utente> utenti, ArrayList<Organizzatore> organizzatori, MainController mainController) {
        this.utenti = utenti;
        this.organizzatori = organizzatori;
        this.mainController = mainController;
        this.recuperaPassword = new RecuperaPassword(this);
    }

    public JPanel getRecuperaPassword() {
        return recuperaPassword.getMainPanel();
    }

    public void recuperoPassword(String email, String username) {
        boolean success = false;
        for(Utente u : utenti){
            if((u.getEmail().equalsIgnoreCase(email)) && (u.getUsername().equalsIgnoreCase(username))){
                success = true;
                JOptionPane.showMessageDialog(getRecuperaPassword(),"Password: " +u.getPassword());
            }
        }
        for(Organizzatore o : organizzatori){
            if((o.getEmail().equalsIgnoreCase(email)) && (o.getUsername().equalsIgnoreCase(username))){
                success = true;
                JOptionPane.showMessageDialog(getRecuperaPassword(),"Password: " +o.getPassword());
            }
        }
        if(!success){
            if(email.isEmpty() || username.isEmpty()){
                JOptionPane.showMessageDialog(getRecuperaPassword(), "Compila tutti i campi");
            } else
                JOptionPane.showMessageDialog(getRecuperaPassword(), "Non ci sono utenti/organizzatori con questo username/email");
        }
    }
    public void logout() {
        mainController.logout();
    }
}

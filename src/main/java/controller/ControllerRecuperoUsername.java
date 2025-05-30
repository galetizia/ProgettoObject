package controller;

import gui.RecuperoUsername;
import model.*;

import javax.swing.*;
import java.util.ArrayList;

public class ControllerRecuperoUsername {
    private final RecuperoUsername recuperoUsername;
    private ArrayList<Utente> utenti;
    private ArrayList<Organizzatore> organizzatori;

    private final MainController mainController;

    public ControllerRecuperoUsername(ArrayList<Utente> utenti, ArrayList<Organizzatore> organizzatori, MainController mainController) {
        this.utenti = utenti;
        this.organizzatori = organizzatori;
        this.mainController = mainController;
        this.recuperoUsername = new RecuperoUsername(this);
    }
    public JPanel getRecuperoUsername() {
        return recuperoUsername.getMainPanel();
    }

    public void recuperoUsername(String email, String password) {

        boolean success = false;
        for(Utente u : utenti){
            if((u.getEmail().equalsIgnoreCase(email)) && (u.getPassword().equalsIgnoreCase(password))){
                success = true;
                JOptionPane.showMessageDialog(getRecuperoUsername(), "Username: " +u.getUsername());
            }
        }
        for(Organizzatore o : organizzatori){
            if((o.getEmail().equalsIgnoreCase(email)) && (o.getPassword().equalsIgnoreCase(password))){
                success = true;
                JOptionPane.showMessageDialog(getRecuperoUsername(),"Username: " +o.getUsername());
            }
        }
        if(!success){
            if(email.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(getRecuperoUsername(), "Compila tutti i campi");
            } else
                JOptionPane.showMessageDialog(getRecuperoUsername(), "Non ci sono utenti/organizzatori con questa email/password");
        }
    }

    public void logout(){
        mainController.logout();
    }

}

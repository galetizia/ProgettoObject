package controller;

import gui.RecuperoUsername;
import implementazionepostgresdao.*;
import implementazionepostgresdao.UtenteDAO;
import model.*;

import javax.swing.*;

public class ControllerRecuperoUsername {
    private final RecuperoUsername recuperoUsername;
    UtenteDAO udao = new UtenteDAO();
    OrganizzatoreDAO odao = new OrganizzatoreDAO();
    GiudiceDAO gdao = new GiudiceDAO();

    private static final String USERNAME = "Username: ";

    private final MainController mainController;

    public ControllerRecuperoUsername(MainController mainController) {
        this.mainController = mainController;
        this.recuperoUsername = new RecuperoUsername(this);
    }
    public JPanel getRecuperoUsername() {
        return recuperoUsername.getMainPanel();
    }

    public void recuperoUsername(String email, String password) {

        boolean success = false;
        Utente u = udao.findUtenteByEmail(email);
        Organizzatore o = odao.findOrganizzatoreByEmail(email);
        Giudice g = gdao.findGiudiceByEmail(email);

        if((u!=null)&&((u.getEmail().equalsIgnoreCase(email)) && (u.getPassword().equalsIgnoreCase(password)))){
            success = true;
            JOptionPane.showMessageDialog(getRecuperoUsername(), USERNAME +u.getUsername());
        }

        if((o!=null)&&((o.getEmail().equalsIgnoreCase(email)) && (o.getPassword().equalsIgnoreCase(password)))){
            success = true;
            JOptionPane.showMessageDialog(getRecuperoUsername(),USERNAME +o.getUsername());
        }

        if((g!=null)&&((g.getEmail().equalsIgnoreCase(email)) && (g.getPassword().equalsIgnoreCase(password)))){
            success = true;
            JOptionPane.showMessageDialog(getRecuperoUsername(),USERNAME +g.getUsername());
        }

        if(!success){
            if(email.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(getRecuperoUsername(), "Compila tutti i campi");
            } else
                JOptionPane.showMessageDialog(getRecuperoUsername(), "Non ci sono utenti/organizzatori/giudici con questa email/password");
        }
    }

    public void indietro(){
        mainController.logout();
    }

}

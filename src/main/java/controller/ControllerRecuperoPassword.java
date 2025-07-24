package controller;

import gui.RecuperaPassword;
import implementazionepostgresdao.*;
import model.*;

import javax.swing.*;

public class ControllerRecuperoPassword {
    private final RecuperaPassword recuperaPassword;
    UtenteDAO udao = new UtenteDAO();
    OrganizzatoreDAO odao = new OrganizzatoreDAO();
    GiudiceDAO gdao = new GiudiceDAO();

    private static final String PASSWORD = "Password: ";

    private final MainController mainController;

    public ControllerRecuperoPassword(MainController mainController) {
        this.mainController = mainController;
        this.recuperaPassword = new RecuperaPassword(this);
    }

    public JPanel getRecuperaPassword() {
        return recuperaPassword.getMainPanel();
    }

    public void recuperoPassword(String email, String username) {
        boolean success = false;
        Utente u = udao.findUtenteByUsername(username);
        Organizzatore o = odao.findOrganizzatoreByUsername(username);
        Giudice g = gdao.findGiudiceByUsername(username);

        if((u!=null)&&((u.getEmail().equalsIgnoreCase(email)) && (u.getUsername().equalsIgnoreCase(username)))){
            success = true;
            JOptionPane.showMessageDialog(getRecuperaPassword(),PASSWORD +u.getPassword());
        }

        if((o!=null)&&((o.getEmail().equalsIgnoreCase(email)) && (o.getUsername().equalsIgnoreCase(username)))){
            success = true;
            JOptionPane.showMessageDialog(getRecuperaPassword(),PASSWORD +o.getPassword());
        }

        if((g!=null)&&((g.getEmail().equalsIgnoreCase(email)) && (g.getUsername().equalsIgnoreCase(username)))){
            success = true;
            JOptionPane.showMessageDialog(getRecuperaPassword(),PASSWORD +g.getPassword());
        }

        if(!success){
            if(email.isEmpty() || username.isEmpty()){
                JOptionPane.showMessageDialog(getRecuperaPassword(), "Compila tutti i campi");
            } else
                JOptionPane.showMessageDialog(getRecuperaPassword(), "Non ci sono utenti/organizzatori/giudici con questo username/email");
        }
    }
    public void indietro() {
        mainController.logout();
    }
}

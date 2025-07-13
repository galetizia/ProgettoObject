package controller;

import gui.RecuperoUsername;
import implementazionepostgresdao.HackathonDAO;
import model.*;

import javax.swing.*;

public class ControllerRecuperoUsername {
    private final RecuperoUsername recuperoUsername;
    HackathonDAO hdao = new HackathonDAO();

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
        Utente u = hdao.findUtenteByEmail(email);
        Organizzatore o = hdao.findOrganizzatoreByEmail(email);
            if((u!=null)&&((u.getEmail().equalsIgnoreCase(email)) && (u.getPassword().equalsIgnoreCase(password)))){
                success = true;
                JOptionPane.showMessageDialog(getRecuperoUsername(), "Username: " +u.getUsername());
            }

            if((o!=null)&&((o.getEmail().equalsIgnoreCase(email)) && (o.getPassword().equalsIgnoreCase(password)))){
                success = true;
                JOptionPane.showMessageDialog(getRecuperoUsername(),"Username: " +o.getUsername());
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

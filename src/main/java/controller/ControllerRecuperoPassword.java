package controller;

import gui.RecuperaPassword;
import implementazionepostgresdao.*;
import model.*;

import javax.swing.*;

public class ControllerRecuperoPassword {
    private final RecuperaPassword recuperaPassword;
    HackathonDAO hdao = new HackathonDAO();

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
        Utente u = hdao.findUtenteByUsername(username);
        Organizzatore o = hdao.findOrganizzatoreByUsername(username);

            if((u!=null)&&((u.getEmail().equalsIgnoreCase(email)) && (u.getUsername().equalsIgnoreCase(username)))){
                success = true;
                JOptionPane.showMessageDialog(getRecuperaPassword(),"Password: " +u.getPassword());
            }

            if((o!=null)&&((o.getEmail().equalsIgnoreCase(email)) && (o.getUsername().equalsIgnoreCase(username)))){
                success = true;
                JOptionPane.showMessageDialog(getRecuperaPassword(),"Password: " +o.getPassword());
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

package controller;

import gui.SignIn;
import model.Utente;
import model.Organizzatore;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ControllerSignIn {
    private final SignIn signInGui;
    private ArrayList<Utente> utenti;
    private ArrayList<Organizzatore> organizzatori;

    private final MainController mainController;

    public ControllerSignIn(ArrayList<Utente> utenti, ArrayList<Organizzatore> organizzatori, MainController mainController) {
        this.utenti = utenti;
        this.organizzatori = organizzatori;
        this.mainController = mainController;
        this.signInGui = new SignIn(this);
    }
    public JPanel getSignIn() {
        return signInGui.getMainPanel();
    }

    public void SignIn(String username, String email, String name, String surname, String password, String confirmPassword, boolean isUtente, boolean isOrganizzatore) {
        if (password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty() || email.isEmpty() || name.isEmpty() || surname.isEmpty()) {
            JOptionPane.showMessageDialog(getSignIn(), "Compilare tutti i campi");
        } else if (!email.contains("@")) {
            JOptionPane.showMessageDialog(getSignIn(), "Formato Email non valido");
        } else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(getSignIn(), "Le password non coincidono!");
        } else if (!isUtente && !isOrganizzatore) {
            JOptionPane.showMessageDialog(getSignIn(), "Inserire un ruolo");
        } else {
            boolean success = false;

            if (isUtente) {
                Utente utente = new Utente(name, surname, email, username, password);
                success = registraUtente(utente);
            } else if (isOrganizzatore) {
                Organizzatore organizzatore = new Organizzatore(name, surname, email, username, password);
                success = registraOrganizzatore(organizzatore);
            }

            if (!success) {
                JOptionPane.showMessageDialog(getSignIn(), "Username già in uso. Scegli un altro.");
            } else {
                JOptionPane.showMessageDialog(getSignIn(), "Registrazione completata!");
                mainController.showLogin();
            }
        }
    }

    public boolean registraUtente(Utente utente) {
        if (isUsernameTaken(utente.getUsername())) return false;
        mainController.getListaUtenti().add(utente);
        return true;
    }

    public boolean registraOrganizzatore(Organizzatore organizzatore) {
        if (isUsernameTaken(organizzatore.getUsername())) return false;
        mainController.getListaOrganizzatori().add(organizzatore);
        return true;

    }
    private boolean isUsernameTaken(String username) {
        return mainController.getListaUtenti().stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username)) ||
                mainController.getListaOrganizzatori().stream().anyMatch(o -> o.getUsername().equalsIgnoreCase(username));
    }


    public void showLogin(){
        mainController.showLogin();
    }

}

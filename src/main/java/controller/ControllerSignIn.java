package controller;

import gui.SignIn;
import implementazionepostgresdao.*;
import model.Utente;

import javax.swing.*;

public class ControllerSignIn {
    private final SignIn signInGui;
    HackathonDAO hdao = new HackathonDAO();

    private final MainController mainController;

    public ControllerSignIn(MainController mainController) {
        this.mainController = mainController;
        this.signInGui = new SignIn(this);
    }
    public JPanel getSignIn() {
        return signInGui.getMainPanel();
    }

    public void signIn(Utente u, String confirmPassword, boolean isUtente, boolean isOrganizzatore) {

        String checkEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (u.getPassword().isEmpty() || confirmPassword.isEmpty() || u.getUsername().isEmpty() || u.getEmail().isEmpty() || u.getNome().isEmpty() || u.getCognome().isEmpty()) {
            JOptionPane.showMessageDialog(getSignIn(), "Compilare tutti i campi");
        } else if (!u.getEmail().matches(checkEmail)) {
            JOptionPane.showMessageDialog(getSignIn(), "Formato Email non valido");
        } else if (!u.getPassword().equals(confirmPassword)) {
            JOptionPane.showMessageDialog(getSignIn(), "Le password non coincidono!");
        } else if (!isUtente && !isOrganizzatore) {
            JOptionPane.showMessageDialog(getSignIn(), "Inserire un ruolo");
        } else {

            boolean success;
            if (isUtente) {
                success = hdao.signInUtente(u.getNome(),u.getCognome(),u.getEmail(),u.getUsername(),u.getPassword());
            } else{
                success = hdao.signInOrganizzatore(u.getNome(),u.getCognome(),u.getEmail(),u.getUsername(),u.getPassword());
            }

            if (!success) {
                JOptionPane.showMessageDialog(getSignIn(), "Username già in uso. Scegli un altro.");
            } else {
                JOptionPane.showMessageDialog(getSignIn(), "Registrazione completata!");
                mainController.showLogin();
            }
        }
    }

    public void showLogin(){
        mainController.showLogin();
    }
}

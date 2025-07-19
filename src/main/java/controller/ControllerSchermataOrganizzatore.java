package controller;

import gui.SchermataOrganizzatore;
import implementazionepostgresdao.HackathonDAO;
import model.*;

import javax.swing.*;

public class ControllerSchermataOrganizzatore {
    private final SchermataOrganizzatore schermataOrganizzatore;

    private final MainController mainController;
    HackathonDAO hdao = new HackathonDAO();

    public ControllerSchermataOrganizzatore(MainController mainController, Organizzatore organizzatore) {
        this.mainController = mainController;
        this.schermataOrganizzatore = new SchermataOrganizzatore(this,organizzatore);
    }
    public JPanel getSchermataOrganizzatore() {
        return schermataOrganizzatore.getMainPanel();
    }

    public void schermataOrganizzaHackathon(Organizzatore organizzatore) {
        if(organizzatore.getHackathonID() == null)
            mainController.showOrganizzaHackathon(organizzatore);
    }

    public void logout() {
        mainController.logout();
    }

    public void mostraProblemaHackathon(Organizzatore organizzatore) {
        if(organizzatore.getHackathonID() != null) {
            Hackathon h = hdao.getHackathonByID(organizzatore.getHackathonID());
            String problema = h.getProblema();
            String problemaHTML = "<html>" + problema.replaceAll("(.{50})", "$1<br>") + "</html>";
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), problemaHTML);
        } else {
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(),"Non partecipi a nessun Hackathon");
        }
    }
    public void getSchermataGestioneHack(Organizzatore organizzatore) {
        mainController.showSchermataGestioneHack(organizzatore);
    }

    public void showSchermataClassifica(Integer hackathonID, Organizzatore organizzatore) {
        mainController.showSchermataClassifica(hackathonID, organizzatore);
    }

} //modificato

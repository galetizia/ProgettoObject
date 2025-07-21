package controller;

import gui.OrganizzaHackathon;

import implementazionepostgresdao.HackathonDAO;
import model.Hackathon;
import model.Organizzatore;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ControllerOrganizzaHackathon {
    private final OrganizzaHackathon schermataOrganizzaHackathon;

    private final MainController mainController;
    private final Organizzatore organizzatore;
    HackathonDAO hdao = new HackathonDAO();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ControllerOrganizzaHackathon(MainController mainController, Organizzatore organizzatore) {
        this.mainController = mainController;
        this.organizzatore = organizzatore;
        this.schermataOrganizzaHackathon = new OrganizzaHackathon(this);
    }

    public JPanel getOrganizzaHackathon() {return schermataOrganizzaHackathon.getMainPanel();}

    public void listeHackathon(JList<String> listElenchi, DefaultListModel<String> modelLista, JScrollPane panelElenchi) {
        List<Hackathon> hackathons = hdao.getHackathons();
        if(hackathons.isEmpty()){
            JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Nessuna Hackathon attiva!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        modelLista.clear();
        for (Hackathon h : hackathons) {
            modelLista.addElement(h.getNome()+" (ID:"+h.getID()+")");
        }
        listElenchi.revalidate();
        listElenchi.repaint();
        panelElenchi.setVisible(true);
    }

    public void creaHackathon(JTextField titoloTextField, JTextField sedeTextField, JTextField problemaTextField, JTextField dataInizioTextField, JTextField dataFineTextField, JTextField maxIscrTextField, JTextField maxDimTeamTextField) {

        String titolo = titoloTextField.getText();
        String sede = sedeTextField.getText();
        String problema = problemaTextField.getText();
        LocalDate dataInizio;
        LocalDate dataFine;
        int maxIscr;
        int maxDimTeam;

        if(titolo.isEmpty() || sede.isEmpty() || problema.isEmpty()) {
            JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Inserire tutti i campi!!");
            return;
        }
        try {
            dataInizio = LocalDate.parse(dataInizioTextField.getText(), formatter);
        } catch (DateTimeParseException datE) {
            JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Il campo Data Fine deve avere formato DD/MM/yyyy", "Errore di formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            dataFine = LocalDate.parse(dataFineTextField.getText(), formatter);
        } catch (DateTimeParseException datE) {
            JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Il campo Data Inizio deve avere formato DD/MM/yyyy", "Errore di formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            maxIscr = Integer.parseInt(maxIscrTextField.getText());
        } catch (NumberFormatException numE) {
            JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Il campo Max Iscritti deve essere un numero intero", "Errore di formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            maxDimTeam = Integer.parseInt(maxDimTeamTextField.getText());
        } catch (NumberFormatException numE) {
            JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Il campo Max Dim. Team deve essere un numero intero", "Errore di formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Hackathon nuovaHackathon = new Hackathon(titolo, sede,  dataInizio, dataFine, problema, maxIscr, maxDimTeam);
        hdao.caricaHackathonDB(nuovaHackathon, organizzatore);
        JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Nuova Hackathon caricata con successo","Success", JOptionPane.INFORMATION_MESSAGE);
        this.indietro();
    }
    public void indietro() {
        mainController.showSchermataOrganizzatore(organizzatore);
    }
}

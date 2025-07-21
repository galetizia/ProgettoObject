package controller;

import gui.IscrizioneTeam;

import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.TeamDAO;
import implementazionepostgresdao.UtenteDAO;
import model.Hackathon;
import model.Team;
import model.Utente;

import javax.swing.*;
import java.util.List;

public class ControllerIscrizioneTeam {

    private final IscrizioneTeam schermataIscrizioneTeam;

    private final MainController mainController;
    private final Utente utente;
    private final TeamDAO tdao = new TeamDAO();
    private final HackathonDAO hdao = new HackathonDAO();
    private final UtenteDAO udao = new UtenteDAO();

    private static final String ERROR = "Error";
    private static final String ERROREFORMATO = "Errore di formato";
    private static final String ATTENZIONE = "Attenzione";

    public ControllerIscrizioneTeam(MainController mainController, Utente utente) {
        this.mainController = mainController;
        this.schermataIscrizioneTeam = new IscrizioneTeam(this);
        this.utente=utente;
    }

    public void creazioneTeam(JTextField nomeNuovoTeamTextField, JTextField creaTeamIDTextField) {

        String nome = nomeNuovoTeamTextField.getText();
        String idHackathon = creaTeamIDTextField.getText();

        if(nome.isEmpty() || idHackathon.isEmpty()) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Inserire tutti i campi!", ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id;
        try{
            id = Integer.parseInt(idHackathon);
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "L'ID deve essere un numero intero valido", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(hdao.getHackathonByID(id) == null) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "ID hackathon non valido, inserire un hackathon esistente!" , ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<Team> teams = hdao.getTeamByHackathon(id);

        if(teams.size() >= hdao.getMaxDimTeam(id)) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Raggiunto numero massimo di Team!" , ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        Team t = new Team(nome,id);
        tdao.caricaTeamNelDB(t,utente);
        JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Il team caricato con successo","Success", JOptionPane.INFORMATION_MESSAGE);
        showUtente();
    }

    public void visualizzaHackathonAttive(JList<String> list, DefaultListModel<String> modelList) {
        List<Hackathon> hackathons = hdao.getHackathons();
        modelList.clear();

        modelList.addElement("------- Elenco Hackathon Attive --------");

        for (Hackathon h : hackathons) {
            List<Team> teams = hdao.getTeamByHackathon(h.getID());
            modelList.addElement(h.getNome()+" (ID: "+h.getID()+") "+"("+teams.size()+"/"+hdao.getMaxIscritti(h.getID())+")");
        }

        list.revalidate();
        list.repaint();
        schermataIscrizioneTeam.setVisiblePanelElenchi();
    }

    public JButton visibilitaHackathonAttive(JList<String> list, DefaultListModel<String> modelList, JButton confermaListaTeamButton, JButton confermaIscrTramiteButton, JPanel panelIscrizione, JButton hackathonAttiveButton, JButton ultimoPulsantePremuto) {

        if(confermaListaTeamButton.isVisible()) {
            schermataIscrizioneTeam.setVisibilityListaTeam(false);
        }

        if(confermaIscrTramiteButton.isVisible()) {
            schermataIscrizioneTeam.setVisibilityIscrivitiTeam(false);
        }

        if(panelIscrizione.isVisible()) {
            panelIscrizione.setVisible(false);
        }

        if(ultimoPulsantePremuto == hackathonAttiveButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        }else{
            visualizzaHackathonAttive(list, modelList);
            ultimoPulsantePremuto = hackathonAttiveButton;
        }

        return ultimoPulsantePremuto;
    }

    public JButton visibilitaListaTeam(JList<String> list, DefaultListModel<String> modelList, JButton listaTeamButton, JButton confermaIscrTramiteButton, JPanel panelIscrizione, JTextField hackathonIDTextField, JButton ultimoPulsantePremuto) {

        if(confermaIscrTramiteButton.isVisible()) {
            schermataIscrizioneTeam.setVisibilityIscrivitiTeam(false);
        }

        if(panelIscrizione.isVisible()) {
            panelIscrizione.setVisible(false);
        }

        schermataIscrizioneTeam.setVisibilityListaTeam(!hackathonIDTextField.isVisible());

        if(ultimoPulsantePremuto == listaTeamButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        }else{
            visualizzaHackathonAttive(list, modelList);
            ultimoPulsantePremuto = listaTeamButton;
        }

        return ultimoPulsantePremuto;
    }

    public JButton visibilitaIscrivitiTeam(JButton confermaListaTeamButton, JButton iscrivitiAdUnTeamButton, JPanel panelIscrizione, JTextField teamIDTextField, JButton ultimoPulsantePremuto) {

        if(confermaListaTeamButton.isVisible()) {
            schermataIscrizioneTeam.setVisibilityListaTeam(false);
        }

        if(panelIscrizione.isVisible()) {
            panelIscrizione.setVisible(false);
        }

        schermataIscrizioneTeam.setVisibilityIscrivitiTeam(!teamIDTextField.isVisible());

        if(ultimoPulsantePremuto == iscrivitiAdUnTeamButton)
            ultimoPulsantePremuto = null;
        else
            ultimoPulsantePremuto = iscrivitiAdUnTeamButton;


        return ultimoPulsantePremuto;
    }

    public JButton visibilitaCreaTeam(DefaultListModel<String> modelList, JButton creaTeamButton, JButton confermaListaTeamButton, JButton confermaIscrTramiteButton, JPanel panelIscrizione, JButton ultimoPulsantePremuto){

        modelList.clear();

        if(confermaListaTeamButton.isVisible()) {
            schermataIscrizioneTeam.setVisibilityListaTeam(false);
        }
        if(confermaIscrTramiteButton.isVisible()) {
            schermataIscrizioneTeam.setVisibilityIscrivitiTeam(false);
        }

        if(ultimoPulsantePremuto == creaTeamButton) {
            ultimoPulsantePremuto = null;
            panelIscrizione.setVisible(false);
        }
        else {
            panelIscrizione.setVisible(true);
            ultimoPulsantePremuto = creaTeamButton;
        }

        return ultimoPulsantePremuto;
    }

    public void iscrizioneTeam(JTextField idTeamTextField) {

        String idTeamTxt = idTeamTextField.getText();

        if(idTeamTxt.isEmpty()) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Inserisci un ID di un team" , ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int id= Integer.parseInt(idTeamTxt);
            Team t = tdao.getTeamByID(id);
            if(t == null) {
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Il Team inserito non esiste!" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<Utente> teams = tdao.membriTeam(id);

            if(teams.size() >= (hdao.getMaxDimTeam(hdao.getHackathonByTeam(id)))) {
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Team Pieno" , ERROR, JOptionPane.ERROR_MESSAGE);
            } else {
                udao.changeIDTeam(t, utente);
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Sei stato aggiunto al Team: "+t.getNome(), "Success", JOptionPane.INFORMATION_MESSAGE);
                showUtente();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "L'ID del Team deve essere un numero intero.", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
        }
    }


    public void visualizzaTeamHackathon(JTextField idHackathonTextField, JList<String> list,DefaultListModel<String> modelList) {
        String idHackathonTxt = idHackathonTextField.getText();
        if(idHackathonTxt.isEmpty()) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Inserisci un ID Hackathon" , ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int hackathonID = Integer.parseInt(idHackathonTxt);
            List<Team> teams = hdao.getTeamByHackathon(hackathonID);
            modelList.clear();

            if(teams.isEmpty()) {
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Nessun Team iscritto a quest Hackathon!", ATTENZIONE, JOptionPane.WARNING_MESSAGE);
                return;
            }

            modelList.addElement("---------- Elenco Team -----------");

            for (Team t : teams) {
                modelList.addElement(t.getNome()+" (ID: "+t.getId()+") "+"("+teams.size()+"/"+hdao.getMaxDimTeam(hackathonID)+")");
            }

            list.revalidate();
            list.repaint();
            schermataIscrizioneTeam.setVisiblePanelElenchi();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "L'ID Hackathon deve essere un numero valido.", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
        }


    }

    public JPanel getIscrizioneTeam() {return schermataIscrizioneTeam.getMainPanel();}

    public void showUtente() {mainController.showSchermataUtente(utente); }

}

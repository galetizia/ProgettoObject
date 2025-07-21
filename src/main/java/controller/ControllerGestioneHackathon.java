package controller;

import gui.GestioneHackathon;
import implementazionepostgresdao.*;
import model.Giudice;
import model.Organizzatore;
import model.Team;
import model.Utente;

import javax.swing.*;
import java.util.List;

public class ControllerGestioneHackathon {
    private final GestioneHackathon schermataGestioneHackathon;
    private final MainController mainController;
    private final Organizzatore organizzatoreLoggato;
    OrganizzatoreDAO odao = new OrganizzatoreDAO();
    TeamDAO tdao = new TeamDAO();
    HackathonDAO hdao = new HackathonDAO();
    UtenteDAO udao = new UtenteDAO();
    GiudiceDAO gdao = new GiudiceDAO();

    public ControllerGestioneHackathon(MainController mainController, Organizzatore organizzatore) {
        this.mainController = mainController;
        this.schermataGestioneHackathon = new GestioneHackathon(this, organizzatore);
        this.organizzatoreLoggato = organizzatore;
    }

    public void terminaHackathon() {
        int conferma = JOptionPane.showConfirmDialog(schermataGestioneHackathon.getMainPanel(), "Sei sicuro di voler terminare l'Hackathon prima della data finale?", "Conferma", JOptionPane.YES_NO_OPTION);
        if (conferma == JOptionPane.YES_OPTION) {
            odao.terminaHackathon(organizzatoreLoggato.getHackathonID());
            organizzatoreLoggato.setHackathonID(null);
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Hai terminato l'Hackathon con successo.");
            mainController.showSchermataOrganizzatore(organizzatoreLoggato);
        }
    }

    public void mostraTeams(JList<String> list, DefaultListModel<String> modelList, JScrollPane panel, Organizzatore organizzatore) {
        List<Team> teams = hdao.getTeamByHackathon(organizzatore.getHackathonID());
        modelList.clear();
        if(teams.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Non sono presenti team","INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        modelList.addElement("--------------------------- Elenco Team ---------------------------");

        for (Team t : teams) {
            modelList.addElement(t.getNome() +" (ID:"+t.getId()+")");
        }
        list.revalidate();
        list.repaint();
        panel.setVisible(true);

    }

    public void mostraUtenti(JList<String> list, DefaultListModel<String> modelList, JScrollPane panel, Organizzatore organizzatore){
        List<Utente> users = hdao.getUtenti(organizzatore.getHackathonID());
        modelList.clear();
        if(users.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Non sono presenti utenti","INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        modelList.addElement("-------------------------- Elenco Utenti --------------------------");
        for (Utente u : users) {
            modelList.addElement(u.getUsername());
        }
        list.revalidate();
        list.repaint();
        panel.setVisible(true);
    }

    public void mostraGiudici(JList<String> list, DefaultListModel<String> modelList, JScrollPane panel, Organizzatore organizzatore){
        List<Giudice> giudici = hdao.getGiudici(organizzatore.getHackathonID());
        modelList.clear();

        if(giudici.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Non sono presenti giudici","INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        modelList.addElement("-------------------------- Elenco Giudici -------------------------");
        for (Giudice g : giudici) {
            modelList.addElement(g.getUsername());
        }
        list.revalidate();
        list.repaint();
        panel.setVisible(true);
    }

    public boolean mostraPotenzialiGiudici(JList<String> list, DefaultListModel<String> modelList, JScrollPane panel){
        List<Utente> potenzialiGiudici = hdao.getPotenzialiGiudici();
        modelList.clear();

        if(potenzialiGiudici.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Non sono presenti potenziali giudici da inserire","INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return false ;
        }
        modelList.addElement("-------------------- Elenco Potenziali Giudici --------------------");
        for (Utente pG : potenzialiGiudici) {
            modelList.addElement(pG.getUsername());
        }
        list.revalidate();
        list.repaint();
        panel.setVisible(true);
        return true;
    }

    public void aggiungiGiudice(JTextField usernameTextField, Organizzatore organizzatore) {
        String usern = usernameTextField.getText();
        if(usern.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Inserire username!" , "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!odao.aggiungiGiudice(usern, organizzatore.getHackathonID())){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "L'utente non esiste/è membro di un team!" , "Error", JOptionPane.ERROR_MESSAGE);
            usernameTextField.setText("");
            return;
        }
        JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Aggiunto giudice!" , "Success", JOptionPane.INFORMATION_MESSAGE);
        usernameTextField.setText("");

    }

    public void gestioneRimozioni(JCheckBox utenteCheckBox, JCheckBox giudiceCheckBox,JCheckBox teamCheckBox, JTextField idTextField,Organizzatore organizzatore){
        if(!utenteCheckBox.isSelected() && !giudiceCheckBox.isSelected() && !teamCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Inserire un ruolo" , "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(utenteCheckBox.isSelected()) rimozioneUtente(idTextField, organizzatore.getHackathonID());
        if(giudiceCheckBox.isSelected()) rimozioneGiudice(idTextField, organizzatore.getHackathonID());
        if(teamCheckBox.isSelected()) rimozioneTeam(idTextField, organizzatore.getHackathonID());
    }

    public void rimozioneUtente(JTextField idTextField, Integer hackathonID) {
        String username = idTextField.getText();
        if(username.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Inserire Username" , "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Utente u = udao.findUtenteByUsername(idTextField.getText());

        if(u == null) {
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Utente "+username+" non trovato" , "Error", JOptionPane.ERROR_MESSAGE);
            idTextField.setText("");
            return;
        }
        if(!u.getHackathonID().equals(hackathonID)){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "L'utente non è iscritto alla sua Hackathon" , "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int conferma = JOptionPane.showConfirmDialog(schermataGestioneHackathon.getMainPanel(), "Sei sicuro di voler rimuovere "+username+"?" ,
                "Conferma", JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Utente rimosso.");
            idTextField.setText("");
            odao.removeUtente(username);
        }
    }

    public void rimozioneGiudice(JTextField idTextField, Integer hackathonID) {
        String username = idTextField.getText();
        if(username.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Inserire Username" , "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Giudice g = gdao.findGiudiceByUsername(username);

        if(g == null) {
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Giudice "+username+" non trovato" , "Error", JOptionPane.ERROR_MESSAGE);
            idTextField.setText("");
            return;
        }
        if(!g.getHackathonID().equals(hackathonID)){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Questo giudice non è presente nella sua Hackathon" , "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int conferma = JOptionPane.showConfirmDialog(schermataGestioneHackathon.getMainPanel(), "Sei sicuro di voler declassare "+username +"?" ,
                "Conferma", JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Giudice declassato ad utente.");
            idTextField.setText("");
            odao.removeGiudice(username);
        }
    }


    public void rimozioneTeam(JTextField idTextField, Integer hackathonID){
        String idTxt = idTextField.getText();

        if(idTxt.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Inserire ID" , "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id;
        try{
            id = Integer.parseInt(idTxt);
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "L'ID deve essere un numero intero valido", "Errore di formato", JOptionPane.ERROR_MESSAGE);
            idTextField.setText("");
            return;
        }

        Team t = tdao.getTeamByID(id);
        if(t == null) {
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Team "+idTextField.getText()+" non trovato" , "Error", JOptionPane.ERROR_MESSAGE);
            idTextField.setText("");
            return;
        }
        if(!t.getHackathonID().equals(hackathonID)){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Questo Team non è iscritto alla sua Hackathon" , "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int conferma = JOptionPane.showConfirmDialog(schermataGestioneHackathon.getMainPanel(), "Sei sicuro di voler rimuovere il Team "+idTextField.getText()+"?" ,
                "Conferma", JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Team rimosso.");
            idTextField.setText("");
            odao.removeTeam(id);
        }
    }

    public JPanel getGestioneHackathon() {
        return schermataGestioneHackathon.getMainPanel();
    }

    public void getSchermataOrganizzatore() {
        mainController.showSchermataOrganizzatore(organizzatoreLoggato);
    }

    public JButton visibilitaAggGiudice(JLabel idLabel, JList<String> list, DefaultListModel<String> modelList, JScrollPane panelHackathon, JLabel username, JButton ultimoPulsantePremuto, JButton aggiungiGiudiceButton){
        if(idLabel.isVisible()) {
            schermataGestioneHackathon.setVisibilityRimozione(false);
        }
        if(!mostraPotenzialiGiudici(list, modelList, panelHackathon)){
            schermataGestioneHackathon.setVisibilityAggGiudice(false);
            modelList.clear();
            return null;
        }
        boolean check=!username.isVisible();
        schermataGestioneHackathon.setVisibilityAggGiudice(check);

        if (ultimoPulsantePremuto == aggiungiGiudiceButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            ultimoPulsantePremuto = aggiungiGiudiceButton;
        }
        return ultimoPulsantePremuto;
    }

    public JButton visibilitaElencoGiudici(JLabel username, JLabel idLabel, JButton ultimoPulsantePremuto, JButton elencoGiudiciButton, JList<String> list, DefaultListModel<String> modelList, Organizzatore organizzatore){
        if(username.isVisible()) {
            schermataGestioneHackathon.setVisibilityAggGiudice(false);
        }
        if(idLabel.isVisible()) {
            schermataGestioneHackathon.setVisibilityRimozione(false);
        }

        if (ultimoPulsantePremuto == elencoGiudiciButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            mostraGiudici(list, modelList, schermataGestioneHackathon.getPanelHackathon(), organizzatore);
            ultimoPulsantePremuto = elencoGiudiciButton;
        }
        return ultimoPulsantePremuto;
    }

    public JButton visibilitaElencoUtenti(JLabel username, JLabel idLabel, JButton ultimoPulsantePremuto, JButton elencoUtentiButton, JList<String> list, DefaultListModel<String> modelList, Organizzatore organizzatore){
        if(username.isVisible()) {
            schermataGestioneHackathon.setVisibilityAggGiudice(false);
        }
        if(idLabel.isVisible()) {
            schermataGestioneHackathon.setVisibilityRimozione(false);
        }
        if (ultimoPulsantePremuto == elencoUtentiButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            mostraUtenti(list, modelList, schermataGestioneHackathon.getPanelHackathon(), organizzatore);
            ultimoPulsantePremuto = elencoUtentiButton;
        }
        return ultimoPulsantePremuto;
    }

    public JButton visibilitaElencoTeam(JLabel username, JLabel idLabel, JButton ultimoPulsantePremuto, JButton elencoTeamsButton, JList<String> list, DefaultListModel<String> modelList, Organizzatore organizzatore){
        if(username.isVisible()) {
            schermataGestioneHackathon.setVisibilityAggGiudice(false);
        }
        if(idLabel.isVisible()) {
            schermataGestioneHackathon.setVisibilityRimozione(false);
        }
        if (ultimoPulsantePremuto == elencoTeamsButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            mostraTeams(list, modelList, schermataGestioneHackathon.getPanelHackathon(), organizzatore);
            ultimoPulsantePremuto = elencoTeamsButton;
        }
        return ultimoPulsantePremuto;
    }

    public JButton visibilitaRimozione(JLabel idLabel, JButton ultimoPulsantePremuto, JButton rimozioneUtenteGiudiceTeamButton, DefaultListModel<String> modelList, JCheckBox utenteCheckBox, JCheckBox giudiceCheckBox, JCheckBox teamCheckBox){
        if(schermataGestioneHackathon.getUsernameLabel().isVisible()) {
            schermataGestioneHackathon.setVisibilityAggGiudice(false);
        }

        schermataGestioneHackathon.setVisibilityRimozione(!idLabel.isVisible());

        if(!utenteCheckBox.isSelected() && !giudiceCheckBox.isSelected() && !teamCheckBox.isSelected())
            modelList.clear();

        if (ultimoPulsantePremuto == rimozioneUtenteGiudiceTeamButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            ultimoPulsantePremuto = rimozioneUtenteGiudiceTeamButton;
        }
        schermataGestioneHackathon.getMainPanel().revalidate();
        schermataGestioneHackathon.getMainPanel().repaint();
        return ultimoPulsantePremuto;
    }





}

package controller;

import gui.TeamSchermataUtente;
import implementazionepostgresdao.OrganizzatoreDAO;
import implementazionepostgresdao.TeamDAO;
import model.Aggiornamento;
import model.Team;
import model.Utente;

import javax.swing.*;
import java.util.List;

public class ControllerTeamSchermataUtente {

    private final TeamSchermataUtente teamSchermataUtente;
    TeamDAO tdao = new TeamDAO();
    OrganizzatoreDAO odao = new OrganizzatoreDAO();

    private final MainController maincontroller;

    public ControllerTeamSchermataUtente(MainController maincontroller, Team team, Utente utente) {
        this.teamSchermataUtente = new TeamSchermataUtente(this, team, utente);
        this.maincontroller = maincontroller;
    }

    public void abbandonaTeam(Utente utente) {
        int conferma = JOptionPane.showConfirmDialog(teamSchermataUtente.getMainPanel(), "Sei sicuro di voler abbandonare il team?",
                "Conferma", JOptionPane.YES_NO_OPTION);
        if (conferma == JOptionPane.YES_OPTION) {
            int id = utente.getTeamID();
            tdao.rimuoviUtenteDalTeam(utente.getUsername());
            utente.setTeamID(null);
            utente.setHackathonID(null);
            List<Utente> membri = tdao.membriTeam(id);
            if (membri.isEmpty()) {
                odao.removeTeam(id);
            }
            JOptionPane.showMessageDialog(teamSchermataUtente.getMainPanel(), "Hai abbandonato il team con successo.");
        }
        maincontroller.showSchermataUtente(utente);
    }

    public JPanel getTeamSchermataUtente() { return teamSchermataUtente.getMainPanel(); }

    public void showSchermataUtente(Utente utente) {
        maincontroller.showSchermataUtente(utente);
    }

    public JButton visualizzaMembri(Team team, JList<String> listaUtenti, DefaultListModel<String> modelListUtenti, JButton membriButton, JButton ultimoPulsantePremuto) {
        List<Utente> membri = tdao.membriTeam(team.getId());

        modelListUtenti.clear();

        if (ultimoPulsantePremuto != membriButton) {

            modelListUtenti.addElement("------- Elenco dei membri del team -------");

            for (Utente u : membri) {
                modelListUtenti.addElement(u.getNome() + " " + u.getCognome());
            }

            ultimoPulsantePremuto = membriButton;
        }
        else
            ultimoPulsantePremuto = null;


        listaUtenti.revalidate();
        listaUtenti.repaint();
        teamSchermataUtente.setVisiblePanelUtenti();

        return ultimoPulsantePremuto;
    }

    public void visualizzaAggiornamento(Utente utente){
        if(tdao.getUltimoAggiornamento(utente.getTeamID()) != null) {
            String aggiornamento = tdao.getUltimoAggiornamento(utente.getTeamID());
            String aggiornamentoHTML = "<html>" + aggiornamento.replaceAll("(.{50})", "$1<br>") + "</html>";
            JOptionPane.showMessageDialog(teamSchermataUtente.getMainPanel(), aggiornamentoHTML, "Aggiornamento", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(teamSchermataUtente.getMainPanel(), "Nessun aggiornamento presente!", "Error", JOptionPane.WARNING_MESSAGE);
    }


    public void caricaAggiornamento(Utente utente, JTextField nomeTextField, JTextField documentoTextField, JCheckBox elaboratoFinaleCheckBox) {

        if(tdao.getElaboratoFinaleUltimoAggiornamento(utente.getTeamID())){
            JOptionPane.showMessageDialog(teamSchermataUtente.getMainPanel(), "Il team a cui appartieni ha già caricato l'elaborato finale!", "Impossibile caricare", JOptionPane.WARNING_MESSAGE);
            nomeTextField.setText(""); documentoTextField.setText("");
            return;
        }

        String nomeAggiornamento = nomeTextField.getText();
        String documentoAggiornamento = documentoTextField.getText();

        if(nomeAggiornamento.isEmpty() || documentoAggiornamento.isEmpty()){
            JOptionPane.showMessageDialog(teamSchermataUtente.getMainPanel(), "Inserire tutti i campi!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Aggiornamento aggiornamento = new Aggiornamento(nomeAggiornamento, documentoAggiornamento, utente.getTeamID(), utente.getUsername());
        boolean isFinale = elaboratoFinaleCheckBox.isSelected();
        aggiornamento.setElaboratoFinale(isFinale);

        tdao.caricaAggiornamentoDB(utente, aggiornamento);
        nomeTextField.setText(""); documentoTextField.setText("");
        JOptionPane.showMessageDialog(teamSchermataUtente.getMainPanel(), isFinale ? "Elaborato Finale Caricato!" : "Aggiornamento Caricato!" , "Success", JOptionPane.INFORMATION_MESSAGE);
    }

}

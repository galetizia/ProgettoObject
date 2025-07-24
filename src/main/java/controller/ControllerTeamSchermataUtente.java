package controller;

import gui.TeamSchermataUtente;
import implementazionepostgresdao.OrganizzatoreDAO;
import implementazionepostgresdao.TeamDAO;
import model.Aggiornamento;
import model.Team;
import model.Utente;

import javax.swing.*;
import java.util.List;

/**
 * Controller per la gestione delle operazioni da parte di un utente all'interno di un team.
 * Coordina l'interazione tra la GUI {@link TeamSchermataUtente}, i DAO e i modelli {@link Team}, {@link Utente}, {@link Aggiornamento}.
 */
public class ControllerTeamSchermataUtente {

    /** Vista grafica della schermata del team per l'utente. */
    private final TeamSchermataUtente teamSchermataUtente;

    /** DAO per operazioni sul team. */
    TeamDAO tdao = new TeamDAO();

    /** DAO per operazioni dell'organizzatore. */
    OrganizzatoreDAO odao = new OrganizzatoreDAO();

    /** Controller principale dell'applicazione. */
    private final MainController maincontroller;

    /**
     * Costruttore che inizializza il controller con la GUI, il team e l'utente associati.
     *
     * @param maincontroller il controller principale
     * @param team il team a cui l'utente appartiene
     * @param utente l'utente autenticato
     */
    public ControllerTeamSchermataUtente(MainController maincontroller, Team team, Utente utente) {
        this.teamSchermataUtente = new TeamSchermataUtente(this, team, utente);
        this.maincontroller = maincontroller;
    }

    /**
     * Permette all'utente di abbandonare il team corrente. Se l'utente è l'ultimo membro,
     * il team viene rimosso anche dal database.
     *
     * @param utente l'utente che abbandona il team
     */
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

    /**
     * Restituisce il pannello principale della schermata del team.
     *
     * @return il pannello principale della GUI del team
     */
    public JPanel getTeamSchermataUtente() { return teamSchermataUtente.getMainPanel(); }

    /**
     * Mostra la schermata principale dell'utente, tornando indietro dal pannello del team.
     *
     * @param utente l'utente da visualizzare nella schermata
     */
    public void showSchermataUtente(Utente utente) {
        maincontroller.showSchermataUtente(utente);
    }

    /**
     * Mostra nella GUI la lista dei membri del team selezionato.
     *
     * @param team il team corrente
     * @param listaUtenti la componente lista per visualizzare i membri
     * @param modelListUtenti il modello dati associato alla lista
     * @param membriButton il bottone per mostrare i membri
     * @param ultimoPulsantePremuto il riferimento all’ultimo bottone cliccato
     * @return il bottone attualmente premuto oppure null se deselezionato
     */
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

    /**
     * Visualizza l'ultimo aggiornamento caricato dal team dell'utente.
     * Se non esiste alcun aggiornamento, viene mostrato un messaggio di errore.
     *
     * @param utente l'utente che richiede la visualizzazione
     */
    public void visualizzaAggiornamento(Utente utente){
        if(tdao.getUltimoAggiornamento(utente.getTeamID()) != null) {
            String aggiornamento = tdao.getUltimoAggiornamento(utente.getTeamID());
            String aggiornamentoHTML = "<html>" + aggiornamento.replaceAll("(.{50})", "$1<br>") + "</html>";
            JOptionPane.showMessageDialog(teamSchermataUtente.getMainPanel(), aggiornamentoHTML, "Aggiornamento", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(teamSchermataUtente.getMainPanel(), "Nessun aggiornamento presente!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Permette all'utente di caricare un nuovo aggiornamento o un elaborato finale per il team.
     * Blocca il caricamento se è già stato inviato un elaborato finale.
     *
     * @param utente l'utente che carica l'aggiornamento
     * @param nomeTextField campo del nome aggiornamento
     * @param documentoTextField campo del contenuto aggiornamento
     * @param elaboratoFinaleCheckBox checkbox per indicare se è un elaborato finale
     */
    public void caricaAggiornamento(Utente utente, JTextField nomeTextField, JTextField documentoTextField, JCheckBox elaboratoFinaleCheckBox) {

        if(tdao.getElaboratoFinaleUltimoAggiornamento(utente.getTeamID())){
            JOptionPane.showMessageDialog(teamSchermataUtente.getMainPanel(), "Il team a cui appartieni ha già caricato l'elaborato finale!", "Impossibile caricare aggiornamenti", JOptionPane.WARNING_MESSAGE);
            nomeTextField.setText(""); documentoTextField.setText("");
            return;
        }

        String nomeAggiornamento = nomeTextField.getText();
        String documentoAggiornamento = documentoTextField.getText();

        if(nomeAggiornamento.isEmpty() || documentoAggiornamento.isEmpty()){
            JOptionPane.showMessageDialog(teamSchermataUtente.getMainPanel(), "Inserire tutti i campi!", "Attenzione", JOptionPane.WARNING_MESSAGE);
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

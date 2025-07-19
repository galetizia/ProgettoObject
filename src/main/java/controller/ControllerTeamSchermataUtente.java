package controller;

import gui.TeamSchermataUtente;
import implementazionepostgresdao.OrganizzatoreDAO;
import implementazionepostgresdao.TeamDAO;
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

    public void abbandonaTeam(Utente utente, int conferma) {
        if (conferma == JOptionPane.YES_OPTION) {
            TeamDAO tdao = new TeamDAO();
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

    public void visualizza(Team team, JList<String> listaUtenti, DefaultListModel<String> modelListUtenti) {
        List<Utente> membri = tdao.membriTeam(team.getId());
        modelListUtenti.clear();

        for (Utente u : membri) {
            modelListUtenti.addElement(u.getNome() + " " + u.getCognome());
        }

        listaUtenti.revalidate();
        listaUtenti.repaint();
        teamSchermataUtente.setVisiblePanelUtenti();
    }

}

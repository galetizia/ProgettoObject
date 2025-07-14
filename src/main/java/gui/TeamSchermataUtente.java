package gui;

import controller.ControllerTeamSchermataUtente;
import implementazionepostgresdao.TeamDAO;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TeamSchermataUtente {

    private JPanel mainPanel;
    private JButton membriButton;
    private JButton abbandonaButton;
    private JList<String> listaUtenti;
    private JScrollPane panelUtenti;
    private DefaultListModel<String> modelListUtenti;
    TeamDAO tdao = new TeamDAO();

    public TeamSchermataUtente(ControllerTeamSchermataUtente controller, Team team) {
        mainPanel.setPreferredSize(new Dimension(600,400));

        modelListUtenti = new DefaultListModel<>();
        listaUtenti.setModel(modelListUtenti);

        membriButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        membriButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        membriButton.addActionListener(e -> {
            List<Utente> membri = tdao.membriTeam(team.getId());
            modelListUtenti.clear();

            for (Utente u : membri) {
                modelListUtenti.addElement(u.getNome() + " " + u.getCognome());
            }

            listaUtenti.revalidate();
            listaUtenti.repaint();
            panelUtenti.setVisible(true);
        });
    }


    public JPanel getMainPanel(){
        return mainPanel;
    }
}

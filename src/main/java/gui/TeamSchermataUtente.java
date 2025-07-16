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
    private JButton indietroButton;
    private JLabel teamLabelName;
    private DefaultListModel<String> modelListUtenti;
    TeamDAO tdao = new TeamDAO();

    public TeamSchermataUtente(ControllerTeamSchermataUtente controller, Team team, Utente utente) {
        mainPanel.setPreferredSize(new Dimension(600,400));

        teamLabelName.setFont(new Font("Segoe UI", Font.BOLD, 38));
        teamLabelName.setText("Team: " + team.getNome());

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

        abbandonaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        abbandonaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        abbandonaButton.addActionListener(e -> {
            int conferma = JOptionPane.showConfirmDialog(
                    mainPanel,
                    "Sei sicuro di voler abbandonare il team?",
                    "Conferma",
                    JOptionPane.YES_NO_OPTION
            );

            if (conferma == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(mainPanel, "Hai abbandonato il team con successo.");
                controller.abbandonaTeam(utente);
            }
        });

        indietroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> {
            controller.showSchermataUtente(utente);
        });
    }


    public JPanel getMainPanel(){
        return mainPanel;
    }
}//Modificato - Fabio (AbbandonaButton) (Titolo del Team)

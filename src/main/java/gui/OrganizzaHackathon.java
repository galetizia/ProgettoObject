package gui;

import controller.ControllerOrganizzaHackathon;

import javax.swing.*;
import java.awt.*;

public class OrganizzaHackathon {
    private JPanel mainPanel;
    private JPanel panelIscrizione;
    private JTextField titoloTextField;
    private JTextField sedeTextField;
    private JButton confermaButton;
    private JScrollPane panelElenchi;
    private JList<String> listElenchi;
    private JButton organizzaNuovaHackathonButton;
    private JButton indietroButton;
    private JButton hackathonAttiveButton;
    private JTextField problemaTextField;
    private JTextField dataInizioTextField;
    private JTextField dataFineTextField;
    private JTextField maxIscrTextField;
    private JTextField maxDimTeamTextField;
    private JLabel area;
    private final DefaultListModel<String> modelLista;


    public OrganizzaHackathon(ControllerOrganizzaHackathon controller) {
        mainPanel.setPreferredSize(new Dimension(600,400));
        area.setFont(new Font("Segoe UI", Font.BOLD, 38));

        panelIscrizione.setVisible(false);

        modelLista = new DefaultListModel<>();
        listElenchi.setModel(modelLista);

        hackathonAttiveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hackathonAttiveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hackathonAttiveButton.addActionListener(ignored -> controller.listeHackathon(listElenchi, modelLista, panelElenchi));

        confermaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(ignored -> controller.creaHackathon(titoloTextField, sedeTextField, problemaTextField, dataInizioTextField, dataFineTextField, maxIscrTextField, maxDimTeamTextField));

        organizzaNuovaHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        organizzaNuovaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        organizzaNuovaHackathonButton.addActionListener(ignored -> panelIscrizione.setVisible(true));

        indietroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> controller.indietro());
    }

    public JPanel getMainPanel() {return mainPanel;}
}

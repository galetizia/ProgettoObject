package gui;

import controller.ControllerTeamSchermataUtente;
import implementazionepostgresdao.HackathonDAO;
import model.*;
import javax.swing.*;
import java.awt.*;

public class TeamSchermataUtente {

    private JPanel mainPanel;
    private JButton membriButton;
    private JButton abbandonaButton;
    private JList<String> listaUtenti;
    private JScrollPane panelUtenti;
    private JButton indietroButton;
    private JLabel teamLabelName;
    private JButton caricaAggiornamentoButton;
    private JTextField nomeTextField;
    private JButton confermaButton;
    private JTextField documentoTextField;
    private JButton visualizzaUltimoAggiornamentoButton;
    private JLabel nome;
    private JLabel documento;
    private JCheckBox elaboratoFinaleCheckBox;
    private final DefaultListModel<String> modelListUtenti;

    private static final String SEGOEUI = "Segoe UI";

    private final HackathonDAO hdao = new HackathonDAO();

    public TeamSchermataUtente(ControllerTeamSchermataUtente controller, Team team, Utente utente) {
        mainPanel.setPreferredSize(new Dimension(600,400));

        teamLabelName.setFont(new Font(SEGOEUI, Font.BOLD, 38));
        teamLabelName.setText("Team: " + team.getNome());

        modelListUtenti = new DefaultListModel<>();
        listaUtenti.setModel(modelListUtenti);

        nomeTextField.setVisible(false);
        documentoTextField.setVisible(false);
        confermaButton.setVisible(false);
        nome.setVisible(false);
        documento.setVisible(false);
        elaboratoFinaleCheckBox.setVisible(false);

        membriButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        membriButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        membriButton.addActionListener(ignored -> controller.visualizzaMembri(team, listaUtenti, modelListUtenti));

        caricaAggiornamentoButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        caricaAggiornamentoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        caricaAggiornamentoButton.addActionListener(ignored -> {

            if(hdao.isClassificaPubblicata(utente.getHackathonID())) {
                JOptionPane.showMessageDialog(mainPanel, "Classifica già pubblicata.\nImpossibile inserire nuovi aggiornamenti.", "Nessun aggiornamento", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!nome.isVisible()) {
                nomeTextField.setVisible(true);
                documentoTextField.setVisible(true);
                confermaButton.setVisible(true);
                nome.setVisible(true);
                documento.setVisible(true);
                elaboratoFinaleCheckBox.setVisible(true);
                return;
            }
            nomeTextField.setVisible(false);
            documentoTextField.setVisible(false);
            confermaButton.setVisible(false);
            nome.setVisible(false);
            documento.setVisible(false);
        });

        confermaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(ignored -> controller.caricaAggiornamento(utente, nomeTextField, documentoTextField, elaboratoFinaleCheckBox));

        abbandonaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        abbandonaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        abbandonaButton.addActionListener(ignored -> controller.abbandonaTeam(utente));

        indietroButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> controller.showSchermataUtente(utente));

        visualizzaUltimoAggiornamentoButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        visualizzaUltimoAggiornamentoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        visualizzaUltimoAggiornamentoButton.addActionListener(ignored -> controller.visualizzaAggiornamento(utente));
    }

    public void setVisiblePanelUtenti() {
        panelUtenti.setVisible(true);
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }
}

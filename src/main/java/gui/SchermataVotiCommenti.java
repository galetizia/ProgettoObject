package gui;

import controller.ControllerVotiCommenti;
import model.Giudice;

import javax.swing.*;
import java.awt.*;

public class SchermataVotiCommenti {
    private JButton elencoTeamButton;
    private JButton commentaUnAggiornamentoButton;
    private JButton valutaUnTeamButton;
    private JButton visualizzaAggiornamentoDiUnButton;
    private JTextField idTextField;
    private JButton confermavisualizzButton;
    private JLabel idTextF;
    private JLabel commIDT;
    private JTextField aggTextId;
    private JTextField commentoTextF;
    private JButton confermacommentoButton;
    private JList<String> list;
    private JTextField idTeamField;
    private JTextField votoField;
    private JButton confermavotoButton;
    private JButton indietroButton;
    private JLabel area;
    private JPanel mainPanel;
    private JScrollPane elencoPanel;
    private JLabel commentoF;
    private JLabel teamIdtextField;
    private JLabel votField;
    private final DefaultListModel<String> modelList;
    private JButton ultimoPulsantePremuto = null;

    public SchermataVotiCommenti(ControllerVotiCommenti controller, Giudice giudice) {
        mainPanel.setPreferredSize(new Dimension(500,350));

        area.setFont(new Font("Segoe UI", Font.BOLD, 38));
        idTextF.setVisible(false);
        idTextField.setVisible(false);
        aggTextId.setVisible(false);
        commentoTextF.setVisible(false);
        commentoF.setVisible(false);
        commIDT.setVisible(false);
        teamIdtextField.setVisible(false);
        votField.setVisible(false);
        confermavotoButton.setVisible(false);
        confermacommentoButton.setVisible(false);
        confermavisualizzButton.setVisible(false);
        idTeamField.setVisible(false);
        votoField.setVisible(false);

        modelList = new DefaultListModel<>();
        list.setModel(modelList);

        elencoTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        elencoTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoTeamButton.addActionListener(ignored ->
            ultimoPulsantePremuto=controller.visibilitaElencoTeam(aggTextId,teamIdtextField,idTextField,modelList, ultimoPulsantePremuto, elencoTeamButton, list));

        visualizzaAggiornamentoDiUnButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        visualizzaAggiornamentoDiUnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        visualizzaAggiornamentoDiUnButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaVisualizzaAgg(aggTextId, teamIdtextField, idTextField, modelList, ultimoPulsantePremuto, visualizzaAggiornamentoDiUnButton, list)
        );

        confermavisualizzButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermavisualizzButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermavisualizzButton.addActionListener(ignored -> controller.visualizzaAggiornamento(idTextField, modelList));

        commentaUnAggiornamentoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        commentaUnAggiornamentoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        commentaUnAggiornamentoButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaCommento(idTextField, teamIdtextField, aggTextId, modelList, ultimoPulsantePremuto, commentaUnAggiornamentoButton, list)
        );

        confermacommentoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermacommentoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermacommentoButton.addActionListener(ignored ->
            controller.caricaCommento(giudice, aggTextId, commentoTextF));

        valutaUnTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valutaUnTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        valutaUnTeamButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaValutazione(idTextField, teamIdtextField, commentoTextF, modelList, ultimoPulsantePremuto, valutaUnTeamButton, list));

        indietroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> controller.getSchermataGiudice());

        confermavotoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermavotoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermavotoButton.addActionListener(ignored -> controller.caricaVoto(giudice, idTeamField, votoField));

    }
    public JPanel getMainPanel(){
        return mainPanel;
    }

    private void setVisibility(boolean visible, JComponent... components) {
        for (JComponent c : components)
            c.setVisible(visible);
        }

    public void setVisiblePanelElenchi() {
        elencoPanel.setVisible(true);
    }

    public void setVisibilityCommentaAggiornamento(boolean visible){
        setVisibility(visible, commIDT,  aggTextId,  commentoF, commentoTextF, confermacommentoButton);
    }

    public void setVisibilityValuta(boolean visible){
        setVisibility(visible, teamIdtextField, idTeamField, votField, votoField, confermavotoButton);
    }

    public void setVisibilityVisualizzaAggiornamento(boolean visible){
        setVisibility(visible, idTextF, idTextField, confermavisualizzButton);
    }
}

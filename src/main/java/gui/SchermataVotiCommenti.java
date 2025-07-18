package gui;

import controller.ControllerVotiCommenti;
import implementazionepostgresdao.GiudiceDAO;
import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.TeamDAO;
import model.Giudice;
import model.Voto;

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
    private JButton elaboratiFinaliConsegnatiButton;
    private DefaultListModel<String> modelList;

    HackathonDAO hdao = new HackathonDAO();
    TeamDAO tdao = new TeamDAO();
    GiudiceDAO gdao = new GiudiceDAO();

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
        elencoTeamButton.addActionListener(e -> {
            controller.getTeams(giudice, list, modelList);
        });

        visualizzaAggiornamentoDiUnButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        visualizzaAggiornamentoDiUnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        visualizzaAggiornamentoDiUnButton.addActionListener(e -> {

            aggTextId.setVisible(false);
            commentoTextF.setVisible(false);
            commentoF.setVisible(false);
            commIDT.setVisible(false);
            teamIdtextField.setVisible(false);
            votField.setVisible(false);
            confermavotoButton.setVisible(false);
            confermacommentoButton.setVisible(false);
            idTeamField.setVisible(false);
            votoField.setVisible(false);

            if(!idTextField.isVisible()){
                idTextF.setVisible(true);
                idTextField.setVisible(true);
                confermavisualizzButton.setVisible(true);
            }else{
                idTextF.setVisible(false);
                idTextField.setVisible(false);
                confermavisualizzButton.setVisible(false);
            }
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        confermavisualizzButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermavisualizzButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermavisualizzButton.addActionListener(e -> {
            String idTxt = idTextField.getText();
            if(idTxt.isEmpty()){
                JOptionPane.showMessageDialog(mainPanel, "Inserire un ID di un team", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int id;
            try {
                id = Integer.parseInt(idTxt);
            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "L'ID deve essere un numero intero valido", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(tdao.getUltimoAggiornamento(id) == null){
                JOptionPane.showMessageDialog(mainPanel, "Nessun aggiornamento presente", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String a = tdao.getUltimoAggiornamento(id);
            String aHTML = "<html>" + a.replaceAll("(.{50})", "$1<br>") + "</html>";
            JOptionPane.showMessageDialog(mainPanel, aHTML, "Aggiornamento", JOptionPane.INFORMATION_MESSAGE);

        });

        commentaUnAggiornamentoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        commentaUnAggiornamentoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        commentaUnAggiornamentoButton.addActionListener(e -> {
            idTextF.setVisible(false);
            idTextField.setVisible(false);
            teamIdtextField.setVisible(false);
            votField.setVisible(false);
            confermavotoButton.setVisible(false);
            confermavisualizzButton.setVisible(false);
            idTeamField.setVisible(false);
            votoField.setVisible(false);

            if(!aggTextId.isVisible()){
                commIDT.setVisible(true);
                aggTextId.setVisible(true);
                commentoTextF.setVisible(true);
                commentoF.setVisible(true);
                confermacommentoButton.setVisible(true);
            } else{
                commIDT.setVisible(false);
                aggTextId.setVisible(false);
                commentoTextF.setVisible(false);
                commentoF.setVisible(false);
                confermacommentoButton.setVisible(false);
            }
        });

        confermacommentoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermacommentoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermacommentoButton.addActionListener(e -> {
            String idTxt = aggTextId.getText();
            String commentoTxt = commentoTextF.getText();

            if(idTxt.isEmpty() || commentoTxt.isEmpty()){
                JOptionPane.showMessageDialog(mainPanel, "Compilare tutti i campi!", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int id;
            try {
                id = Integer.parseInt(idTxt);
            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "L'ID deve essere un numero valido!", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer idAgg = tdao.getIdAggiornamentoByTeam(id);
            if (idAgg == null) {
                JOptionPane.showMessageDialog(mainPanel, "Questo team non ha ancora caricato aggiornamenti.\nImpossibile commentare.", "Nessun aggiornamento", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!gdao.haCommentatoAggiornamento(id,giudice)){
                gdao.saveCommento(commentoTxt, id, giudice);
                JOptionPane.showMessageDialog(mainPanel, "Commento salvato","Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(mainPanel, "Hai già commentato questo team!", "Errore", JOptionPane.ERROR_MESSAGE);


        });

        valutaUnTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valutaUnTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        valutaUnTeamButton.addActionListener(e -> {
            idTextF.setVisible(false);
            idTextField.setVisible(false);
            aggTextId.setVisible(false);
            commentoTextF.setVisible(false);
            commentoF.setVisible(false);
            commIDT.setVisible(false);
            confermacommentoButton.setVisible(false);
            confermavisualizzButton.setVisible(false);

            if(!teamIdtextField.isVisible()){
                teamIdtextField.setVisible(true);
                idTeamField.setVisible(true);
                votField.setVisible(true);
                votoField.setVisible(true);
                confermavotoButton.setVisible(true);
            } else{
                teamIdtextField.setVisible(false);
                idTeamField.setVisible(false);
                votField.setVisible(false);
                votoField.setVisible(false);
                confermavotoButton.setVisible(false);
            }
        });

        elaboratiFinaliConsegnatiButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        elaboratiFinaliConsegnatiButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elaboratiFinaliConsegnatiButton.addActionListener(e -> {
            controller.elaboratiFinali(list, modelList);
        });

        indietroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> {
            controller.getSchermataGiudice();
        });

        confermavotoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermavotoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermavotoButton.addActionListener(e -> {

            String idTeamText = idTeamField.getText();
            String votoText = votoField.getText();

            if(idTeamText.isEmpty() || votoText.isEmpty()){
                JOptionPane.showMessageDialog(mainPanel, "Compilare tutti i campi!", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idTeam;
            int valutazione;
            try {
                idTeam = Integer.parseInt(idTeamText);
                valutazione = Integer.parseInt(votoText);
            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Entrambi i campi devono essere un numero valido!", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(hdao.isClassificaPubblicata(giudice.getHackathonID())){
                JOptionPane.showMessageDialog(mainPanel, "Classifica già pubblicata.\nImpossibile valutare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                idTeamField.setText("");
                votoField.setText("");
                return;
            }
            if (!gdao.isElaboratoFinale(idTeam)) {
                JOptionPane.showMessageDialog(mainPanel, "Questo team non ha caricato l'elaborato finale.\nImpossibile valutare.", "Nessun aggiornamento", JOptionPane.WARNING_MESSAGE);
                idTeamField.setText("");
                votoField.setText("");
                return;
            }

            if(!gdao.controlloVotoTeam(idTeam,giudice.getUsername())) {
                if (valutazione < 0 || valutazione > 10) {
                    JOptionPane.showMessageDialog(mainPanel, "Entrambi i campi devono essere un numero valido!", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Voto voto = new Voto(valutazione, giudice, tdao.getTeamByID(idTeam));
                gdao.caricaVoto(voto);

                idTeamField.setText("");
                votoField.setText("");
                JOptionPane.showConfirmDialog(mainPanel, "Voto caricato con successo!", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(mainPanel, "Hai già votato questo team!", "Errore", JOptionPane.ERROR_MESSAGE);
            idTeamField.setText("");
            votoField.setText("");

        });

    }
    public JPanel getMainPanel(){
        return mainPanel;
    }

    public void setVisiblePanelElenchi() {
        elencoPanel.setVisible(true);
    }
}

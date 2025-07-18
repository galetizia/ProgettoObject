package gui;

import controller.ControllerTeamSchermataUtente;
import implementazionepostgresdao.TeamDAO;
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
    private final DefaultListModel<String> modelListUtenti;
    private boolean aggiornamentoVisibile = false;

    TeamDAO tdao = new TeamDAO();

    public TeamSchermataUtente(ControllerTeamSchermataUtente controller, Team team, Utente utente) {
        mainPanel.setPreferredSize(new Dimension(600,400));

        teamLabelName.setFont(new Font("Segoe UI", Font.BOLD, 38));
        teamLabelName.setText("Team: " + team.getNome());

        modelListUtenti = new DefaultListModel<>();
        listaUtenti.setModel(modelListUtenti);

        nomeTextField.setVisible(false);
        documentoTextField.setVisible(false);
        confermaButton.setVisible(false);
        nome.setVisible(false);
        documento.setVisible(false);

        membriButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        membriButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        membriButton.addActionListener(e -> controller.visualizza(team, listaUtenti, modelListUtenti));

        caricaAggiornamentoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        caricaAggiornamentoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        caricaAggiornamentoButton.addActionListener(e -> {

            if(!aggiornamentoVisibile) {
                nomeTextField.setVisible(true);
                documentoTextField.setVisible(true);
                confermaButton.setVisible(true);
                nome.setVisible(true);
                documento.setVisible(true);

                aggiornamentoVisibile = true;
            }
            else{
                nomeTextField.setVisible(false);
                documentoTextField.setVisible(false);
                confermaButton.setVisible(false);
                nome.setVisible(false);
                documento.setVisible(false);

                aggiornamentoVisibile = false;
            }
        });

        confermaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(e -> {

            String nomeAggiornamento = nomeTextField.getText();
            String documentoAggiornamento = documentoTextField.getText();

            if(!nomeAggiornamento.isEmpty() && !documentoAggiornamento.isEmpty()) {
                Aggiornamento aggiornamento = new Aggiornamento(nomeAggiornamento, documentoAggiornamento, utente.getTeamID(), utente.getUsername());
                tdao.caricaAggiornamentoDB(utente, aggiornamento);
                nomeTextField.setText("");
                documentoTextField.setText("");
                JOptionPane.showMessageDialog(mainPanel, "Aggiornamento Caricato!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(mainPanel, "Inserire tutti i campi!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        abbandonaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        abbandonaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        abbandonaButton.addActionListener(e -> {
            int conferma = JOptionPane.showConfirmDialog(mainPanel, "Sei sicuro di voler abbandonare il team?",
                    "Conferma", JOptionPane.YES_NO_OPTION);
            controller.abbandonaTeam(utente, conferma);
        });

        indietroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> controller.showSchermataUtente(utente));

        visualizzaUltimoAggiornamentoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        visualizzaUltimoAggiornamentoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        visualizzaUltimoAggiornamentoButton.addActionListener(e -> {
            if(tdao.getUltimoAggiornamento(utente.getTeamID()) != null) {
                String aggiornamento = tdao.getUltimoAggiornamento(utente.getTeamID());
                String aggiornamentoHTML = "<html>" + aggiornamento.replaceAll("(.{50})", "$1<br>") + "</html>";
                JOptionPane.showMessageDialog(mainPanel, aggiornamentoHTML, "Aggiornamento", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(mainPanel, "Nessun aggiornamento presente!", "Error", JOptionPane.ERROR_MESSAGE);
        });
    }

    public void setVisiblePanelUtenti() {
        panelUtenti.setVisible(true);
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }
}

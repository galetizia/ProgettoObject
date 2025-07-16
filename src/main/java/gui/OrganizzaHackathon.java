package gui;

import controller.ControllerOrganizzaHackathon;
import implementazionepostgresdao.HackathonDAO;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

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
    private DefaultListModel<String> modelLista;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    HackathonDAO hdao = new HackathonDAO();

    public OrganizzaHackathon(ControllerOrganizzaHackathon controller, Organizzatore organizzatore) {
        mainPanel.setPreferredSize(new Dimension(600,400));
        area.setFont(new Font("Segoe UI", Font.BOLD, 38));

        panelIscrizione.setVisible(false);

        modelLista = new DefaultListModel<>();
        listElenchi.setModel(modelLista);

        hackathonAttiveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hackathonAttiveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hackathonAttiveButton.addActionListener(e -> {
            List<Hackathon> hackathons = hdao.getHackathons();
            modelLista.clear();
            for (Hackathon h : hackathons) {
                modelLista.addElement(h.getNome()+" (ID:"+h.getID()+")");
            }
            listElenchi.revalidate();
            listElenchi.repaint();
            panelElenchi.setVisible(true);
        });

        confermaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(e -> {

            String titolo = titoloTextField.getText();
            String sede = sedeTextField.getText();
            String problema = problemaTextField.getText();
            LocalDate dataInizio;
            LocalDate dataFine;
            int maxIscr;
            int maxDimTeam;

            try {
                dataInizio = LocalDate.parse(dataInizioTextField.getText(), formatter);
            } catch (DateTimeParseException datE) {
                JOptionPane.showMessageDialog(mainPanel, "Il campo Data Fine deve avere formato DD/MM/yyyy", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                dataFine = LocalDate.parse(dataFineTextField.getText(), formatter);
            } catch (DateTimeParseException datE) {
                JOptionPane.showMessageDialog(mainPanel, "Il campo Data Inizio deve avere formato DD/MM/yyyy", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                maxIscr = Integer.parseInt(maxIscrTextField.getText());
            } catch (NumberFormatException numE) {
                JOptionPane.showMessageDialog(mainPanel, "Il campo Max Iscritti deve essere un numero intero", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                maxDimTeam = Integer.parseInt(maxDimTeamTextField.getText());
            } catch (NumberFormatException numE) {
                JOptionPane.showMessageDialog(mainPanel, "Il campo Max Dim. Team deve essere un numero intero", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(titolo.isEmpty() || sede.isEmpty() || problema.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Inserire tutti i campi!!");
                return;
            }
            else{
                Hackathon nuovaHackathon = new Hackathon(titolo, sede,  dataInizio, dataFine, problema, maxIscr, maxDimTeam);
                hdao.caricaHackathonDB(nuovaHackathon, organizzatore);
                JOptionPane.showMessageDialog(mainPanel, "Nuova Hackathon caricata con successo","Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        organizzaNuovaHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        organizzaNuovaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        organizzaNuovaHackathonButton.addActionListener(e -> {
            panelIscrizione.setVisible(true);
        });

        indietroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> {
            controller.indietro();
        });
    }

    public JPanel getMainPanel() {return mainPanel;}
}

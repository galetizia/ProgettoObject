package gui;

import controller.ControllerGestioneHackathon;
import implementazionepostgresdao.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GestioneHackathon {
    private JLabel area;
    private JList<String> list;
    private JButton elencoUtentiButton;
    private JButton elencoGiudiciButton;
    private JButton elencoTeamsButton;
    private JButton rimozioneUtenteGiudiceTeamButton;
    private JTextField idTextField;
    private JButton confermaButton;
    private JButton indietroButton;
    private JLabel idLabel;
    private JScrollPane panelHackathon;
    private JPanel mainpanel;
    private JCheckBox utenteCheckBox;
    private JCheckBox giudiceCheckBox;
    private JCheckBox teamCheckBox;
    private JButton terminaHackathonButton;
    private boolean rimozioniVisibili = false;

    private DefaultListModel<String> modelList;
    TeamDAO tdao = new TeamDAO();
    HackathonDAO hdao = new HackathonDAO();
    UtenteDAO udao = new UtenteDAO();
    OrganizzatoreDAO odao = new OrganizzatoreDAO();

    public GestioneHackathon(ControllerGestioneHackathon controller, Organizzatore organizzatore) {
        mainpanel.setPreferredSize(new Dimension(600, 400));
        area.setFont(new Font("Segoe UI", Font.BOLD, 38));

        idLabel.setVisible(false);
        idTextField.setVisible(false);
        confermaButton.setVisible(false);
        utenteCheckBox.setVisible(false);
        giudiceCheckBox.setVisible(false);
        teamCheckBox.setVisible(false);


        modelList = new DefaultListModel<>();
        list.setModel(modelList);

        elencoUtentiButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        elencoUtentiButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoUtentiButton.addActionListener(e -> {
            List<Utente> users = hdao.getUtenti(organizzatore.getHackathonID());
            modelList.clear();
            for (Utente u : users) {
                modelList.addElement(u.getUsername());
            }
            list.revalidate();
            list.repaint();
            panelHackathon.setVisible(true);

        });

        elencoGiudiciButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        elencoGiudiciButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoGiudiciButton.addActionListener(e -> {
            List<Giudice> giudici = hdao.getGiudici(organizzatore.getHackathonID());
            modelList.clear();
            for (Giudice g : giudici) {
                modelList.addElement(g.getUsername());
            }
            list.revalidate();
            list.repaint();
            panelHackathon.setVisible(true);
        });

        elencoTeamsButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        elencoTeamsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoTeamsButton.addActionListener(e -> {
            List<Team> teams = tdao.getTeamByHackathon(organizzatore.getHackathonID());
            modelList.clear();
            for (Team t : teams) {
                modelList.addElement(t.getNome() +" (ID:"+t.getId()+")");
            }
            list.revalidate();
            list.repaint();
            panelHackathon.setVisible(true);
        });

        terminaHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        terminaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        terminaHackathonButton.addActionListener(e -> {

            int conferma = JOptionPane.showConfirmDialog(mainpanel, "Sei sicuro di voler terminare l'Hackathon prima della data finale?", "Conferma", JOptionPane.YES_NO_OPTION);

            controller.terminaHackathon(conferma);

        });

        rimozioneUtenteGiudiceTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rimozioneUtenteGiudiceTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rimozioneUtenteGiudiceTeamButton.addActionListener(e -> {

            if(!rimozioniVisibili) {
                idLabel.setVisible(true);
                idTextField.setVisible(true);
                confermaButton.setVisible(true);
                utenteCheckBox.setVisible(true);
                giudiceCheckBox.setVisible(true);
                teamCheckBox.setVisible(true);

                rimozioniVisibili = true;
            }else{
                idLabel.setVisible(false);
                idTextField.setVisible(false);
                confermaButton.setVisible(false);
                utenteCheckBox.setVisible(false);
                giudiceCheckBox.setVisible(false);
                teamCheckBox.setVisible(false);

                rimozioniVisibili = false;
            }

            mainpanel.revalidate();
            mainpanel.repaint();
        });

        utenteCheckBox.addActionListener(e -> {
            if (utenteCheckBox.isSelected()) {
                teamCheckBox.setSelected(false); giudiceCheckBox.setSelected(false);}
        });

        teamCheckBox.addActionListener(e -> {
            if (teamCheckBox.isSelected()) {
                utenteCheckBox.setSelected(false); giudiceCheckBox.setSelected(false);}
        });

        giudiceCheckBox.addActionListener(e -> {
            if (giudiceCheckBox.isSelected()) {
                utenteCheckBox.setSelected(false); teamCheckBox.setSelected(false);}
        });

        confermaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(e -> {
            if(!utenteCheckBox.isSelected() && !giudiceCheckBox.isSelected() && !teamCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(mainpanel, "Inserire un ruolo" , "Error", JOptionPane.ERROR_MESSAGE);
            }

            if(utenteCheckBox.isSelected()) {
                if(idTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(mainpanel, "Inserire Username" , "Error", JOptionPane.ERROR_MESSAGE); return;}
                Utente u = hdao.findUtenteByUsername(idTextField.getText());

                if(u == null) {
                    JOptionPane.showMessageDialog(mainpanel, "Utente "+idTextField.getText()+"non trovato" , "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int conferma = JOptionPane.showConfirmDialog(mainpanel, "Sei sicuro di voler rimuovere "+idTextField.getText()+"?" ,
                            "Conferma", JOptionPane.YES_NO_OPTION);

                    if (conferma == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(mainpanel, "Utente rimosso.");
                        odao.removeUtente(idTextField.getText());
                    }
                }
            }

            if(giudiceCheckBox.isSelected()) {
                if(idTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(mainpanel, "Inserire Username" , "Error", JOptionPane.ERROR_MESSAGE); return;}
                Giudice g = hdao.findGiudiceByUsername(idTextField.getText());

                if(g == null) {
                    JOptionPane.showMessageDialog(mainpanel, "Giudice "+idTextField.getText()+"non trovato" , "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int conferma = JOptionPane.showConfirmDialog(mainpanel, "Sei sicuro di voler declassare "+idTextField.getText()+"?" ,
                            "Conferma", JOptionPane.YES_NO_OPTION);

                    if (conferma == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(mainpanel, "Giudice declassato ad utente.");
                        odao.removeGiudice(idTextField.getText());
                    }
                }
            }


            if(teamCheckBox.isSelected()) {
                String idTxt = idTextField.getText();

                if(idTxt.isEmpty()){
                    JOptionPane.showMessageDialog(mainpanel, "Inserire ID" , "Error", JOptionPane.ERROR_MESSAGE); return;}

                int id;
                try{
                    id = Integer.parseInt(idTxt);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(mainpanel, "L'ID deve essere un numero intero valido", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Team t = tdao.getTeamByID(id);

                if(t == null) {
                    JOptionPane.showMessageDialog(mainpanel, "Team "+idTextField.getText()+"non trovato" , "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int conferma = JOptionPane.showConfirmDialog(mainpanel, "Sei sicuro di voler rimuovere il Team "+idTextField.getText()+"?" ,
                            "Conferma", JOptionPane.YES_NO_OPTION);

                    if (conferma == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(mainpanel, "Team rimosso.");
                        odao.removeTeam(id);
                    }
                }
            }
        });

        indietroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> {
            controller.getSchermataOrganizzatore();
        });


    }
    public JPanel getMainPanel() {
        return mainpanel;
    }
}

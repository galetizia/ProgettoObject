package gui;

import controller.ControllerTeamSchermataUtente;
import model.*;
import javax.swing.*;

public class TeamSchermataUtente {

    private JPanel mainPanel;
    private JButton myTeamButton;
    private JButton button2;
    private JButton lasciaTeamButton;
    private JButton cercaTeamButton;
    private JButton creaTeamButton;

    public TeamSchermataUtente(ControllerTeamSchermataUtente controller, Team team) {}


    public JPanel getMainPanel(){
        return mainPanel;
    }
}

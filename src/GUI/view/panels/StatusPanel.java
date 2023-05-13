package GUI.view.panels;

import GUI.view.buttons.RoundedOutlinedButton;
import GUI.view.frames.Notifiable;
import GUI.view.labels.RoundedJLabel;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Status panel for the current player and end turn button
 */
public class StatusPanel extends JPanel implements ActionListener{
    /**
     * Button to end the turn
     */
    private JButton endTurnButton;
    /**
     * The player that is currently playing
     */
    private JLabel currentPlayerLabel;
    /**
     * Indicates how many actions does the player have left
     */
    private JLabel actionCounter;
    /**
     * The object that needs to be notified
     */
    private Notifiable notifiable;

    /**
     * Creates a status-panel
     * @param notifiable The object that needs to be notified
     */
    public StatusPanel(Notifiable notifiable) {
        this.notifiable = notifiable;
        endTurnButton = new RoundedOutlinedButton("End Turn");
        endTurnButton.addActionListener(this);
        this.add(endTurnButton);

        // this is necessary for information about the current player (currentPlayerLabel and actionCounter) to be displayed in the same column
        JPanel currentPlayerInformationContainer = new JPanel();
        currentPlayerInformationContainer.setOpaque(false);

        currentPlayerLabel = new RoundedJLabel();
        currentPlayerLabel.setText(TurnHandler.getActiveVirologist().getName());
        currentPlayerLabel.setAlignmentX(CENTER_ALIGNMENT);
        currentPlayerInformationContainer.add(currentPlayerLabel);

        actionCounter = new RoundedJLabel();
        actionCounter.setText("Initializing...");
        actionCounter.setAlignmentX(CENTER_ALIGNMENT);
        currentPlayerInformationContainer.add(actionCounter);
        currentPlayerInformationContainer.setLayout(new BoxLayout(currentPlayerInformationContainer, BoxLayout.Y_AXIS));
        this.add(currentPlayerInformationContainer);
    }

    /**
     * Updates every graphical object
     */
    public void update() {
        this.currentPlayerLabel.setText(TurnHandler.getActiveVirologist().getName());
        this.actionCounter.setText(Integer.toString(TurnHandler.getActiveVirologist().getActions()));
        this.repaint();
    }

    /**
     * By pressing the end-turn button the virologist
     * passes their turn
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == endTurnButton) {
            TurnHandler.getActiveVirologist().endTurn();
            notifiable.creativeNotify("New turn");
        }
    }
}

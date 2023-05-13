package GUI.view.view.agentView;

import GUI.view.frames.GameFrame;
import GUI.view.view.VirologistView;
import main.com.teamalfa.blindvirologists.agents.virus.ParalyzeVirus;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import javax.swing.*;
import java.awt.event.ActionEvent;
/**
 * View for the paralyze virus
 */
public class ParalyzeVirusView extends AgentView{
    /**
     * The virus that is binded with the model
     */
    private ParalyzeVirus virus;
    /**
     * Creates a view
     * @param virus The virus that will be binded with this view
     */
    public ParalyzeVirusView(ParalyzeVirus virus) {
        this.virus = virus;
        setImageIcon(new ImageIcon("resources/viruses/bluevirus.png"));
    }
    /**
     * The virus can be used on the highlighted virologist
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        VirologistView highlightedVirologistView = GameFrame.getHighlightedVirologistView();
        if (highlightedVirologistView != null) {
            Virologist target = highlightedVirologistView.getVirologist();
            TurnHandler.getActiveVirologist().use(virus, target);
        }
    }
}

package com.teamalfa.GUI.view.view.agentView;

import com.teamalfa.GUI.view.frames.GameFrame;
import com.teamalfa.GUI.view.view.VirologistView;
import com.teamalfa.blindvirologists.agents.virus.BearVirus;
import com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import com.teamalfa.blindvirologists.virologist.Virologist;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * View for the bear virus
 */
public class BearVirusView extends AgentView{
    /**
     * The virus that is binded with the model
     */
    private BearVirus virus;

    /**
     * Creates a view
     * @param virus The virus that will be binded with this view
     */
    public BearVirusView(BearVirus virus) {
        this.virus = virus;
        setImageIcon(new ImageIcon("resources/viruses/redvirus.png"));
    }
    /**
     * The virus could be passed to the highlighted virologist
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

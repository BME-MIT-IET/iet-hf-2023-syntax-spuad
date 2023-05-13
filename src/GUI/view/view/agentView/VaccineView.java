package GUI.view.view.agentView;

import GUI.view.frames.GameFrame;
import GUI.view.view.VirologistView;
import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.genetic_code.*;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import javax.swing.*;
import java.awt.event.ActionEvent;
/**
 * View for the vaccine
 */
public class VaccineView extends AgentView {
    /**
     * The vaccine in the model
     */
    private Vaccine vaccine;

    /**
     * creates a new view to a vaccine in the model
     * @param vaccine the vaccine belonging to the view
     */
    public VaccineView(Vaccine vaccine) {
        // set the appropriate image
        String str = "valami";
        String imagePath = "resources/vaccines/yellow_vaccine.png";
        if (vaccine.getGeneticCode() instanceof DanceCode) imagePath = "resources/vaccines/yellow_vaccine.png";
        if (vaccine.getGeneticCode() instanceof AmnesiaCode) imagePath = "resources/vaccines/green_vaccine.png";
        if (vaccine.getGeneticCode() instanceof ParalyzeCode) imagePath = "resources/vaccines/blue_vaccine.png";
        if (vaccine.getGeneticCode() instanceof BearCode) imagePath = "resources/vaccines/red_vaccine.png";
        setImageIcon(new ImageIcon(imagePath));

        // set the vaccine
        this.vaccine = vaccine;
    }
    /**
     * The vaccine could be used on the highlighted virologist
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        VirologistView highlightedVirologistView = GameFrame.getHighlightedVirologistView();
        if (highlightedVirologistView != null) {
            Virologist target = highlightedVirologistView.getVirologist();
            TurnHandler.getActiveVirologist().use(vaccine, target);
        }
    }
}

package GUI.view.panels;

import GUI.view.view.geneticCodeView.*;
import main.com.teamalfa.blindvirologists.agents.genetic_code.*;
import main.com.teamalfa.blindvirologists.virologist.backpack.pockets.GeneticCodePocket;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This panel contains the genetic codes
 * in the inventory
 */
public class GeneticCodePanel extends BaseBagPanel {
    /**
     * View for every genetic code
     */
    private ArrayList<GeneticCodeView> views;
    /**
     * Slots for the genetic codes
     */
    private ArrayList<InventorySlot> slots;
    /**
     * The genetic code pocket bound with the view
     */
    GeneticCodePocket geneticCodePocket;

    public GeneticCodePanel(GeneticCodePocket geneticCodePocket) {
        // initializing
        setLayout(new GridBagLayout());
        this.geneticCodePocket = geneticCodePocket;
        slots = new ArrayList<>();

        // creating inventory slots
        for (int i = 0; i < 4; i++) {
            slots.add(new InventorySlot(null));
        }

        // the update method will create views and bind them to the slots
        update();

        // creating the layout of the panel
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets(0, 0, 2, 0), 0, 0);

        // creating the title of the panel
        JLabel title = new JLabel("Genetic codes");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Viner Hand ITC", Font.PLAIN, 12));
        title.setForeground(Color.RED);

        add(title, constraints);

        // adding slots
        for(var s: slots) {
            constraints.gridy++;
            add(s, constraints);
        }
    }

    /**
     * Updates the graphic view every time
     * something happens
     */
    public void update() {
        views = new ArrayList<>();
        ArrayList<GeneticCode> geneticCodes = geneticCodePocket.getGeneticCodes();

        // creates a view for each agent found in the agent pocket
        for (var gc : geneticCodes) {
            if (gc instanceof ParalyzeCode) {
                views.add(new ParalyzeCodeInventoryView((ParalyzeCode) gc));
                continue;
            }
            if (gc instanceof BearCode) {
                views.add(new BearCodeInventoryView((BearCode) gc));
                continue;
            }
            if (gc instanceof AmnesiaCode) {
                views.add(new AmnesiaCodeInventoryView((AmnesiaCode) gc));
                continue;
            }
            if (gc instanceof DanceCode) {
                views.add(new DanceCodeInventoryView((DanceCode) gc));
                continue;
            }
        }

        // binds the views to slots
        int i = 0;
        for (i = i; i < views.size(); i++) {
            slots.get(i).setView(views.get(i));
        }

        // fill the rest with empty slots
        for(i = i; i < slots.size(); i++)
            slots.get(i).setView(null);
    }

    /**
     * Sets the genetic code pocket of this panel
     * @param gcp Genetic code pocket
     */
    public void setGeneticCodePocket(GeneticCodePocket gcp) {
        this.geneticCodePocket = gcp;
    }
}

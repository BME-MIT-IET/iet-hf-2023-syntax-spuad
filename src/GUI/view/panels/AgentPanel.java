package GUI.view.panels;

import GUI.view.view.agentView.*;
import main.com.teamalfa.blindvirologists.agents.Agent;
import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.virus.AmnesiaVirus;
import main.com.teamalfa.blindvirologists.agents.virus.BearVirus;
import main.com.teamalfa.blindvirologists.agents.virus.DanceVirus;
import main.com.teamalfa.blindvirologists.agents.virus.ParalyzeVirus;
import main.com.teamalfa.blindvirologists.virologist.backpack.pockets.AgentPocket;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AgentPanel extends BaseBagPanel {
    private ArrayList<AgentView> views;
    private ArrayList<InventorySlot> slots;
    private AgentPocket agentPocket;

    /**
     * constructs a new agent panel base on the parameter
     * @param agentPocket The pocket in the backpack that contains agents
     */
    public AgentPanel(AgentPocket agentPocket) {
        // initializing
        setLayout(new GridBagLayout());
        this.agentPocket = agentPocket;
        slots = new ArrayList<>();

        // creating inventory slots
        for (int i = 0; i < agentPocket.getMaxSize(); i++) {
            slots.add(new InventorySlot(null));
        }

        // the update method will create views and bind them to the slots
        update();

        // creating the layout of the panel
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets(0, 0, 2, 0), 0, 0);

        // creating the title of the panel
        JLabel title = new JLabel("Agents");
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
     * Updates the view of the agent panel: constructs views from agents found in the agent pocket and binds them to the inventory slots.
     */
    @Override
    public void update() {
        views = new ArrayList<>();
        ArrayList<Agent> agents = agentPocket.getAgentHolder();

        // creates a view for each agent found in the agent pocket
        for (var a : agents) {
            if (a instanceof Vaccine) {
                views.add(new VaccineView((Vaccine) a));
                continue;
            }
            if (a instanceof ParalyzeVirus) {
                views.add(new ParalyzeVirusView((ParalyzeVirus) a));
                continue;
            }
            if (a instanceof BearVirus) {
                views.add(new BearVirusView((BearVirus) a));
                continue;
            }
            if (a instanceof AmnesiaVirus) {
                views.add(new AmnesiaVirusView((AmnesiaVirus) a));
                continue;
            }
            if (a instanceof DanceVirus) {
                views.add(new DanceVirusView((DanceVirus) a));
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
     * Sets the agent pocket and displays its contents
     * @param agentPocket - The agent pocket to be set
     */
    public void setAgentPocket(AgentPocket agentPocket) {
        this.agentPocket = agentPocket;
        update();
    }
}

package GUI.view.panels;

import GUI.view.buttons.RoundedOutlinedButton;
import GUI.view.frames.GameFrame;
import GUI.view.menus.RobPopupMenu;
import GUI.view.view.View;
import GUI.view.view.VirologistView;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;

import javax.swing.*;
import java.awt.*;

/**
 * The whole inventory of the player can be seen on
 * this panel
 */
public class InventoryPanel extends JPanel implements View {
    /**
     * Separate panel for the agents
     */
    AgentPanel agentPanel;
    /**
     * Separate panel for the equipments
     */
    EquipmentPanel equipmentPanel;
    /**
     * Separate panel for the genetic codes
     */
    GeneticCodePanel geneticCodePanel;
    /**
     * Separate panel for the elements
     */
    ElementPanel elementPanel;

    /**
     * Constructs a new inventory panel
     */
    public InventoryPanel() {
        // creating the layout of the panel. creating constraints that will later be manipulated at each element
        setLayout(new GridBagLayout());
        setBackground(Color.GRAY.darker().darker().darker());
        setOpaque(false);
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets(3, 0, 3, 0), 0, 0);


        // creating the title of the panel
        JLabel title = new JLabel("Inventory");
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Viner Hand ITC", Font.PLAIN, 15));
        title.setForeground(Color.RED);
        add(title, constraints);


        // Creating bag panels
        agentPanel = new AgentPanel(TurnHandler.getActiveVirologist().getBackpack().getAgentPocket());
        equipmentPanel = new EquipmentPanel(TurnHandler.getActiveVirologist().getBackpack().getEquipmentPocket());
        geneticCodePanel = new GeneticCodePanel(TurnHandler.getActiveVirologist().getBackpack().getGeneticCodePocket());

        // Creating the panel that will hold the bag panels
        JPanel bagPanelsPanel = new JPanel();
        bagPanelsPanel.setOpaque(false);
        GridLayout gridLayout = new GridLayout(1, 3);
        gridLayout.setHgap(20);
        gridLayout.setVgap(0);
        bagPanelsPanel.setLayout(gridLayout);

        // adding bag panels
        bagPanelsPanel.add(agentPanel);
        bagPanelsPanel.add(equipmentPanel);
        bagPanelsPanel.add(geneticCodePanel);

        // making the master panel scrollable by adding it to a scroll pane
        JScrollPane scrollPane = new JScrollPane(bagPanelsPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // manipulating the constraints and then adding the scrollable pane
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(0, 20, 0, 20);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weighty = 1.0;
        add(scrollPane, constraints);

        // create the panel displaying the elements
        elementPanel = new ElementPanel(TurnHandler.getActiveVirologist().getBackpack().getElementBank());

        // adding the panel to the parent panel
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.weighty = 0.0;
        add(elementPanel, constraints);

        // create a new button to rob
        JButton rob = new RoundedOutlinedButton("Rob");
        rob.addActionListener(e -> {
            VirologistView targetView = GameFrame.getHighlightedVirologistView();
            if (targetView != null) {
                Virologist target = targetView.getVirologist();
                JPopupMenu robMenu = new RobPopupMenu(target);
                robMenu.show(targetView, 0, 0);
            }
        });

        // adding it to the panel
        constraints.gridy = 3;
        add(rob, constraints);

    }

    /**
     * Prints the panel: makes the background have rounded corners.
     * @param g -  i have no idea what this is... :(
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
        g2.dispose();

        //also call the original jpanl paint method
        super.paint(g);
    }

    /**
     * fill the invetory gui with the items in the backpack of the current virologist
     */
    public void update() {
        Backpack bp = TurnHandler.getActiveVirologist().getBackpack();

        agentPanel.setAgentPocket(bp.getAgentPocket());
        agentPanel.update();

        elementPanel.setElementBank(bp.getElementBank());
        elementPanel.update();

        equipmentPanel.setEquipmentPocket(bp.getEquipmentPocket());
        equipmentPanel.update();

        geneticCodePanel.setGeneticCodePocket(bp.getGeneticCodePocket());
        geneticCodePanel.update();
    }

    /**
     * Doesn't do anything
     */
    public void onClick() {
        //TODO
    }

}

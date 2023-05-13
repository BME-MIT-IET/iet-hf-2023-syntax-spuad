package GUI.view.panels;

import GUI.view.view.View;
import GUI.view.view.equipmentView.*;
import main.com.teamalfa.blindvirologists.equipments.Bag;
import main.com.teamalfa.blindvirologists.equipments.Cloak;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Axe;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Gloves;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The equipments that the virologist is currently wearing
 */
public class WornEquipmentPanel extends JPanel implements View {
    /**
     * The virologist who's wearing the equipments
     */
    private Virologist virologist;
    /**
     * The slot for the equipments
     */
    private WornEquipmentInventorySlot[] slots;
    /**
     * The views for the equipments
     */
    private EquipmentView[] views;

    /**
     * Creates the panel for the worn equipments
     */
    public WornEquipmentPanel() {
        // initializing attributes
        this.slots = new WornEquipmentInventorySlot[3];
        this.views = new EquipmentView[3];

        // creating the layout of the panel. creating constraints that will later be manipulated at each element
        setLayout(new GridBagLayout());
        setBackground(Color.GRAY.darker().darker().darker());
        setOpaque(false);
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets(3, 0, 3, 0), 0, 0);

        // creating the title of the panel
        JLabel title = new JLabel("Current Equipments");
        title.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Viner Hand ITC", Font.PLAIN, 15));
        title.setForeground(Color.RED);
        add(title,constraints);

        // create inventory slots
        for (int i = 0; i < 3; i++)
            slots[i] = new WornEquipmentInventorySlot(null);

        // the update method will create views and bind them to the slots
        update();

        // Creating the panel that will hold the slots
        JPanel slotPanel = new JPanel();
        slotPanel.setOpaque(false);

        // Adding bag panels
        for (var s : slots)
            slotPanel.add(s);

        constraints.gridy = 1;
        constraints.fill =GridBagConstraints.BOTH;
        constraints.insets = new Insets(0,20,0,20);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weighty = 1.0;
        add(slotPanel,constraints);
    }

    /**
     * Overriding the paint method
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
        g2.dispose();
        super.paint(g);
    }

    /**
     * Updating the views and graphics when s
     * something happens
     */
    @Override
    public void update() {
        virologist = TurnHandler.getActiveVirologist();
        ArrayList<Equipment> worn = virologist.getWornEquipment();

        // creating views and binding them to the slots
        int i = 0;
        for (i = i; i < 3; i++) {
            // if the slot is filled
            if (i < worn.size()) {
                if (worn.get(i) instanceof Gloves)
                    views[i] = new GlovesInventoryView((Gloves) worn.get(i), true);

                if (worn.get(i) instanceof Cloak)
                    views[i] = new CloakInventoryView((Cloak) worn.get(i), true);

                if (worn.get(i) instanceof Axe)
                    views[i] = new AxeInventoryView((Axe) worn.get(i), true);

                if (worn.get(i) instanceof Bag)
                    views[i] = new BagInventoryView((Bag) worn.get(i), true);

                slots[i].setView(views[i]);
            }

            // if it is not filled
            else
                slots[i].setView(null);
        }
    }

    /**
     * Doesn't do anything
     */
    @Override
    public void onClick() {

    }
}

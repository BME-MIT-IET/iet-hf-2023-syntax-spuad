package GUI.view.panels;

import GUI.view.view.equipmentView.*;
import main.com.teamalfa.blindvirologists.equipments.Bag;
import main.com.teamalfa.blindvirologists.equipments.Cloak;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Axe;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Gloves;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.backpack.pockets.EquipmentPocket;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EquipmentPanel extends BaseBagPanel {
    /**
     * Array of views of equipments
     */
    private ArrayList<EquipmentView> views;
    /**
     * The slots in the inventory
     */
    private ArrayList<InventorySlot> slots;
    /**
     * The pocket that the panel refers to
     */
    private EquipmentPocket equipmentPocket;

    /**
     * Constructs a new equipment panel base on the parameter
     * @param equipmentPocket The pocket that the panel refers to
     */
    public EquipmentPanel(EquipmentPocket equipmentPocket) {
        // initializing
        setLayout(new GridBagLayout());
        this.equipmentPocket = equipmentPocket;
        slots = new ArrayList<>();

        // creating inventory slots
        for (int i = 0; i < equipmentPocket.getMaxSize(); i++) {
            slots.add(new InventorySlot(null));
        }

        // the update method will create views and bind them to the slots
        update();

        // creating the layout of the panel
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets(0, 0, 2, 0), 0, 0);

        // creating the title of the panel
        // this correctly called "Pieces of equipment" but that is long and making it multiline is difficult
        JLabel title = new JLabel("Equipments");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Viner Hand ITC", Font.PLAIN, 12));
        title.setForeground(Color.RED);
        add(title, constraints);

        // adding slots
        //constraints.anchor = GridBagConstraints.CENTER;
        for(var s: slots) {
            constraints.gridy++;
            add(s, constraints);
        }
    }
    /**
     * Updates the view of the equipment panel: constructs views from pices of equipment found in the equipment pocket and binds them to the inventory slots.
     */
    @Override
    public void update() {
        views = new ArrayList<>();
        // it is important to make a shallow copy!
        ArrayList<Equipment> piecesOfEquipment = new ArrayList<>(equipmentPocket.getEquipmentHolder());
        ArrayList<Equipment> wornPiecesOfEquipment = TurnHandler.getActiveVirologist().getWornEquipment();

        // creates a view for each agent found in the agent pocket
        for (var eq : piecesOfEquipment) {
            if (eq instanceof Gloves) {
                views.add(new GlovesInventoryView((Gloves) eq, false));
                continue;
            }
            if (eq instanceof Cloak) {
                views.add(new CloakInventoryView((Cloak) eq, false));
                continue;
            }
            if (eq instanceof Axe) {
                views.add(new AxeInventoryView((Axe) eq, false));
                continue;
            }
            if (eq instanceof Bag) {
                views.add(new BagInventoryView((Bag) eq, false));
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

    public void setEquipmentPocket(EquipmentPocket equipmentPocket) {
        this.equipmentPocket = equipmentPocket;
    }
}

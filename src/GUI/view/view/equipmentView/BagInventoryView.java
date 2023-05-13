package GUI.view.view.equipmentView;

import GUI.view.menus.EquipmentPopupMenu;
import main.com.teamalfa.blindvirologists.equipments.Bag;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
/**
 * View for the bag in the inventory
 */
public class BagInventoryView extends BagView {
    /**
     * If it is currently worn this attribute is true
     */
    private boolean isWorn;
    /**
     * Pop up menu for different actions
     */
    private JPopupMenu popupMenu = new EquipmentPopupMenu(bag);

    /**
     * Creates a view for the bag
     * @param bag The bag in the model
     * @param isWorn If it is currently worn this attribute is true
     */
    public BagInventoryView(Bag bag, boolean isWorn) {
        super(bag);
        this.isWorn = isWorn;
    }

    /**
     * You can equip or unequip the bag by clicking it
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!isWorn)
            if(TurnHandler.getActiveVirologist().getField().canChangeEquipment())
                popupMenu.show(this, 0, 0);
        else {
            TurnHandler.getActiveVirologist().toggle(bag);
        }
    }
}

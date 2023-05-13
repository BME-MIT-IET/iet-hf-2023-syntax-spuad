package GUI.view.view.equipmentView;

import GUI.view.menus.EquipmentPopupMenu;
import main.com.teamalfa.blindvirologists.equipments.Cloak;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
/**
 * View for the cloak in the inventory
 */
public class CloakInventoryView extends CloakView {
    /**
     * If it is currently worn this attribute is true
     */
    private boolean isWorn;
    /**
     * Pop up menu for different actions
     */
    private JPopupMenu popupMenu = new EquipmentPopupMenu(cloak);

    /**
     * Creates a view for the cloak
     * @param cloak The cloak in the model
     * @param isWorn If it is currently worn this attribute is true
     */
    public CloakInventoryView(Cloak cloak, boolean isWorn) {
        super(cloak);
        this.isWorn = isWorn;
    }

    /**
     * You can equip or unequip the cloak by clicking it
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!isWorn)
            if(TurnHandler.getActiveVirologist().getField().canChangeEquipment())
                popupMenu.show(this, 0, 0);
        else
            TurnHandler.getActiveVirologist().toggle(cloak);
    }
}

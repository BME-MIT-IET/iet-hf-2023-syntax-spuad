package GUI.view.view.equipmentView;

import GUI.view.menus.EquipmentPopupMenu;
import GUI.view.menus.WornGlovePopupMenu;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Gloves;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
/**
 * View for the gloves in the inventory
 */
public class GlovesInventoryView extends GlovesView {
    /**
     * If it is currently worn this attribute is true
     */
    private boolean isWorn;
    /**
     * Pop up menu for different actions
     */
    private JPopupMenu popupMenu = new EquipmentPopupMenu(gloves);

    /**
     * Creates a view for the gloves
     * @param gloves The gloves in the model
     * @param isWorn If it is currently worn this attribute is true
     */
    public GlovesInventoryView(Gloves gloves, boolean isWorn) {
        super(gloves);
        this.isWorn = isWorn;
    }
    /**
     * You can equip or unequip the gloves by clicking it
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!isWorn)
            if(TurnHandler.getActiveVirologist().getField().canChangeEquipment()) {
                popupMenu = new EquipmentPopupMenu(gloves);
                popupMenu.show(this, 0, 0);
            }
        else {
            popupMenu = new WornGlovePopupMenu(gloves);
            popupMenu.show(this, 0, 0);
        }
    }
}

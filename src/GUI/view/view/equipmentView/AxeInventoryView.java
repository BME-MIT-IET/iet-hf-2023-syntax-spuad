package GUI.view.view.equipmentView;

import GUI.view.frames.GameFrame;
import GUI.view.menus.EquipmentPopupMenu;
import GUI.view.menus.WornAxePopupMenu;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Axe;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * View for the axe in the inventory
 */
public class AxeInventoryView extends AxeView {
    /**
     * If it is currently worn this attribute is true
     */
    private boolean isWorn;
    /**
     * Pop up menu for different actions
     */
    private JPopupMenu popupMenu;

    /**
     * Creates a view for the axe
     * @param axe The axe in the model
     * @param isWorn If it is currently worn this attribute is true
     */
    public AxeInventoryView(Axe axe, boolean isWorn) {
        super(axe);
        this.isWorn = isWorn;
    }

    /**
     * You can use the axe on other virologists
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isWorn) {
            if (!axe.isWornOut()) {
                popupMenu = new WornAxePopupMenu(axe, GameFrame.getHighlightedVirologistView() != null,
                        TurnHandler.getActiveVirologist().getField().canChangeEquipment());
                popupMenu.show(this, 0, 0);
            }
            else {
                TurnHandler.getActiveVirologist().toggle(axe);
            }
        }
        else {
            if(TurnHandler.getActiveVirologist().getField().canChangeEquipment()) {
                popupMenu = new EquipmentPopupMenu(axe);
                popupMenu.show(this, 0, 0);
            }
        }
        handleIcon();
    }
}

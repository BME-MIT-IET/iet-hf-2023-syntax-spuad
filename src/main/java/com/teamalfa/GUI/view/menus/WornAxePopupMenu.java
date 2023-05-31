package com.teamalfa.GUI.view.menus;

import com.teamalfa.GUI.view.frames.GameFrame;
import com.teamalfa.GUI.view.view.equipmentView.AxeInventoryView;
import com.teamalfa.GUI.view.view.equipmentView.EquipmentView;
import com.teamalfa.blindvirologists.equipments.active_equipments.Axe;
import com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import com.teamalfa.blindvirologists.virologist.Virologist;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Pop up menu for the axe
 */
public class WornAxePopupMenu extends JPopupMenu {
    private Axe axe;
    public WornAxePopupMenu(Axe axe, boolean canUseAxe, boolean canChangeEquipment) {
        this.axe = axe;
        setOpaque(false);
        setBorderPainted(false);

        if(canUseAxe) {
            JMenuItem use = new NiceMenuItem();
            use.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (getInvoker() instanceof AxeInventoryView && GameFrame.getHighlightedVirologistView() != null) {
                        Virologist target = GameFrame.getHighlightedVirologistView().getVirologist();
                        TurnHandler.getActiveVirologist().use(axe, target);
                    }
                }
            });
            use.setText("Use");
            add(use);
        }

        if(canChangeEquipment) {
            JMenuItem unequip = new NiceMenuItem();
            unequip.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (getInvoker() instanceof EquipmentView) {
                        TurnHandler.getActiveVirologist().toggle(axe);
                    }
                }
            });
            unequip.setText("Unequip");
            add(unequip);
        }
    }
}

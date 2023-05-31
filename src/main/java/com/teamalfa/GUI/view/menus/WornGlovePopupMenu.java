package com.teamalfa.GUI.view.menus;

import com.teamalfa.GUI.view.frames.GameFrame;
import com.teamalfa.GUI.view.view.equipmentView.AxeInventoryView;
import com.teamalfa.blindvirologists.equipments.active_equipments.Gloves;
import com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import com.teamalfa.blindvirologists.virologist.Virologist;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Pop up menu for the current glove
 */
public class WornGlovePopupMenu extends JPopupMenu {
    private Gloves gloves;

    public WornGlovePopupMenu(Gloves gloves) {
        this.gloves = gloves;
        setOpaque(false);
        setBorderPainted(false);

        if (TurnHandler.getActiveVirologist().getField().canChangeEquipment()) {
            JMenuItem unequip = new NiceMenuItem();
            unequip.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (getInvoker() instanceof AxeInventoryView && GameFrame.getHighlightedVirologistView() != null) {
                        TurnHandler.getActiveVirologist().toggle(gloves);
                    }
                }
            });
            unequip.setText("Unequip");
            add(unequip);
        }

        for (var v: TurnHandler.getActiveVirologist().getViruses()) {
            JMenuItem newVirusItem = new NiceMenuItem();
            newVirusItem.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gloves.setUsedVirus(v);
                    Virologist target = GameFrame.getHighlightedVirologistView().getVirologist();
                    TurnHandler.getActiveVirologist().use(gloves, target);
                }
            });
            newVirusItem.setText(v.getName());
            add(newVirusItem);
        }
    }
}

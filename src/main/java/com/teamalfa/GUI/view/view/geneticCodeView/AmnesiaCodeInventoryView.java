package com.teamalfa.GUI.view.view.geneticCodeView;

import com.teamalfa.GUI.view.menus.GeneticCodePopupMenu;
import com.teamalfa.blindvirologists.agents.genetic_code.AmnesiaCode;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Represents the amensia genetic code view in the inventory
 */
public class AmnesiaCodeInventoryView extends AmnesiaCodeView {
    /**
     * ctr
     * @param code dancecode param
     */
    public AmnesiaCodeInventoryView(AmnesiaCode code) {
        super(code);
    }

    /**
     * When clicked it makes the pop up menu appear.
     * @param e actionevent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JPopupMenu popupMenu = new GeneticCodePopupMenu();
        popupMenu.show(this, 0, 0);
    }
}

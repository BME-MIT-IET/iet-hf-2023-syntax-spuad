package com.teamalfa.GUI.view.view.geneticCodeView;

import com.teamalfa.GUI.view.menus.GeneticCodePopupMenu;
import com.teamalfa.blindvirologists.agents.genetic_code.ParalyzeCode;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Represents the paralyzecode view in the inventory panel.
 */
public class ParalyzeCodeInventoryView extends ParalyzeCodeView {
    /**
     * ctr
     * @param code the code param
     */
    public ParalyzeCodeInventoryView(ParalyzeCode code) {
        super(code);
    }

    /**
     * Makes the pop up menu appear, when clicked.
     * @param e actionevent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JPopupMenu popupMenu = new GeneticCodePopupMenu();
        popupMenu.show(this, 0, 0);
    }
}

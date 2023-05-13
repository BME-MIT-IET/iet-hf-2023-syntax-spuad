package GUI.view.view.geneticCodeView;

import GUI.view.menus.GeneticCodePopupMenu;
import main.com.teamalfa.blindvirologists.agents.genetic_code.BearCode;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Represents the bear genetic code view in the inventory
 */
public class BearCodeInventoryView extends BearCodeView {
    /**
     * ctr
     * @param code bearcode param
     */
    public BearCodeInventoryView(BearCode code) {
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

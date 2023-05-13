package GUI.view.view.geneticCodeView;

import GUI.view.menus.GeneticCodePopupMenu;
import GUI.view.view.agentView.DanceVirusView;
import main.com.teamalfa.blindvirologists.agents.genetic_code.DanceCode;
import main.com.teamalfa.blindvirologists.agents.virus.DanceVirus;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Represents the dance genetic code view in the inventory
 */
public class DanceCodeInventoryView extends DanceCodeView {
    /**
     * ctr
     * @param code dancecode param
     */
    public DanceCodeInventoryView(DanceCode code) {
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

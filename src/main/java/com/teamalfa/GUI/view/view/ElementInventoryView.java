package main.java.com.teamalfa.GUI.view.view;

import main.java.com.teamalfa.GUI.view.panels.ElementPanel;
import main.java.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

/**
 * Represents the view of the elements in the inventory.
 */
public class ElementInventoryView extends ElementView {
    /**
     * An elementpanel
     */
    private ElementPanel panel;

    /**
     * ctr tab tab
     * @param elementBank the elementbank it represent
     * @param panel the panel it represents yay
     */
    public ElementInventoryView(ElementBank elementBank, ElementPanel panel) {
        super(elementBank);
        this.panel = panel;
        update();
    }

    /**
     * setter, it's literally in the name what it can do
     * sets the aminoacid quantity -> set's the textlabel's text.
     * @param q the number it has to be set to.
     */
    private void setAminoQuantity(int q) {
        panel.getAminoTextLabel().setText(": " + q);
    }

    /**
     * The same but with nucleotides.
     * @param q the number it has to be set to.
     */
    private void setNucleoQuantity(int q) {
        panel.getNucleoTextLabel().setText(": " + q);
    }


    /**
     * Updates the quantity textpanels.
     */
    @Override
    public void update() {
        setAminoQuantity(eb.getAminoAcid());
        setNucleoQuantity(eb.getNucleotide());
    }
}

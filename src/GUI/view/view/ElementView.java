package GUI.view.view;

import main.com.teamalfa.blindvirologists.city.fields.StoreHouse;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the element banks in the GUI
 */
public class ElementView extends JButton implements View, ActionListener {
    /**
     * The element bank it represents.
     */
    protected ElementBank eb;
    /**
     * The width of the icon.
     */
    private final int iconWidth = 896 / 20;
    /**
     * The height of the icon.
     */
    private final int iconHeight = 1196 / 20;

    /**
     * Ctr tab tab
     * @param elementBank the elemnt it represents.
     */
    public ElementView(ElementBank elementBank) {
        setLayout(null);
        this.eb = elementBank;
        setPreferredSize(new Dimension(iconWidth, iconHeight));
        handleIcon();
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        this.addActionListener(this);
    }

    /**
     * Updates the icon if the view is updated.
     */
    protected void handleIcon() {
        if(eb.getAminoAcid() > 0 || eb.getNucleotide() > 0) {
            ImageIcon icon = new ImageIcon("resources/elementbank.png");
            Image img = icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
            JLabel thumb = new JLabel();
            thumb.setIcon(icon);
            thumb.setBounds(0, 0, iconWidth, iconHeight);
            add(thumb);
        }
        else {
            this.setEnabled(false);
        }
    }

    /**
     * ActionListener's method, calls the virologist's pickupmaterial method when clicked.
     * @param e actionevent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(eb.getAminoAcid() > 0 || eb.getNucleotide() > 0) {
            TurnHandler.getActiveVirologist().pickUpMaterial(eb);
        }
    }

    /**
     * doesn't do anything
     */
    @Override
    public void update() {

    }

    /**
     * doesn't do anything
     */
    @Override
    public void onClick() {

    }

    /**
     * setter
     * @param elementBank sets the value, like we all don't know what a setter do bruh
     */
    public void setElementBank(ElementBank elementBank) {
        eb = elementBank;
    }
}

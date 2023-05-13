package GUI.view.view.equipmentView;

import main.com.teamalfa.blindvirologists.equipments.active_equipments.Axe;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * View for the axe on the field
 */
public class AxeView extends EquipmentView{
    /**
     * The axe in the model
     */
    protected final Axe axe;
    /**
     * The width of the button
     */
    private final int iconWidth = 896 / 20;
    /**
     * The height of the button
     */
    private final int iconHeight = 1196 / 20;

    /**
     * Creates an axe-view
     * @param axe With this axe
     */
    public AxeView(Axe axe){
        setLayout(null);
        this.axe = axe;
        setPreferredSize(new Dimension(iconWidth, iconHeight));
        handleIcon();
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        this.addActionListener(this);
    }
    /**
     * Sets up the icon or background of the view
     */
    @Override
    protected void handleIcon() {
        removeAll();
        ImageIcon icon = new ImageIcon("resources/axe.png");
        if (axe.isWornOut())
            icon = new ImageIcon("resources/shattered_axe.png");
        Image img = icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        JLabel thumb = new JLabel();
        thumb.setIcon(icon);
        thumb.setBounds(0,0, iconWidth, iconHeight);
        add(thumb);
    }

    /**
     * You can pick up the axe by clicking the button
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        TurnHandler.getActiveVirologist().pickUpEquipment(axe);
    }
}

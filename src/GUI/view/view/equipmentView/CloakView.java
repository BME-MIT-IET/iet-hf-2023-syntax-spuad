package GUI.view.view.equipmentView;

import main.com.teamalfa.blindvirologists.equipments.Cloak;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/**
 * View for the cloak on the field
 */
public class CloakView extends EquipmentView {
    /**
     * The cloak in the model
     */
    protected Cloak cloak;
    /**
     * The width of the button
     */
    private final int iconWidth = 896 / 20;
    /**
     * The height of the button
     */
    private final int iconHeight = 1196 / 20;

    /**
     * Creates a cloak-view
     * @param cloak With this cloak
     */
    public CloakView(Cloak cloak) {
        setLayout(null);
        this.cloak = cloak;
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
        ImageIcon icon = new ImageIcon("resources/cloak.png");
        Image img = icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        JLabel thumb = new JLabel();
        thumb.setIcon(icon);
        thumb.setBounds(0, 0, iconWidth, iconHeight);
        add(thumb);
    }
    /**
     * You can pick up the cloak by clicking the button
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        TurnHandler.getActiveVirologist().pickUpEquipment(cloak);
    }
}

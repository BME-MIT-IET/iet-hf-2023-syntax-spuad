package GUI.view.view.equipmentView;

import main.com.teamalfa.blindvirologists.equipments.active_equipments.Gloves;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/**
 * View for the gloves on the field
 */
public class GlovesView extends EquipmentView{
    /**
     * The gloves in the model
     */
    protected Gloves gloves;
    /**
     * The width of the button
     */
    private final int iconWidth = 896 / 20;
    /**
     * The height of the button
     */
    private final int iconHeight = 1196 / 20;

    /**
     * Creates a gloves-view
     * @param gloves With this glove
     */
    public GlovesView(Gloves gloves) {
        setLayout(null);
        this.gloves = gloves;
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
        ImageIcon icon = new ImageIcon("resources/glove.png");
        Image img = icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        JLabel thumb = new JLabel();
        thumb.setIcon(icon);
        thumb.setBounds(0, 0, iconWidth, iconHeight);
        add(thumb);
    }
    /**
     * You can pick up the gloves by clicking the button
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        TurnHandler.getActiveVirologist().pickUpEquipment(gloves);
    }
}

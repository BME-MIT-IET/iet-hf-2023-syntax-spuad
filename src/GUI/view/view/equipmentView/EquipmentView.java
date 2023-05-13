package GUI.view.view.equipmentView;

import GUI.view.view.View;
import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Abstract class for all the equipment views
 */
public abstract class EquipmentView extends JButton implements View, ActionListener {
    /**
     * Updates the graphical view for any change
     */
    @Override
    public void update() {}

    /**
     * Does something when you click it
     */
    @Override
    public void onClick() {}

    /**
     * Sets up the icon or background of the view
     */
    protected abstract void handleIcon();
}

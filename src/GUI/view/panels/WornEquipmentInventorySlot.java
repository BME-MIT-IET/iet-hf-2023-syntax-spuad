package GUI.view.panels;

import javax.swing.*;
import java.awt.*;

/**
 * The equipments that the virologist is currently wearing
 */
public class WornEquipmentInventorySlot extends InventorySlot {
    /**
     * This is the slot containing the equipment
     * @param view The view for the slot
     */
    public WornEquipmentInventorySlot(JButton view) {
        super(view);
    }

    /**
     * It sets the view of slot
     * @param view The view that needs to be created
     */
    @Override
    public void setView(JButton view) {
        super.setView(view);
        if (view == null) {
            JLabel text = new JLabel("Empty");
            text.setPreferredSize(new Dimension(sideLength, sideLength));
            text.setHorizontalAlignment(SwingConstants.CENTER);
            text.setVerticalAlignment(SwingConstants.CENTER);
            text.setFont(new Font("Viner Hand ITC", Font.PLAIN, 12));
            text.setForeground(Color.RED);
            setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            add(text);
        }
    }
}

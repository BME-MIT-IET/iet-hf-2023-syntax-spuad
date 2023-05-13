package GUI.view.panels;

import GUI.view.view.View;

import javax.swing.*;

/**
 * base class to represent panels in the inventory. this might not be needed...
 */
public class BaseBagPanel extends JPanel implements View {
    public BaseBagPanel() {
        setOpaque(false);
    }

    @Override
    public void update() {    }

    @Override
    public void onClick() {    }
}

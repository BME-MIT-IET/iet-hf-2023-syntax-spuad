package GUI.view.view.agentView;

import GUI.view.panels.AgentPanel;
import GUI.view.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A view for the agent (Abstract class)
 */
public abstract class AgentView extends JButton implements View, ActionListener {
    /**
     * The panel that is binded with view
     */
    private AgentPanel panel;
    Dimension mysize = new Dimension(20, 20);

    /**
     * Initializes the button part of the view
     */
    public AgentView() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setVerticalAlignment(CENTER);
        setHorizontalAlignment(CENTER);
        this.addActionListener(this);
        setSize(mysize);
    }

    /**
     * scales and sets the passed icon as the picture on the view.
     * @param imageIcon - the image
     */
    public void setImageIcon(ImageIcon imageIcon) {
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
        setIcon(imageIcon);
    }

    /**
     * Doesn't do anything
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {   }

    /**
     * Updates the panel
     */
    @Override
    public void update() {
        panel.update();
    }

    /**
     * Doesn't do anything
     */
    @Override
    public void onClick() {

    }
}

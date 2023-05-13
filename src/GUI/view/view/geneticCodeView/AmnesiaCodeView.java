package GUI.view.view.geneticCodeView;

import main.com.teamalfa.blindvirologists.agents.genetic_code.AmnesiaCode;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Represents the amnesia genetic code in the GUI.
 */
public class AmnesiaCodeView extends GeneticCodeView {
    /**
     * The amnesia code it represents
     */
    private final AmnesiaCode code;
    /**
     * The width of the icon
     */
    private final int iconWidth = 896 / 20;
    /**
     * The height of the icon.
     */
    private final int iconHeight = 1196 / 20;

    /**
     * ctr
     * @param code code param.
     */
    public AmnesiaCodeView(AmnesiaCode code) {
        super(code);
        setLayout(null);
        this.code = code;
        setPreferredSize(new Dimension(iconWidth, iconHeight));
        handleIcon();
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        this.addActionListener(this);
    }

    /**
     * Redraws the icon when the game view is updated.
     */
    @Override
    protected void handleIcon() {
        ImageIcon icon = new ImageIcon("resources/amnesiavirus.gif");
        JLabel thumb = new JLabel();
        thumb.setIcon(icon);
        thumb.setBounds(0,0, iconWidth, iconHeight);
        add(thumb);
    }

    /**
     * When clicked calls the active virologist's learn method.
     * @param e actionevent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        TurnHandler.getActiveVirologist().learn(code);
    }
}

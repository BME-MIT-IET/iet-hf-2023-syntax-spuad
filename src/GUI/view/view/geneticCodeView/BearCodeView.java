package GUI.view.view.geneticCodeView;

import main.com.teamalfa.blindvirologists.agents.genetic_code.BearCode;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Represent the bear genetic code in the game a.k.a the acid-tripping sparrow.
 */
public class BearCodeView extends GeneticCodeView {
    /**
     * The bear code it represents
     */
    private final BearCode code;
    /**
     * The width of the icon
     */
    private final int iconWidth = 896 / 20;
    /**
     * The height of the icon
     */
    private final int iconHeight = 1196 / 20;

    /**
     * ctr
     * @param code bearcode param
     */
    public BearCodeView(BearCode code) {
        super(code);
        setLayout(null);
        this.code = code;
        setPreferredSize(new Dimension(iconWidth+20, iconHeight+20));
        handleIcon();
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        this.addActionListener(this);
    }

    /**
     * Redraws the icon if the game view is updated.
     */
    @Override
    protected void handleIcon() {
        ImageIcon icon = new ImageIcon("resources/dont_open_me.gif");
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

package GUI.view.view.geneticCodeView;

import main.com.teamalfa.blindvirologists.agents.genetic_code.DanceCode;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Represents the dance genetic code in the game.
 */
public class DanceCodeView extends GeneticCodeView {
    /**
     * The dance code it represents.
     */
    private final DanceCode code;
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
     * @param code danceCode param
     */
    public DanceCodeView(DanceCode code) {
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
     * Redraws the icon when game is updated.
     */
    @Override
    protected void handleIcon() {
        ImageIcon icon = new ImageIcon("resources/dancevirus.gif");
        JLabel thumb = new JLabel();
        thumb.setIcon(icon);
        thumb.setBounds(0,0, iconWidth, iconHeight);
        add(thumb);
    }

    /**
     * When it's clicked it calls the active virologist's learn method.
     * @param e actionevent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        TurnHandler.getActiveVirologist().learn(code);
    }
}

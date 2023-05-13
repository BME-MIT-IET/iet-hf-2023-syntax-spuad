package GUI.view.view.geneticCodeView;

import GUI.view.view.View;
import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Abstract class that represents the genetic codes in the game.
 */
public abstract class GeneticCodeView extends JButton implements View, ActionListener {
    /**
     * the genetic code it represents.
     */
    private GeneticCode geneticCode;

    /**
     * ctr
     * @param geneticCode geneticCode param.
     */
    public GeneticCodeView(GeneticCode geneticCode) {
        this.geneticCode = geneticCode;
    }

    /**
     * getter
     * @return the geneticcode
     */
    public GeneticCode getGeneticCode() {
        return geneticCode;
    }

    /**
     * doesnt do anything
     * @param e actionevent
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * doesn't do anything
     */
    @Override
    public void update() {

    }

    /**
     * doesn't do anything
     */
    @Override
    public void onClick() {

    }

    /**
     * Abstract method that handles how the icons appear in the game.
     */
    protected abstract void handleIcon();
}

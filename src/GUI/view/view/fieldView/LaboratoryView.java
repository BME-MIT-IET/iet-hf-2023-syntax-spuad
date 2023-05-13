package GUI.view.view.fieldView;

import GUI.view.view.VirologistView;
import GUI.view.view.geneticCodeView.*;
import main.com.teamalfa.blindvirologists.agents.genetic_code.*;
import main.com.teamalfa.blindvirologists.city.fields.Laboratory;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.awt.*;

/**
 * Represents laboratories in the game.
 */
public class LaboratoryView extends FieldView{
    /**
     * The genetic code that's in the laboratory.
     */
    private GeneticCodeView code;
    /**
     * The laboratory itself.
     */
    private Laboratory laboratory;

    /**
     * ctr
     */
    public LaboratoryView(){
        color = new Color(3, 18, 9);
        newImage = Toolkit.getDefaultToolkit().createImage("resources/lab.png");
        backGround = newImage.getScaledInstance(315,315,Image.SCALE_DEFAULT);
        this.text = "lab";
        setFieldText("lab");
    }

    /**
     * setter
     * @param lab sets the laboratory to this laboratory.
     */
    public void setField(Laboratory lab) {
        field = lab;
    }

    /**
     * Graphics paint method, paints the hexagons on the game.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        drawPolygon(g2d,200/2, 200/2 ,1,color.getRGB(),true);
        if(TurnHandler.getActiveVirologist().getDiscoveredFields().contains(field))
            g.drawImage(backGround,-45,-8,null);
        this.repaint();
    }

    /**
     * Called when the game view gets updated.
     * Removes every component from the field, and updates it if it's the current field.
     */
    @Override
    public void update() {
        // remove all components from field
        removeAll();

        // and update only if its current field
        if(field.equals(TurnHandler.getActiveVirologist().getField())) {
            if(TurnHandler.getActiveVirologist().getDiscoveredFields().contains(field)) {
                for (Virologist virologist : field.getVirologists()) {
                    add(new VirologistView(virologist));
                }
                laboratory = (Laboratory) TurnHandler.getActiveVirologist().getField();
                GeneticCode currentCode = laboratory.getGeneticCode();

                if(currentCode != null) {
                    switch(currentCode.getName()) {
                        case "amnesia code": add(new AmnesiaCodeView((AmnesiaCode) laboratory.getGeneticCode()));
                        break;

                        case "bear code": add(new BearCodeView((BearCode) laboratory.getGeneticCode()));
                        break;

                        case "dance code": add(new DanceCodeView((DanceCode) laboratory.getGeneticCode()));
                        break;

                        case "paralyze code": add(new ParalyzeCodeView((ParalyzeCode) laboratory.getGeneticCode()));
                        break;

                        default:
                    }
                }
            }
            else {
                add(new VirologistView(TurnHandler.getActiveVirologist()));
            }
        }
    }
}

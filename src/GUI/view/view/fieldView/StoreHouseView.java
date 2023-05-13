package GUI.view.view.fieldView;

import GUI.view.view.ElementView;
import GUI.view.view.VirologistView;
import main.com.teamalfa.blindvirologists.city.fields.StoreHouse;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.awt.*;

/**
 * Represents storehouses in the game.
 */
public class StoreHouseView extends FieldView {
    /**
     * The elementView on the store house.
     */
    private ElementView elementView;
    /**
     * The store house itself.
     */
    private StoreHouse storeHouse;

    /**
     * ctr
     */
    public StoreHouseView(){
        color = new Color(3, 18, 9);
        newImage = Toolkit.getDefaultToolkit().createImage("resources/StoreHouse1.png");
        backGround = newImage.getScaledInstance(200,200,Image.SCALE_DEFAULT);
        this.text = "store";
        setFieldText("store");
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
            g.drawImage(backGround,0,0,null);
        this.repaint();
    }

    /**
     * setter
     * @param storeh sets the store house to this store house.
     */
    public void setField(StoreHouse storeh) {
        field = storeh;
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
                storeHouse = (StoreHouse) TurnHandler.getActiveVirologist().getField();
                elementView = new ElementView(storeHouse.getElements());
                add(elementView);
            }
            else {
                add(new VirologistView(TurnHandler.getActiveVirologist()));
            }
        }
    }
}

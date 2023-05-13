package GUI.view.view.fieldView;

import GUI.view.view.VirologistView;
import GUI.view.view.equipmentView.*;
import main.com.teamalfa.blindvirologists.city.fields.SafeHouse;
import main.com.teamalfa.blindvirologists.equipments.Bag;
import main.com.teamalfa.blindvirologists.equipments.Cloak;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Axe;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Gloves;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents the safehouses in the game.
 */
public class SafeHouseView extends FieldView{
    /**
     * Equipment views that are currently on the safe house.
     */
    private ArrayList<EquipmentView> equipments = new ArrayList<>();
    /**
     * The safehouse
     */
    private SafeHouse safeHouse;

    /**
     * ctr
     */
    public SafeHouseView(){
        color = new Color(3, 18, 9);
        newImage = Toolkit.getDefaultToolkit().createImage("resources/SafeHouse1.png");
        backGround = newImage.getScaledInstance(200,200,Image.SCALE_DEFAULT);
        this.text = "safe";
        setFieldText("safe");
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
     * @param safeh sets the safehouse to this safehouse.
     */
    public void setField(SafeHouse safeh) {
        field = safeh;
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
                safeHouse = (SafeHouse) TurnHandler.getActiveVirologist().getField();
                for (Equipment equipment : safeHouse.getEquipments()) {
                    if (equipment.getName().equals("axe"))
                        add(new AxeView((Axe) equipment));
                    else if (equipment.getName().equals("bag"))
                        add(new BagView((Bag) equipment));
                    else if (equipment.getName().equals("cloak"))
                        add(new CloakView((Cloak) equipment));
                    else if (equipment.getName().equals("glove"))
                        add(new GlovesView((Gloves) equipment));
                }
            }
            else {
                add(new VirologistView(TurnHandler.getActiveVirologist()));
            }
        }
    }
}

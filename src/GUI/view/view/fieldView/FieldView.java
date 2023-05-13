package GUI.view.view.fieldView;

import GUI.view.frames.GameFrame;
import GUI.view.view.View;
import GUI.view.view.VirologistView;
import GUI.view.view.equipmentView.EquipmentView;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Represents the fields in the game
 */
public class FieldView extends JPanel implements View, MouseListener {
    /**
     * The field it represents
     */
    protected Field field;
    /**
     * VirologistViews on the field.
     */
    private ArrayList<VirologistView> virologistOnField = new ArrayList<>();
    /**
     * Equipmentviews on the field
     */
    private ArrayList<EquipmentView> equipmentViews = new ArrayList<>();
    /**
     * for testing
     */
    private JLabel textField = new JLabel("Field");
    /**
     * for testing
     */
    protected String text = "Field";
    /**
     * the color of the field before searched on it.
     */
    protected Color color;
    /**
     * draw images
     */
    protected Image newImage;
    protected Image backGround;
    /**
     * dimensions for the hexagon
     */
    private static final int hexaDimension = 200;

    /**
     * ctr
     */
    public FieldView(){
        color = new Color(3, 18, 9);
        newImage = Toolkit.getDefaultToolkit().createImage("resources/field1.png");
        backGround = newImage.getScaledInstance(198,198,Image.SCALE_DEFAULT);
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(100,100));
        this.setFont(new Font("Viner Hand ITC", Font.PLAIN, 15));
        this.add(textField);
        this.setVisible(true);
        this.addMouseListener(this);
    }

    /**
     * getter for the hexadimension.
     * @return the hexadimension.
     */
    public static int getHexaDimension(){
        return hexaDimension;
    }

    /**
     * setter for the field
     * @param f the field it gets set on
     */
    public void setField(Field f){
        this.field = f;
    }

    /**
     * setter for the textfield
     * @param text the text it's set to
     */
    public void setFieldText(String text) {
        textField.setText(text);
    }

    /**
     * getter for the field
     * @return the field
     */
    public Field getField(){
        return field;
    }

    /**
     * MouseListener method, doesn't needed
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * If mousepressed on a neighbouring field it calls the move method on the active virologist.
     * If it's pressed on the current field and it hasn't been searched yet, it calls the search method on
     * the active virologist.
     * @param e mousevent
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Virologist current = TurnHandler.getActiveVirologist();
        if(field != current.getField() && current != null) {
            current.move(field);
            GameFrame.setHighlightedVirologistView(null);
        }
        else if(field.equals(current.getField())) {
            if(!TurnHandler.getActiveVirologist().getDiscoveredFields().contains(field))
                TurnHandler.getActiveVirologist().search();
        }
    }

    /**
     * MouseListener method, doesn't needed
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * for testing
     * @param e mousevent
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        String name = "null";
        if(field != null) {
            String[] parts = field.toString().split("\\.");
            name = parts[parts.length-1];
        }
        textField.setText(name);
    }

    /**
     * for testing.
     * @param e mousevent
     */
    @Override
    public void mouseExited(MouseEvent e) {
        textField.setText(text);
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
            if(!TurnHandler.getActiveVirologist().getDiscoveredFields().contains(field)) {
                add(new VirologistView(TurnHandler.getActiveVirologist()));
            }
            else {
                for (Virologist virologist : field.getVirologists()) {
                    add(new VirologistView(virologist));
                }
            }
        }
    }

    /**
     * Doesn't do anxthing.
     */
    @Override
    public void onClick() {}

    /**
     * Graphics paint method, paints the hexagons on the game.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        drawPolygon(g2d,hexaDimension/2, hexaDimension/2 ,1,color.getRGB(),true);
        if(TurnHandler.getActiveVirologist().getDiscoveredFields().contains(field))
            g.drawImage(backGround,1,1,null);
        this.repaint();
    }

    /**
     * Drawing the hexagon.
     * @param g The drawing tool.
     * @param xcenter The x coordinate of the center point.
     * @param ycenter The y coordinate of the center point.
     * @param lineThickness The thickness of the line.
     * @param colorValue The number of the colors.
     * @param filled Bool if it's filled or not.
     */
    public void drawPolygon(Graphics2D g, int xcenter, int ycenter, int lineThickness, int colorValue, boolean filled) {
        g.setColor(new Color(colorValue));
        g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

        int[] xpoints = new int[6];
        int[] ypoints = new int[6];
        for (int p = 0; p < 6; p++) {
            double fraction = (double) p / 6;
            double angle = fraction * Math.PI * 2 + Math.toRadians((90 + 180) % 360);
            int x = (int) (xcenter + Math.cos(angle) * hexaDimension/2); //1000/2 -> center
            int y = (int) (ycenter + Math.sin(angle) * hexaDimension/2); //120 -> radius
            Point point = new Point(x,y);
            xpoints[p] = point.x;
            ypoints[p] = point.y;
        }
        if (filled)
            g.fillPolygon(xpoints, ypoints, 6);
        else
            g.drawPolygon(xpoints, ypoints, 6);
    }

}

package GUI.view.frames;

import GUI.view.panels.*;
import GUI.view.view.VirologistView;
import main.com.teamalfa.blindvirologists.city.City;
import main.com.teamalfa.blindvirologists.turn_handler.Game;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame implements Notifiable {
    /**
     * Status panel for the current player and end turn button
     */
    private final StatusPanel statusPanel;
    /**
     * Panel for items like equipments, genetic codes, and agents
     */
    private final InventoryPanel inventoryPanel;
    /**
     * The equipments that the virologist is currently wearing
     */
    private final WornEquipmentPanel wornEquipmentPanel;
    /**
     * This is where you can watch the generated hexagon map
     */
    private final MapPanel mapPanel;
    /**
     * This panel gives a basic feedback of what is happening in the game
     */
    private final WhatHappenedPanel whatHappenedPanel;
    /**
     * The current view of the virologist
     */
    public static VirologistView target = null;

    /**
     * Set a virologist to be highlighted on the screen
     * @param virologistView The virologist who is highlighted
     */
    public static void setHighlightedVirologistView(VirologistView virologistView) {
        if (target != virologistView) {
            if (target != null)
                target.setHighlighted(false);
            if (virologistView != null)
                virologistView.setHighlighted(true);
        }
        target = virologistView;
    }

    /**
     *
     * @return The current highlighted virologist
     */
    public static VirologistView getHighlightedVirologistView() {
        return target;
    }

    /**
     *
     * @param numberOfPlayers How many players playing the game
     */
    public GameFrame(int numberOfPlayers){
        //Starting the game
        this.startGame(numberOfPlayers);
        //And setting up the basics...
        this.setTitle("Blind Virologist");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1280,720);
        ImageIcon imageIcon = new ImageIcon("resources/logo.png");
        this.setIconImage(imageIcon.getImage());
        this.getContentPane().setBackground(new Color(0,0,0));

        //LayeredPane panel, map will be in the back and the rest front
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0,0,1280,720);

        // Creating the status panel
        statusPanel = new StatusPanel(this);
        statusPanel.setBounds(0,0,300,100);
        statusPanel.setBackground(Color.black);

        //Creating worn equipment panel
        wornEquipmentPanel = new WornEquipmentPanel();
        wornEquipmentPanel.setBounds(960,5, 300,110);

        //Creating the inventory panel
        inventoryPanel = new InventoryPanel();
        inventoryPanel.setBounds(960,115,300,300);

        //Creating what happened panel
        whatHappenedPanel = new WhatHappenedPanel();
        whatHappenedPanel.setBounds(960,415,300,258);

        //Creating map panel
        mapPanel = new MapPanel();
        mapPanel.setBounds(0,80,1000,550);

        this.setLayout(null);
        this.setVisible(true);
        layeredPane.add(mapPanel,Integer.valueOf(0));           //Default Layer(bottom)
        layeredPane.add(whatHappenedPanel,Integer.valueOf(1));  //The rest will go to the front
        layeredPane.add(wornEquipmentPanel,Integer.valueOf(1));
        layeredPane.add(statusPanel,Integer.valueOf(1));
        layeredPane.add(inventoryPanel,Integer.valueOf(1));
        this.add(layeredPane);
        this.repaint();
    }

    /**
     * Starts the game with numberOfPlayers
     * @param numberOfPlayers How many players playing the game
     */
    public void startGame (int numberOfPlayers) {
        Game.setNumberOfPlayers(numberOfPlayers);
        Game.getInstance().startGame();
        //Adding players to the turnhandler
        for(int i = 1 ; i <= numberOfPlayers ; i++){
            Virologist virologist = new Virologist("Player"+i);
            virologist.setNotifiable(this);
            City.getAllFields().get(0).accept(virologist);
            TurnHandler.accept(virologist);
        }
    }

    /**
     * Updates all Views
     */
    public void updateView() {
        //Game ends if all virologists died
        if(TurnHandler.GetOrder().size() == 0){
            JOptionPane.showMessageDialog(null,"Everyone died","Game lost",JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
        if(TurnHandler.getActiveVirologist().getBackpack().getGeneticCodePocket().getGeneticCodes().size() == 4){
            JOptionPane.showMessageDialog(null,"Congratulations! You won!","Game over",JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
        statusPanel.update();
        wornEquipmentPanel.update();
        inventoryPanel.update();
        whatHappenedPanel.update();
        mapPanel.update();
    }

    /**
     * Passes the event massage to the WhatHappenedPanel
     * Updates all Views
     */
    @Override
    public void creativeNotify(String massage) {
        whatHappenedPanel.logOnPanel(massage);
        updateView();
    }
}

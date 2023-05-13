package main.com.teamalfa.blindvirologists.turn_handler;

import main.com.teamalfa.blindvirologists.agents.virus.BearVirus;
import main.com.teamalfa.blindvirologists.city.City;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.util.ArrayList;

public class Game implements Steppable{
    private static Game instance;
    private City city;
    private static int numberOfPlayers;
    private ArrayList<Virologist> bears;

    /**
     * Singleton
     */
    static {
        instance = new Game();
        TurnHandler.accept(instance);
    }

    private Virologist virologist;

    private Game() {
        bears = new ArrayList<>();
    }

    /**
     * gets the instance
     * @return the instance
     */
    public static Game getInstance() {
        return instance;
    }

    /**
     * generates the map
     */
    public void startGame() {
        City.getInstance().GenerateMap();
    }

    public void endGame(ArrayList<Virologist> winners) {}

    /**
     * Adds the virologist to the bears.
     * @param v The virologist that turned into a bear.
     */
    public void accept(Virologist v) {
        bears.add(v);
    }

    /**
     * Removes the virologist from the bears.
     * @param v The bear that died.
     */
    public void remove(Virologist v) {
        bears.remove(v);
    }

    /**
     * Controls the bears.
     * Every bear steps into a new field, they infect every virologist on said field
     * with bearvirus, then if they are inside a store they destroy every element in there.
     */

    public void controlBears() {
        for (int i = 0; i < bears.size(); i++) {
            Virologist bear = bears.get(i);
            Field f = bear.getField();
            if(bear.getActions() <= 50)
                bear.setActions(100);
            bear.move(f);
            if(bear.searchForVirologist() != null) {
                for (Virologist enemy : bear.searchForVirologist()) {
                    bear.use(new BearVirus(), enemy);
                }
                bear.getField().destroy();
            }
        }
    }

    /**
     * For console use
     * @param bear the bear
     * @param origin origin field
     * @param destination new field
     */
    private void printMovement(Virologist bear, Field origin, Field destination) {
        System.out.println("Bear moved: ");
        System.out.println("ID: ");
    }

    /**
     * Calls the controlbears method.
     */
    public void step() {
        controlBears();
    }

    /**
     * getters, setters
     */
    public ArrayList<Virologist> getBears() {
        return bears;
    }

    //setter
    public static void setNumberOfPlayers(int players) { numberOfPlayers = players; }

    //getter
    public static int getNumberOfPlayers() { return numberOfPlayers; }

}

package main.com.teamalfa.blindvirologists.turn_handler;

import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.util.ArrayList;
import java.util.Collections;

public class TurnHandler {
    private static TurnHandler instance;
    private static final ArrayList<Steppable> steppables = new ArrayList<>();
    private static ArrayList<Virologist> order = new ArrayList<>();
    private static Virologist activeVirologist; // the virologist, who's turn is active

    /**
     * Needed for singleton design pattern.
     */

    static {
      instance = new TurnHandler();
    }

    private TurnHandler(){}

    public static TurnHandler getInstance() {
        // Any class that wants to access the TurnHandler can call this method.
        return instance;
    }

    /**
     * Makes every steppable step.
     *
     */
    public static void tick() {
        for(int i = 0; i < steppables.size(); i++) {
            steppables.get(i).step();
        }
        if(!TurnHandler.GetOrder().isEmpty()) {
            changeActiveVirologist();
        }
    }

    /**
     * Helper method to change the current virologist to the next one.
     */
    public static void changeActiveVirologist() {
        int indx = order.indexOf(activeVirologist);
        if(indx == order.size()-1) {
            reOrderVirologists();
            activeVirologist = order.get(0);
        }
        else {
            activeVirologist = order.get(indx + 1);
        }
        activeVirologist.startTurn();
    }

    /**
     * Adds the new steppable to the list.
     * @param steppable The steppable that's added to the list.
     */
    public static void accept(Steppable steppable) {
        steppables.add(steppable);
    }

    /**
     * Adds the virologist to the list.
     * @param virologist The new Virologist.
     */
    public static void accept(Virologist virologist) {
        if(order.isEmpty())
            activeVirologist = virologist;
        order.add(virologist);
    }

    /**
     * Removes the steppable from the list.
     * @param steppable
     */
    public void remove(Steppable steppable) {
        steppables.remove(steppable);
    }

    /**
     * Removes the virologist from the list.
     * @param virologist
     */
    public void remove(Virologist virologist) {
        order.remove(virologist);
    }

    /**
     * Reorders the virologists list.
     */
    private static void reOrderVirologists() {
        Collections.shuffle(order);
    }

    //getters, setters
    public static ArrayList<Virologist> GetOrder() {
        return order;
    }


    public static Virologist getActiveVirologist() {
        return activeVirologist;
    }

    public static void setActiveVirologist(Virologist v) {
        activeVirologist = v;
    }
}

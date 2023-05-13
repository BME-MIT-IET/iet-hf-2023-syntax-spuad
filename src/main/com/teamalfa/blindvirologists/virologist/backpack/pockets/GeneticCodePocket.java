package main.com.teamalfa.blindvirologists.virologist.backpack.pockets;

import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;

import java.util.ArrayList;

public class GeneticCodePocket extends Pocket{

    private ArrayList<GeneticCode> geneticCodes = new ArrayList<GeneticCode>();
    private Backpack backpack;

    public GeneticCodePocket(Backpack b) {
        backpack = b;
    }

    //getter
    public ArrayList<GeneticCode> getGeneticCodes() {
        return geneticCodes;
    }

    /**
     * adds a new genetic code to the pocket.
     */

    public void add(GeneticCode gc){
        geneticCodes.add(gc);
    }

    /**
     * Deletes every geneticCode from the pocket.
     */
    public void deleteAll() {
        geneticCodes.clear();
    }

    //getter
    public int getCurrentSize(){
        return geneticCodes.size();
    }
}

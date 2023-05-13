package main.com.teamalfa.blindvirologists.agents;

import main.com.teamalfa.blindvirologists.agents.genetic_code.*;

import java.util.ArrayList;

public class GeneticCodeBank {
    private static GeneticCodeBank instance;
    private ArrayList<GeneticCode> codes;

    private GeneticCodeBank() {
        codes = new ArrayList<>();
        codes.add(new ParalyzeCode());
        codes.add(new DanceCode());
        codes.add(new AmnesiaCode());
        codes.add(new BearCode());
    }

    /**
     * Creates one object in the beginning of the game.
     */
    static {
        instance = new GeneticCodeBank();
    }

    //getters
    public ArrayList<GeneticCode> getCodes() {
        return codes;
    }

    public static GeneticCodeBank getInstance() {
        return instance;
    }

}
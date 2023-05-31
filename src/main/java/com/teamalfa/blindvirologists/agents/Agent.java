package main.java.com.teamalfa.blindvirologists.agents;

import main.java.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.java.com.teamalfa.blindvirologists.turn_handler.Steppable;
import main.java.com.teamalfa.blindvirologists.virologist.Virologist;
import main.java.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

/**
 * Abstract class that represents the agents in the game.
 */
abstract public class Agent implements Steppable {
    protected String name;
    /**
     * The cost of the Agent.
     */
    protected ElementBank cost;
    /**
     * The virologist that agent is used on.
     */
    protected Virologist target = null;
    /**
     * The agent's genetic code.
     */
    protected GeneticCode geneticCode;
    /**
     * The time the agent affects the virologist.
     */
    protected int duration;
    /**
     * The time the agent can spend in the backpack.
     */
    protected int expiry;

    /**
     * ctr
     */
    public Agent() {
        cost = new ElementBank(0,0);
    }

    //getters, setters
    public GeneticCode getGeneticCode() {
        return geneticCode;
    }

    public void setGeneticCode(GeneticCode code) {
        geneticCode = code;
    }

    /**
     * Applies tha Agent and it's effect on the Virologist.
     * @param target The virologist the agent affects.
     */
    public abstract void apply(Virologist target);

    //getters
    public ElementBank getCost() {
        return cost;
    }

    public Virologist getTarget() {
        return target;
    }
    public void setTarget(Virologist virologist) { target = virologist; }

    public String getName() { return name; }
}
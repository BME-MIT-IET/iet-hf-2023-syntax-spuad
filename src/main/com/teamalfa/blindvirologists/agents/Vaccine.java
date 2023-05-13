package main.com.teamalfa.blindvirologists.agents;

import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.turn_handler.Game;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class Vaccine extends Agent {

    public Vaccine(GeneticCode geneticcode) {
        expiry = duration = 2 * Game.getNumberOfPlayers();
        this.geneticCode = geneticcode;
        name = "vaccine";

        cost.setAminoAcid(10);
        cost.setNucleotide(10);
    }

    /**
     * This method is called when a Vaccine is used on a Virologist.
     * @param target The virologist that got vaccinated.
     */
    @Override
    public void apply(Virologist target) {
        target.protectedBy(this);
        this.target = target;
    }

    /**
     * Handles the duration and the expiry of the vaccine,
     * if one of them is zero it removes it from the backpack/virologist.
     */
    @Override
    public void step() {
        if(expiry > 0 && target.getBackpack().getAgentPocket().getAgentHolder().contains(this))
            expiry--;
        else if(expiry == 0 && target.getBackpack().getAgentPocket().getAgentHolder().contains(this)) {
            target.getBackpack().getAgentPocket().getAgentHolder().remove(this);
            TurnHandler.getInstance().remove(this);
        }
        else if(duration > 0 && target.getProtectionBank().contains(geneticCode))
            duration--;
        else if(duration == 0 && target.getProtectionBank().contains(geneticCode)) {
            target.removeVaccine(this);
            TurnHandler.getInstance().remove(this);
        }
    }
}

package main.com.teamalfa.blindvirologists.agents.virus;

import main.com.teamalfa.blindvirologists.agents.genetic_code.AmnesiaCode;
import main.com.teamalfa.blindvirologists.city.City;
import main.com.teamalfa.blindvirologists.turn_handler.Game;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class AmnesiaVirus extends Virus {

    public AmnesiaVirus() {
        priority = 4;
        expiry = duration = 2 * Game.getNumberOfPlayers();
        geneticCode = new AmnesiaCode();
        name = "amnesia virus";
        cost.setNucleotide(20);
        cost.setAminoAcid(20);
    }

    /**
     * This method is called when the Virus is applied to a Virologist.
     * If them infection is successful the Virus deletes every genetic code the Virologist
     * has learned so far.
     * @param target The Virologist the AmnesiaVirus is applied to.
     */
    @Override
    public void apply(Virologist target) {
        if (target.infectedBy(this)) {
            this.target = target;
            this.target.getBackpack().deleteAllGeneticCodes();
        }
    }
}
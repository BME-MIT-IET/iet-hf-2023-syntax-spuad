package main.com.teamalfa.blindvirologists.agents.genetic_code;

import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.virus.AmnesiaVirus;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

public class AmnesiaCode extends GeneticCode {

    public AmnesiaCode(){
        this.type = "amnesia";
        name = "amnesia code";
    }

    /**
     * This method creates a Virus with the amnesia geneticcode.
     *
     * @return The AmnesiaVirus that has been created.
     */
    @Override
    public AmnesiaVirus createVirus(ElementBank elementBank) {
        // create paralyze virus
        AmnesiaVirus av = new AmnesiaVirus();
        // handle cost
        ElementBank cost = av.getCost();
        if(elementBank.remove(cost)) {
            TurnHandler.getInstance().accept(av);
            return av;
        }
        // if the virologist didn't have enough material
        return null;
    }

    /**
     * This method create a Vaccine with the amnesia geneticcode.
     *
     * @return The Vaccine that has been created.
     */
    @Override
    public Vaccine createVaccine(ElementBank elementBank) {
        // create paralyze vaccine
        Vaccine av = new Vaccine(this);
        // handle cost
        ElementBank cost = av.getCost();
        if(elementBank.remove(cost)) {
            TurnHandler.getInstance().accept(av);
            return av;
        }
        // if the virologist didn't have enough material
        return null;
    }
}
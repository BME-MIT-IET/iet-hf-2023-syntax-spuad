package main.com.teamalfa.blindvirologists.agents.genetic_code;

import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.virus.DanceVirus;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

public class DanceCode extends GeneticCode{

    public DanceCode(){
        this.type = "dance";
        name = "dance code";
    }

    /**
     * This method creates a Virus with the dance geneticcode.
     * @return The DanceVirus that has been created.
     */
    @Override
    public DanceVirus createVirus(ElementBank elementBank) {
        // create paralyze virus
        DanceVirus dv = new DanceVirus();
        // handle cost
        ElementBank cost = dv.getCost();
        if(elementBank.remove(cost)) {
            TurnHandler.getInstance().accept(dv);
            return dv;
        }
        // if the virologist didn't have enough material
        return null;
    }

    /**
     * This method create a Vaccine with the dance geneticcode.
     * @return The Vaccine that has been created.
     */
    @Override
    public Vaccine createVaccine(ElementBank elementBank) {
        // create paralyze vaccine
        Vaccine dv = new Vaccine(this);
        // handle cost
        ElementBank cost = dv.getCost();
        if(elementBank.remove(cost)) {
            TurnHandler.getInstance().accept(dv);
            return dv;
        }
        // if the virologist didn't have enough material
        return null;
    }
}

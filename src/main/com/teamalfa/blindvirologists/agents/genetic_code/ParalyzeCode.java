package main.com.teamalfa.blindvirologists.agents.genetic_code;
import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.virus.ParalyzeVirus;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

public class ParalyzeCode extends GeneticCode{

    public ParalyzeCode(){
        this.type = "paralyze";
        name = "paralyze code";
    }

    /**
     * This method creates a Virus with the paralyze geneticcode.
     * @return The ParalyzeVirus that has been created.
     */
    @Override
    public ParalyzeVirus createVirus(ElementBank elementBank) {
        // create paralyze virus
        ParalyzeVirus pv = new ParalyzeVirus();
        // handle cost
        ElementBank cost = pv.getCost();
        if(elementBank.remove(cost)) {
            TurnHandler.getInstance().accept(pv);
            return pv;
        }
        // if the virologist didn't have enough material
        return null;
    }

    /**
     * This method create a Vaccine with the paralyze geneticcode.
     * @return The Vaccine that has been created.
     */
    @Override
    public Vaccine createVaccine(ElementBank elementBank) {
        Vaccine pv = new Vaccine(this);
        // handle cost
        ElementBank cost = pv.getCost();
        if(elementBank.remove(cost)) {
            TurnHandler.getInstance().accept(pv);
            return pv;
        }
        // if the virologist didn't have enough material
        return null;
    }
}

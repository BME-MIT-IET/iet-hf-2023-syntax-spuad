package main.com.teamalfa.blindvirologists.agents.virus;

import main.com.teamalfa.blindvirologists.agents.genetic_code.BearCode;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class BearVirus extends DanceVirus{

    public BearVirus(){
        priority = 1;
        geneticCode = new BearCode();
        name = "bear virus";
        cost.setNucleotide(40);
        cost.setAminoAcid(40);
    }

    /**
     * This method infects the Virologist,
     * if the infection was successful the Virologist is turned into a bear.
     * @param target The Virologist the Virus was used on.
     */
    @Override
    public void apply(Virologist target) {
        if(target.infectedBy(this)) {
            this.addVirologist(target);
            target.addVirus(this);
            target.turntoBear();
        }
    }

    /**
     * This method doesn't do anything,
     * because the BearVirus(unlike the other viruses) doesn't expire.
     */
    @Override
    public void step(){}
}

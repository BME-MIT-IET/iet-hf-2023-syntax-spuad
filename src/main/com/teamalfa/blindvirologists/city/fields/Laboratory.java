package main.com.teamalfa.blindvirologists.city.fields;

import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class Laboratory extends Field{
    private GeneticCode geneticCode;

    /**
     * This method is called when the Virologist searches the Laboratory they are standing on.
     * It puts the geneticCode from the laboratory into their Backpack.
     * @param virologist The Virologist who stands on the Field and searches it.
     */
    @Override
    public void searchedBy(Virologist virologist) {
        //todo
    }

    /**
     * Adds the virologist to the field, sets the field as the virologist's field
     * The genetic code infects them.
     * @param v The virologist that's stepped on the field.
     */
    @Override
    public void accept(Virologist v) {
        super.accept(v);
        if(geneticCode != null) {
            geneticCode.autoInfect(v);
        }
    }

    //setter, getter
    public void setGeneticCode(GeneticCode geneticCode) {
        this.geneticCode = geneticCode;
    }

    public GeneticCode getGeneticCode() {
        return geneticCode;
    }
}
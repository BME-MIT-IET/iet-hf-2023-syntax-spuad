package com.teamalfa.blindvirologists.agents.virus;

import com.teamalfa.blindvirologists.agents.genetic_code.DanceCode;
import com.teamalfa.blindvirologists.city.fields.Field;
import com.teamalfa.blindvirologists.turn_handler.Game;

import java.util.ArrayList;
import java.util.Random;

public class DanceVirus extends Virus {

    public Random random = new Random();

    public DanceVirus(){
        geneticCode = new DanceCode();
        name = "dance virus";
        priority = 3;
        expiry = duration = 2 * Game.getNumberOfPlayers();

        cost.setNucleotide(15);
        cost.setAminoAcid(15);
    }

    /**
     * This method makes the Virologist move to a random Field instead of the chosen one.
     * There's a slight chance the chosen Field and the random Field will be the same.
     * @param current The Field the Virologist is standing on.
     * @return The chosen Field.
     */
    @Override
    public Field affectMovement(Field current) {
        return pickRandom(current.getNeighbours());
    }

    private Field pickRandom(ArrayList<Field> neighbours){
        int size = neighbours.size() - 1;
        int idx = size != 1 ? random.nextInt(size) : 0;
        return neighbours.get(idx);
    }
}
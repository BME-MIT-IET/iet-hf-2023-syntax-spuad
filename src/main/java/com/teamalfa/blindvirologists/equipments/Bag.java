package main.java.com.teamalfa.blindvirologists.equipments;

import main.java.com.teamalfa.blindvirologists.virologist.backpack.Backpack;
import main.java.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

public class Bag extends Equipment{
    private final int extraSize;

    public Bag(int size){
        extraSize = size;
        name = "bag";
    }

    public Bag() {
        extraSize = 20;
        name = "bag";
    }


    /**
     * Increases the size of the virologist's elementbank with extrasize.
     */
    @Override
    public void equip(){
        Backpack b = virologist.getBackpack();
        ElementBank e = b.getElementBank();
        e.increaseMaxSize(extraSize);
        virologist.addWorn(this);
    }

    @Override
    public String getType() {
        return "Bag";
    }

    /**
     * Decreases the size of the virologist's elementbank with extra-size.
     */
    @Override
    public void unEquip() {
        if (canBeUnequipped()) {
            Backpack b = virologist.getBackpack();
            ElementBank e = b.getElementBank();
            e.decreaseMaxSize(extraSize);
            virologist.removeWorn(this);
        }
    }

    /**
     * Checks if the bag is empty so it can be unequipped.
     * @return true if it's empty, false if there's still elements in it.
     */
    private boolean canBeUnequipped(){
        return this.virologist.getBackpack().getElementBank().getAminoAcidMaxSize() - extraSize > this.virologist.getBackpack().getElementBank().getAminoAcid()
                && this.virologist.getBackpack().getElementBank().getNucleotideMaxSize() - extraSize > this.virologist.getBackpack().getElementBank().getNucleotide();
    }
}

package main.com.teamalfa.blindvirologists.equipments;

import main.com.teamalfa.blindvirologists.virologist.Virologist;

abstract public class Equipment {
    protected Virologist virologist;
    protected String name;
    /**
     * This calls the virologists removeWorn function
     * and so the passive equipment will be removed from
     * the virologists bag
     * */
    public void unEquip(){
        virologist.removeWorn(this);
    }
    /**
     * This calls the virologists addWorn function
     * so the equipment can be added to the virologists bag
     * */
    public void equip(){
        virologist.addWorn(this);
    }
    /**
     * Setting the virologist who owns the equipment
     * */
    public void setVirologist(Virologist v) {this.virologist = v;}
    /**
     * @return false as default
     * */
    public boolean protect() {
        return false;
    }

    /**
     * getters
     */

    public String getName() { return name; }

    abstract public String getType();

    public Virologist getVirologist() {
        return virologist;
    }
}

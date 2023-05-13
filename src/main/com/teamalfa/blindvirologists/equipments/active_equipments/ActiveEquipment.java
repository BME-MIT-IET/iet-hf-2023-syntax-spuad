package main.com.teamalfa.blindvirologists.equipments.active_equipments;

import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.turn_handler.Steppable;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

abstract public class ActiveEquipment extends Equipment implements Steppable {
    protected int cooldownDuration;
    protected int cooldown;
    protected int usetime;

    //getter
    public int getCooldown() { return cooldown; }

    /**
     * abstract method that tells what happens when the item is used.
     * @param v
     */
    public abstract boolean use(Virologist v);

    /**
     * Adds the item to the virologist's worn items and also their active items.
     */
    public void equip(){
        virologist.addWorn(this);
        virologist.addActive(this);
    }

    /**
     * Removes the item from the virologist's worn items and also their active items.
     */
    public void unEquip(){
        virologist.removeWorn(this);
        virologist.removeActive(this);
    }

    /**
     * Sets the cooldown from 0 to the cooldownduration.
     */
    public void startCooldown() {
        cooldown = cooldownDuration;
    }

    /**
     * Tells what happens if the equipment was used too many times.
     */
    public abstract void wornOut();

    /**
     * Handles the cooldown of the equipment.
     */
    public abstract void step();

    /**
     * Returns the worn-out status of the equipment.
     * @return True, if the equipment is indeed worn out and cannot be objected to further use. False, if it can be used further.
     */
    public boolean isWornOut() {
        return usetime < 1;
    }

}

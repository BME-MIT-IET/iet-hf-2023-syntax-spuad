package main.com.teamalfa.blindvirologists.equipments.active_equipments;

import main.com.teamalfa.blindvirologists.agents.virus.Virus;
import main.com.teamalfa.blindvirologists.turn_handler.Game;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class Gloves extends ActiveEquipment {

    private Virus usedVirus = null;

    public Gloves() {
        cooldownDuration = 2 * Game.getNumberOfPlayers();
        usetime = 3;
        cooldown = 0;
        name = "glove";

        TurnHandler.accept(this);
    }

    //setters
    public void setUseTime(int num) {
        usetime = num;
    }

    public void setUsedVirus(Virus virus) {
        usedVirus = virus;
    }

    /**
     * Removes the virus from the virologist and applies it to the target virologist.
     * Only if the usetime is greater than 0, and the cool down is at zero.
     * Usetime-1;
     * And starts the cool down.
     * @param target
     */
    public boolean use(Virologist target){
        if(usedVirus != null && usetime > 0 && cooldown == 0) {
            cooldown = cooldownDuration;
            virologist.removeVirus(usedVirus);
            usedVirus.apply(target);
            usetime--;
            usedVirus = null;
            return true;
        }
        return false;
    }

    /**
     * Removes the equipment from the virologist.
     */
    public void wornOut() {
        virologist.removeWorn(this);
        virologist.removeActive(this);
        TurnHandler.getInstance().remove(this);
        if(virologist.getWornEquipment().contains(this))
        {
            virologist.removeWorn(this);
            virologist.removeActive(this);
        }
        if(virologist.getBackpack().getEquipmentPocket().getEquipmentHolder().contains(this))
            virologist.getBackpack().getEquipmentPocket().getEquipmentHolder().remove(this);
    }

    /**
     * If the cool down is greater than zero, decreases it.
     * If the usetime is zero calls the wornout method.
     */
    public void step() {
        if(usetime == 0)
            wornOut();
        else if(cooldown > 0)
            cooldown--;
    }

    //getters

    @Override
    public String getType() {
        return "Gloves";
    }
}

package main.com.teamalfa.blindvirologists.virologist.backpack.pockets;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.city.fields.SafeHouse;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.virologist.Virologist;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;

import java.util.ArrayList;

public class EquipmentPocket extends Pocket{

    private Backpack backpack;
    private ArrayList<Equipment> equipmentHolder = new ArrayList<>();
    //private int wornSize;

    public EquipmentPocket(Backpack b){
        backpack = b;
        //wornSize = 3;
        maxSize = 5;
    }

    //getters, setters
    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack b) {
        backpack = b;
    }

    public ArrayList<Equipment> getEquipmentHolder() {
        return equipmentHolder;
    }

    public void setEquipEquipmentHolder(ArrayList<Equipment> e) {
        equipmentHolder = e;
    }

    /**
     * Adds an equipment to the pocket, if there's enough place.
     * @param equipment The new equipment
     * @return true if it was successful, false if it wasn't.
     */
    public boolean add(Equipment equipment) {
        Boolean ret = false;
        if(equipmentHolder.size() < maxSize){
            equipmentHolder.add(equipment);
            ret = true;
        }
        return ret;
    }

    //getter
    @Override
    public int getCurrentSize() {
        return equipmentHolder.size();
    }
}

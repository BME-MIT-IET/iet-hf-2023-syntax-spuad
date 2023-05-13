package main.com.teamalfa.blindvirologists.virologist.backpack.pockets;

import main.com.teamalfa.blindvirologists.equipments.Equipment;

abstract public class Pocket {

    protected int maxSize;

    /**
     * abstract method
     * @return the current size of the pocket
     */
    public abstract int getCurrentSize();

    public int getMaxSize() {
        return maxSize;
    }
}

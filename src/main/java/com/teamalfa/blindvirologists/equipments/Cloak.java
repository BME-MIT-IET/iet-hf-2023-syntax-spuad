package main.java.com.teamalfa.blindvirologists.equipments;

import java.util.Random;

public class Cloak extends Equipment{
    private Random helper = new Random();

    private final int protectionRate;

    public Cloak(){
        protectionRate = 823;
        name = "cloak";
    }

    /**
     * Tells if the cloak protected the virologist from an infection.
     * @return true if it did, false if it did not.
     */
    @Override
    public boolean protect(){
        boolean tmp = helper.nextInt(1001) < protectionRate;
        if(tmp){
            virologist.getGame().creativeNotify("Infection blocked by cape");
        }
        else {
            virologist.getGame().creativeNotify("Cape didn't block the infection.");
        }
        return tmp;
    }

    @Override
    public String getType() {
        return "Cloak";
    }
}

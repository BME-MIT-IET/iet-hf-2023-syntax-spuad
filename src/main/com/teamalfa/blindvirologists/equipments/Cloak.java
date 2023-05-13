package main.com.teamalfa.blindvirologists.equipments;

import main.com.teamalfa.blindvirologists.consoleController.random.MyRandom;

import java.util.Random;

public class Cloak extends Equipment{
    MyRandom random;

    private final int protectionRate;

    public Cloak(){
        protectionRate = 823;
        name = "cloak";
    }

    /**
     * Tells if the cloak protected the virologist from an infection.
     * @return true if it did, false if it did not.
     */
    public boolean protect(){
        boolean tmp = new Random().nextInt(1001) < protectionRate;
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

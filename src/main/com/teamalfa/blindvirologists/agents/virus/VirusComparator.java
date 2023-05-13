package main.com.teamalfa.blindvirologists.agents.virus;

import java.util.Comparator;

public class VirusComparator implements Comparator<Virus> {
    /**
     *
     * This comparator sorts virus in ascending order by their priority number.
     *
     * */

    @Override
    public int compare(Virus v1, Virus v2) {
        return v1.getPriority() - v2.getPriority();
    }
}
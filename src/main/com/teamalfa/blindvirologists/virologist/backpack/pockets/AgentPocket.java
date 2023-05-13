package main.com.teamalfa.blindvirologists.virologist.backpack.pockets;

import main.com.teamalfa.blindvirologists.agents.Agent;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;

import java.util.ArrayList;

public class AgentPocket extends Pocket {

    private Backpack backpack;
    private ArrayList<Agent> agentHolder = new ArrayList<Agent>();

    public AgentPocket(Backpack b) {
        backpack = b;
        maxSize = 6;
    }

    /**
     * Adds the agent to the packet if there's place.
     * @param agent The added agent.
     * @return False if it was unsuccessful, tru if it was successfull
     */
    public boolean addAgent(Agent agent) {
        if(agentHolder.size() < maxSize) {
            agentHolder.add(agent);
            agent.setTarget(backpack.getVirologist());
            return true;
        }
        return false;
    }

    /**
     * Removes the agent from the agentHolder
     * @param a The agent that has been removed.
     */
    public void removeAgent(Agent a) {
        agentHolder.remove(a);
    }

    //getters
    public int getCurrentSize() {
        return  agentHolder.size();
    }

    public ArrayList<Agent> getAgentHolder() {
        return agentHolder;
    }
}

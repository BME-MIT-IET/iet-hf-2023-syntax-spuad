package main.com.teamalfa.blindvirologists.virologist;

import GUI.view.frames.Notifiable;
import main.com.teamalfa.blindvirologists.agents.Agent;
import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.agents.virus.Virus;
import main.com.teamalfa.blindvirologists.agents.virus.VirusComparator;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.city.fields.SafeHouse;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.ActiveEquipment;
import main.com.teamalfa.blindvirologists.turn_handler.Game;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents the virologist who is the player.
 */
public class Virologist {
    /**
     * The name
     */
    private final String name;
    /**
     * Vaccine genetic codes.
     */
    private final ArrayList<GeneticCode> protectionBank;
    /**
     * The active viruses.
     */
    private final ArrayList<Virus> activeViruses;
    /**
     * The worn equipments.
     */
    private final ArrayList<Equipment> wornEquipment = new ArrayList<>();
    /**
     * The active equipments,
     */
    private final ArrayList<ActiveEquipment> activeEquipments = new ArrayList<>();
    /**
     * The backpack
     */
    private final Backpack backpack;
    /**
     * The field it stands on.
     */
    private Field field;
    /**
     * Connects the GUI with the modell
     */
    private Notifiable game;
    /**
     * The fields that has been searched by the virologist
     */
    private final ArrayList<Field> discoveredFields = new ArrayList<>();
    /**
     * The max actions.
     */
    private static final int maxActions = 5;

    /**
     * Number of actions.
     */
    private int actions;


    /**
     * Constructs a virologist with an empty inventory.
     * @param name The name od the virologist.
     */
    public Virologist(String name) {
        this.name = name;
        protectionBank = new ArrayList<>();
        activeViruses = new ArrayList<>();
        backpack = new Backpack(this);
        actions = maxActions;
    }

    //getters setters
    public void setNotifiable(Notifiable game) {
        this.game = game;
    }

    public ArrayList<GeneticCode> getProtectionBank() {
        return protectionBank;
    }
    public Field getField() {
        return field;
    }
    public void setField(Field f){
        this.field = f;
    }

    public String getName(){ return name; }
    public Backpack getBackpack() {
        return backpack;
    }
    public Notifiable getGame() { return game; }
    public ArrayList<Field> getDiscoveredFields() { return discoveredFields; }

    public int getActions(){ return actions; }
    public void setActions(int num ) { actions = num; }

    /**
     * Resets the action counter.
     */
    public void startTurn() {
        actions = maxActions;
        game.creativeNotify(name + "'s turn started.");
    }

    /**
     * The method is called when the virologist moves to another field,
     * it checks if the virologist is affected by any viruses, if yes
     * the affect-movement method of the virus with the highest priority number is called.
     * It overrides the parameter of the field the virologist would like to step onto, or returns with null.
     * If the return statement is null than the destination field doesn't change, if it's not then it changes to the return statement.
     * After that the virologist is removed from their current field, and is accepted by the destination field.
     * @param destination the field the virologist would like to step onto.
     */
    public void move(Field destination) {
        if(actions > 0) {
            Field modified = null;

            if (!activeViruses.isEmpty()) modified = activeViruses.get(0).affectMovement(field);
            if (modified != null) destination = modified;

            field.remove(this);
            destination.accept(this);

            field = destination;

            actions--;
            if(!isParalyzed())
                game.creativeNotify("Moved.");
            else
                game.creativeNotify("Paralyzed.");
        }
    }

    /**
     * Uses an active equipment on a Virologist.
     * @param a active equipment that is used
     * @param v targeted virologist
     * @return true if it can be done and were successful, false if the virologist is paralízed or doesnt have action points, or the use were unsuccessful.
     */
    public boolean use(ActiveEquipment a, Virologist v) {
        if(!(checkUsageAffect()) && actions > 0) {
            actions--;
            if(a.getCooldown() == 0) {
                boolean result = a.use(v);
                game.creativeNotify(a.getName() + " used on " + v.getName() + ".");
                return result;
            }
            else{
                game.creativeNotify(a.getName() + "is on cooldown.");
            }
        }
        return false;
    }

    /**
     * This method is called when the virologist uses an agent on another virologist.
     * It calls the agent's apply method to the other virologist.
     * @param a The agent that is used on a virologist.
     * @param v The virologist the agent is used on.
     */
    public void use(Agent a, Virologist v){
        if (a != null && !(checkUsageAffect()) && actions > 0){
            a.apply(v);
            backpack.getAgentPocket().removeAgent(a);
            game.creativeNotify(a.getName() + " used on " + v.getName() + ".");
        }
    }

    /**
     * Learns the genetic code that's on the laboratory's wall.
     * @param gc The genetic that's on the laboratory's wall.
     * @return True if it was learned, false otherwise.
     */
    public boolean learn(GeneticCode gc) {
        if(!(this.checkUsageAffect()) && gc != null && actions > 0) {
            for(GeneticCode alreadyLearnt : backpack.getGeneticCodePocket().getGeneticCodes()) {
                if(gc.equals(alreadyLearnt)) {
                    return false;
                }
            }
            backpack.add(gc);
            actions--;
            game.creativeNotify(gc.getName() + "learnt.");
            return true;
        }
        return false;
    }

    /**
     * Picks up the equipment, if not paralyzed
     * @param equipment The picked up equipment
     * @return true if it was successful, false if it wasn't
     */
    public boolean pickUpEquipment(Equipment equipment) {
        if(!isParalyzed() && actions > 0) {
            if(field.canChangeEquipment()) {
                SafeHouse safeHouse = (SafeHouse) field;
                if (safeHouse.getEquipments().contains(equipment)) {
                    if (backpack.add(equipment)) {
                        equipment.setVirologist(this);
                        safeHouse.remove(equipment);
                        game.creativeNotify(equipment.getName() + " picked up.");
                        actions--;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Calls the field's searchedby method.
     */
    public void pickUpMaterial() {
        if(!isParalyzed())
            field.searchedBy(this);
    }

    /**
     * If it's possuvle calls the backpack's add method.
     * @param elements The elments that needs to be added.
     */
    public void pickUpMaterial(ElementBank elements) {
        if(!isParalyzed() && actions > 0 && !backpack.getElementBank().isFull()) {
            backpack.add(elements);
            actions--;
            game.creativeNotify("Element added.");
        }
        else {
            game.creativeNotify("ElementBank is full.");
        }
    }

    /**
     * This method is called when the virologist is being robbed.
     * @return the virologist backpack, if the virologist is paralyzed, null if the virologist is not paralyzed.
     */
    public Backpack robbed() {
        if(!(activeViruses.isEmpty())){
            if(activeViruses.get(0).affectRobbability()){
                game.creativeNotify(name + " were robbed.");
                return backpack;
            }
        }
        return null;
    }

    /**
     * This method is called when the virologist tries to rob another virologist.
     * It calls the other virologist's robbed method.
     * @param v The virologist that is being robbed.
     * @return the virologist's backpack or null.
     */
    public Backpack rob(Virologist v) {
        if(!(checkUsageAffect()) && actions > 0) {
            actions--;
            game.creativeNotify(name + " robbed " + v.getName() + ".");
            return v.robbed();
        }
        game.creativeNotify("You can't rob" + v.getName() + ". He is not paralyzed!");
        return null;
    }

    /**
     * This method is called when a virologist gets infected by a virus.
     * @param virus The virus that the virologist gets infected by.
     * @return true if rhe infection was successful, otherwise it returns false.
     */
    public boolean infectedBy(Virus virus) {

        // check vaccine protection
        for(GeneticCode code : protectionBank) {
            if(code.equals(virus.getGeneticCode())) {
                return false;
            }
        }

        // check equipment protection
        for(Equipment equipment : wornEquipment) {
            if(equipment.protect()) {
                return false;
            }
        }

        activeViruses.add(virus);
        game.creativeNotify(name + " were infected by " + virus.getName() + ".");
        return true;
    }

    /**
     * This method is called when the virologist gets a vaccine shot.
     * @param vaccine The vaccine that gets injected to the virologist.
     */
    public void protectedBy(Vaccine vaccine) {
        GeneticCode geneticcode = vaccine.getGeneticCode();
        protectionBank.add(geneticcode);
        game.creativeNotify(name + " got vaccinated.");
    }

    /**
     * This method is called when an active virus is expired and it gets removed from the virologist.
     * @param virus The expired virus.
     */
    public void removeVirus(Virus virus) {
        activeViruses.remove(virus);
        game.creativeNotify(virus.getName() + " is over.");
    }

    /**
     * This method is called when a vaccine expires, and it gets removed from the Virologist's protectionBank.
     * @param vaccine The expired vaccine.
     */
    public void removeVaccine(Vaccine vaccine) {
        protectionBank.remove(vaccine.getGeneticCode());
        game.creativeNotify(vaccine.getName() + " is over.");
    }

    /**
     * This method is called when the virologist steps on a new field and choses to explore it.
     * It calls the current field's searchedBy method.
     */
    public void search() {
        if(actions > 0 && !isParalyzed()) {
            //field.searchedBy(this);
            actions--;
            discoveredFields.add(field);
            game.creativeNotify(name + " searched field.");
        }

    }

    /**
     * This method is called when the Virologist does some kind of action,
     * it checks if the virologist is affected by anything that would block the virologist from doing the action.
     * @return true, if it blocks the action, otherwise it returns false.
     */
    private boolean checkUsageAffect() {

        if(activeViruses.isEmpty()){
            return false;
        }
        return activeViruses.get(0).affectUsage();
    }

    /**
     * When the virologist is infected by a new Virus the method adds said virus to the active viruses list.
     * @param virus The virus the virologist gets infected by.
     */
    public void addVirus(Virus virus) {
        activeViruses.add(virus);
        sortViruses();
    }

    /**
     * This method is called when the Virologist removes an equipment from themselves,
     * it removes the equipment from the wornEquipments list.
     * @param equipment The equipment the virologist removed.
     */
    public void removeWorn(Equipment equipment) {
        wornEquipment.remove(equipment);
    }

    /**
     * This method is called when the Virologist starts wearing an equipment,
     * it adds the equipment from the wornEquipments list.
     * @param equipment The equipment the virologist added.
     */
    public void addWorn(Equipment equipment){
        if(wornEquipment.size() < 3)
            wornEquipment.add(equipment);
    }

    /**
     * This method is called when the Virologist starts wearing an active equipment,
     * it adds the activeequipment from the ActiveEquipments list.
     * @param activeEquipment The activeequipment the virologist added.
     */
    public void addActive(ActiveEquipment activeEquipment) {
        if(wornEquipment.size() < 3)
            activeEquipments.add(activeEquipment);
    }

    /**
     * This method is called when the Virologist stops wearing an activeequipment,
     * it removes the activeequipment from the ActiveEquipments list.
     * @param activeEquipment The activeequipment the virologist removed.
     */
    public void removeActive(ActiveEquipment activeEquipment) {
        activeEquipments.remove(activeEquipment);
    }

    /**
     * This method sorts the active viruses by their priority number in a ascending order.
     */
    private void sortViruses(){
        Collections.sort(activeViruses, new VirusComparator());
    }


    //getters
    public ArrayList<Virus> getViruses() {
        return activeViruses;
    }

    public ArrayList<Equipment> getWornEquipment() {
        return wornEquipment;
    }
    public ArrayList<ActiveEquipment> getActiveEquipments() { return  activeEquipments; }

    /**
     * Calls the field's (the one the virologist is currently standing on) destroy method.
     */
    public void destroy() {
        field.destroy();
        game.creativeNotify("Field destroyed.");
    }

    /**
     * Removes the virologist from the turnhandler or from the game.
     */
    public void die() {
        if(TurnHandler.GetOrder().contains(this)) {
            TurnHandler.getInstance().remove(this);
        }
        else {
            Game.getInstance().remove(this);
        }
        field.remove(this);
        actions = 0;
        game.creativeNotify(name + "died:c");
    }

    /**
     * Turns the virologist into bear. Removes the virologist from the turnhandler and gives the virologist to the game.
     */
    public void turntoBear() {
        if(!(Game.getInstance().getBears().contains(this))) {
            TurnHandler.getInstance().remove(this);
            Game.getInstance().accept(this);
        }
        actions = 0;
        game.creativeNotify(name + "turned to bear :c");
    }

    /**
     * Tosses the equipment to the ground if they are not wearing it,
     * and currently standing on a safehouse, and is not paralyzed.
     * @param e The tossed equipment.
     * @return true if it was successful, false otherwise.
     */
    public boolean toss(Equipment e){
        if(!(wornEquipment.contains(e)) && actions > 0){
            Virologist v = backpack.getVirologist();
            Field f = v.getField();
            if(f.canChangeEquipment()){
                backpack.getEquipmentPocket().getEquipmentHolder().remove(e);
                f.add(e);
                actions--;
                game.creativeNotify(name + " tossed " + e.getName() + ".");
                return true;
            }
        }
        return false;
    }

    /**
     * Robs a piece of equipment from it's original owner.
     * @param e
     */
    public boolean robEquipment(Equipment e) {
        if (actions > 0 && e.getVirologist().isParalyzed()) {
            Virologist target = e.getVirologist();
            boolean wasWorn = target.getWornEquipment().remove(e);
            target.getBackpack().getEquipmentPocket().getEquipmentHolder().remove(e);
            if (!isParalyzed()) {
                if (backpack.add(e)) {
                    e.setVirologist(this);
                    game.creativeNotify(name + " robbed " + e.getName() + " from " + e.getVirologist().getName() + ".");
                    actions--;
                    return true;
                }
                else {
                    target.getBackpack().add(e);
                    if (wasWorn)
                        target.getWornEquipment().add(e);
                    game.creativeNotify("You can't take " + e.getName() + ", because you have no free inventory space.");
                    return false;
                }
            }
            else {
                game.creativeNotify("You can't rob " + e.getName() + ", because you are paralyzed.");
                return false;
            }
        }
        game.creativeNotify("You can't rob " + e.getName() + ", because they are not paralyzed.");
        return false;
    }

    /**
     * Robs an agent from it's original owner.
     * @param a - the agent you want to rob
     * @param target - the agent's current owner
     */
    public boolean robAgent(Agent a, Virologist target) {
        if (actions > 0 && target.isParalyzed()) {
            target.getBackpack().getAgentPocket().getAgentHolder().remove(a);
            if (!isParalyzed()) {
                if (backpack.getAgentPocket().addAgent(a)) {
                    game.creativeNotify(name + " robbed " + a.getName() + " from " + target.getName() + ".");
                    actions--;
                    return true;
                }
                else {
                    target.getBackpack().getAgentPocket().addAgent(a);
                    game.creativeNotify("You can't take " + a.getName() + ", because you have no free inventory space.");
                    return false;
                }
            }
            else {
                game.creativeNotify("You can't rob " + target.getName() + ", because you are paralyzed.");
                return false;
            }
        }
        game.creativeNotify("You can't rob " + target.getName() + ", because they are not paralyzed.");
        return false;
    }

    /**
     * Robs the enemy virologist from elements.
     * @param target The targeted virologist.
     */
    public void robElements(Virologist target) {
        if (target.isParalyzed()) {
            if (!isParalyzed()) {
                ElementBank targetElementBank = target.getBackpack().getElementBank();
                ElementBank actorElementBank = backpack.getElementBank();
                actorElementBank.add(targetElementBank);
                game.creativeNotify("You robbed elements from " + target.getName() + ".");
            } else {
                game.creativeNotify("You can't rob " + target.getName() + ", because you are paralyzed.");
            }
        } else {
            game.creativeNotify("You can't rob " + target.getName() + ", because they are not paralyzed.");
        }
    }

    /**
     * Wears/unwears the equipment if they are not paralyzed and standing in a safe-house.
     * @param e The toggled equipment.
     */
    public void toggle(Equipment e){
        Virologist v = backpack.getVirologist();
        Field f = v.getField();
        if(f.canChangeEquipment() && actions > 0){
            boolean isParalysed = false;
            for (var vir : activeViruses) {
                if (isParalysed == vir.affectUsage());
                break;
            }
            if (!isParalysed) {
                actions--;
                if (wornEquipment.contains(e)) {
                    e.unEquip();
                    game.creativeNotify(name + " unequipped " + e.getName());
                }
                else {
                    e.equip();
                    game.creativeNotify(name + " equipped " + e.getName());
                }
            }
        }
    }

    /**
     * Tells if the virologist is paralyzed or not, there's another method that does the exact same thing
     * but that one is 3 lines long, while this one is only a line so ofc it's way cooler so we need to keep it
     * just for that reason.
     * @return True if paralyzed, false if not.
     */
    public boolean isParalyzed(){
        return !activeViruses.isEmpty() && activeViruses.get(0).affectUsage();
    }


    public GeneticCode getCodeByType(String typeToMatch) {
        for(GeneticCode code : backpack.getGeneticCodePocket().getGeneticCodes()) {
            if(code.getType().equals(typeToMatch)){
                return code;
            }
        }
        return null;
    }

    /**
     * Calls the field's (the one the virologist is currently standing on searchforvirologists method)
     * @return the method.
     */
    public ArrayList<Virologist> searchForVirologist() {
        if(actions > 0) {
            actions--;
            return field.searchForVirologist(this);
        }
        return null;
    }

    /**
     * Calls the Turnhandler's tick method. Notifies the game.
     */
    public void endTurn() {
            TurnHandler.tick();
            game.creativeNotify(name + "'s turn ended");
    }

}
package main.com.teamalfa.blindvirologists.consoleController;

import main.com.teamalfa.blindvirologists.agents.Agent;
import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.genetic_code.*;
import main.com.teamalfa.blindvirologists.agents.virus.*;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.city.fields.Laboratory;
import main.com.teamalfa.blindvirologists.city.fields.SafeHouse;
import main.com.teamalfa.blindvirologists.city.fields.StoreHouse;
import main.com.teamalfa.blindvirologists.equipments.Bag;
import main.com.teamalfa.blindvirologists.equipments.Cloak;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.ActiveEquipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Axe;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Gloves;
import main.com.teamalfa.blindvirologists.consoleController.random.MyRandom;
import main.com.teamalfa.blindvirologists.turn_handler.Game;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

import static main.com.teamalfa.blindvirologists.consoleController.ConsoleControllerHelper.*;
import static main.com.teamalfa.blindvirologists.consoleController.ErrorPrinter.*;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ConsoleController {
    private static HashMap<String,Field> fieldHashMap = new HashMap<>();
    private static HashMap<String, Virologist> virologistHashMap = new HashMap<>();
    private static HashMap<String, Backpack> backpackHashMap = new HashMap<>();
    private static HashMap<String, ElementBank> elementBankHashMap = new HashMap<>();
    private static HashMap<String, Equipment> equipmentHashMap = new HashMap<>();
    private static HashMap<String, GeneticCode> geneticCodeHashMap = new HashMap<>();
    private static HashMap<String, Agent> agentHashMap = new HashMap<>();

    private static HashMap<String, Integer> idCounter = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);
    String fileSeparator;

    public ConsoleController() {
        initIdCounter();
        fileSeparator = System.getProperty("file.separator");
    }

    private void initIdCounter(){
        for(Prefixes prefix : Prefixes.values())
            idCounter.put(prefix.toString(), 1);
    }
    private void reset() {
        fieldHashMap.clear();
        virologistHashMap.clear();
        backpackHashMap.clear();
        elementBankHashMap.clear();
        equipmentHashMap.clear();
        geneticCodeHashMap.clear();
        agentHashMap.clear();
        idCounter.clear();
        initIdCounter();
        System.setOut(System.out);
    }

    public void startProgram() {
        boolean stopped = false;
        while(!stopped)
            stopped = handleCommands(readCommands());
    }

    private ArrayList<String> readCommands() {
        // make array list from input string
        return splitParameters(scanner.nextLine());
    }

    private ArrayList<String> splitParameters(String parameters) {
        return new ArrayList<>(Arrays.asList(parameters.split(" ")));
    }

    private boolean handleCommands(ArrayList<String> input) {
        try{
            if(!printHelp(input)){
                // get command if not empty
                String command = getNextArgument(input);

                if(!command.equals("#") && command != null) {
                    switch(command.toLowerCase()){
                        case "createfield": createField(input); break;
                        case "createvirologist": createVirologist(input); break;
                        case "createelements": createElements(input); break;
                        case "createequipment": createEquipment(input); break;
                        case "creategeneticcode": createGeneticCode(input); break;
                        case "createagent": createAgent(input); break;
                        case "move": move(input); break;
                        case "pickupequipment":
                        case "dropequipment": pickDropEquipment(input, command.toLowerCase()); break;
                        case "rob": rob(input); break;
                        case "learngeneticcode": learnGeneticCode(input); break;
                        case "useequipment": useEquipment(input); break;
                        case "craftagent": craftAgent(input); break;
                        case "useagent": useAgent(input); break;
                        case "pickupmaterial": pickUpMaterial(input); break;
                        case "startturn": startTurn(input); break;
                        case "endturn": endTurn(input); break;
                        case "status": status(input); break;
                        case "toggle": toggle(input); break;
                        case "runscript": runScript(input); break;
                        case "search": search(input); break;
                        case "setrandom": setRandom(input); break;
                        case "destroy": destroy(input); break;
                        case "controlbears": controlBears(); break;
                        case "exit": return true;
                        default: System.out.println("Wrong command.");
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        return false;
    }

    private void controlBears() {
        Game.getInstance().controlBears();
    }

    private void rob(ArrayList<String> input) {
        String output = "";

        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        String targetId = getNextArgument(input);
        Virologist target = handleDoesNotExistError(targetId, virologistHashMap);

        Backpack targetBackpack = virologist.rob(target);
        if(targetBackpack != null) {
            ArrayList<Equipment> targetEquipments = targetBackpack.getEquipmentPocket().getEquipmentHolder();
            ElementBank targetElementBank = targetBackpack.getElementBank();

            String equipmentFlag = getNextArgument(input);

            if(equipmentFlag.equals("-eq")) {
                ArrayList<String> equipmentToStealIds = getParametersUntilFirstDash(input);
                ArrayList<Equipment> equipmentsToSteal = handleManyDoNotExistError(equipmentToStealIds, equipmentHashMap);
                if(targetEquipments.containsAll(equipmentsToSteal)) {
                    ArrayList<Equipment> equipmentsWhichFit = new ArrayList<>();

                    for(Equipment equipment : equipmentsToSteal) {
                        if(virologist.getBackpack().add(equipment)) {
                            equipmentsWhichFit.add(equipment);
                        }
                    }

                    targetEquipments.removeAll(equipmentsWhichFit);

                    output += "Virologist was robbed:\n";
                    output += "Robber: " + virologistId + "\n";
                    output += "Victim: " + targetId + "\n";
                    output += "Equipments: " + joinWithComma(equipmentsWhichFit, equipmentHashMap) + "\n";
                }
            }
            if(targetElementBank != null) {
                int currentNuc = virologist.getBackpack().getElementBank().getNucleotide();
                int currentAmi = virologist.getBackpack().getElementBank().getAminoAcid();

                virologist.getBackpack().add(targetElementBank);

                int pickedNuc = virologist.getBackpack().getElementBank().getNucleotide() - currentNuc;
                int pickedAmi = virologist.getBackpack().getElementBank().getAminoAcid() -currentAmi;

                output += "AminoAcid: " + pickedAmi + "\n";
                output += "Nucleotide: " + pickedNuc + "\n";
            }
        }
        String finalOut = output.isEmpty() ? "Virologist was not robbed" : output;
        System.out.println(finalOut);
    }

    private void createField(ArrayList<String> input) {
        // get neighbour ids until flag and check if all neighbours exist.
        ArrayList<String> neighbourIds = getParametersUntilFirstDash(input);
        ArrayList<Field> neighbours = handleManyDoNotExistError(neighbourIds, fieldHashMap);

        // strip down neighbour ids because they are no longer necessary
        input.removeAll(neighbourIds);

        // check if flag isn't missing
        if(!input.isEmpty())
            handleMissingFlagError("t", input);

        // by default create a "simple" field
        String type = (input.isEmpty()) ? "field" : input.remove(0);

        Field field = null;
        String idPrefix = "";

        switch (type) {
            case "field": field = new Field(); idPrefix = Prefixes.Field.toString(); break;
            case "laboratory": field = new Laboratory(); idPrefix = Prefixes.Laboratory.toString();  break;
            case "storehouse": field = new StoreHouse(); idPrefix = Prefixes.StoreHouse.toString(); break;
            case "safehouse": field = new SafeHouse(); idPrefix = Prefixes.SafeHouse.toString(); break;
            default: handleWrongTypeError(type, FIELD_TYPES);
        }

        // register object and get id
        field.setNeighbours(neighbours);
        String id = registerObject(field, fieldHashMap, idCounter, idPrefix);

        // print creation
        System.out.println("Field created:");
        System.out.println("ID: " + id);
        System.out.println("Type: " + capitalizeString(type));
        System.out.println("Neighbours: " + joinWithComma(neighbours, fieldHashMap));
    }

    private void createVirologist(ArrayList<String> input) {
        Field field = null;
        String fieldId = "";

        // Check if field is exists
        fieldId = getNextArgument(input);
        field = handleDoesNotExistError(fieldId, fieldHashMap);

        // create virologist and position on given field
        Virologist virologist = new Virologist("nameless virologist");
        Backpack backpack = virologist.getBackpack();
        ElementBank elementBank = virologist.getBackpack().getElementBank();
        field.accept(virologist);

        // register virologist, backpack and element bank
        String virologistId = registerObject(virologist, virologistHashMap, idCounter, Prefixes.Virologist.toString());
        String backpackId = registerObject(backpack, backpackHashMap, idCounter, Prefixes.Backpack.toString());
        registerObject(elementBank, elementBankHashMap, idCounter, Prefixes.ElementBank.toString());

        // print creation
        System.out.println("Virologist created:");
        System.out.println("ID: " + virologistId);
        System.out.println("Field: " + fieldId);
        System.out.println("Backpack: " + backpackId);
    }

    private void createElements(ArrayList<String> input) {
        // get all parameters before flag, should be element quantities and max sizes
        ArrayList<String> quantitiesParams = getParametersUntilFirstDash(input);
        input.removeAll(quantitiesParams);

        // check if quantity and max size syntax are correct
        int[] quantities = handleNucleotideAminoAcidQuantityFormat(quantitiesParams);
        int[] maxSizes = handleNucleotideAminoAcidSizeFormat(quantitiesParams);

        // check if flag is missing
        String flag = handleMissingFlagError("vs", input);

        ElementBank elementBank = new ElementBank(quantities[0], quantities[1], maxSizes[0], maxSizes[1]);
        String virologistId = ""; Virologist virologist = null;
        String storeHouseId = ""; StoreHouse storeHouse = null;

        if(flag.equals("-v")) {
            virologistId = getNextArgument(input);
            virologist = handleDoesNotExistError(virologistId, virologistHashMap);
            virologist.getBackpack().setElementBank(elementBank);
        }else {
            storeHouseId = getNextArgument(input);
            storeHouse = (StoreHouse) handleDoesNotExistError(storeHouseId, fieldHashMap);
            storeHouse.setElements(elementBank);
        }

        // register object
        String elementId = registerObject(elementBank, elementBankHashMap, idCounter, Prefixes.ElementBank.toString());
        String destination = virologistId.isEmpty() ? storeHouseId : virologistId;

        // print creation
        System.out.println("Elements created:");
        System.out.println("ID: " + elementId);
        System.out.println("Nucleotide: " + quantities[0]);
        System.out.println("AminoAcid: " + quantities[1]);
        System.out.println("NucleotideSize: " + maxSizes[0]);
        System.out.println("AminoAcidSize: " + maxSizes[1]);
        System.out.println("Destination: " + destination);
    }

    private void createEquipment(ArrayList<String> input) {
        String type = getNextArgument(input);

        Equipment equipment = null;

        // create equipment based on type or print error
        switch(type) {
            case "cloak": equipment = new Cloak(); break;
            case "bag": equipment = new Bag(); break;
            case "gloves": equipment = new Gloves(); break;
            case "axe": equipment = new Axe(); break;
            default: handleWrongTypeError(type, "cloak, bag, gloves or axe.");
        }

        // get flag or print error
        String flag = handleMissingFlagError("sv", input);

        String virologistId = ""; Virologist virologist = null;
        String safeHouseId = ""; SafeHouse safeHouse = null;
        boolean successful = true;

        // set destination based on flag
        if(flag.equals("-v")){
            virologistId = !input.isEmpty() ? input.remove(0) : "";
            virologist = handleDoesNotExistError(virologistId, virologistHashMap);
            successful = virologist.getBackpack().getEquipmentPocket().add(equipment);
            equipment.setVirologist(virologist);
        }else {
            safeHouseId = !input.isEmpty() ? input.remove(0) : "";
            safeHouse = (SafeHouse)handleDoesNotExistError(safeHouseId, fieldHashMap);
            safeHouse.add(equipment);
        }

        // register object
        String equipmentId = registerObject(equipment, equipmentHashMap, idCounter, Prefixes.Equipment.toString());
        String destination = virologistId.isEmpty() ? safeHouseId : virologistId;

        // print creation
        System.out.println("Equipment created:");
        System.out.println("ID: " + equipmentId);
        System.out.println("Type: " + type);
        System.out.println("Destination: " + destination);
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
    }

    private void createGeneticCode(ArrayList<String> input) {
        String type = getNextArgument(input);
        GeneticCode geneticCode = null;

        // create code based on type, handle error if type does not exist
        switch(type) {
            case "paralyze": geneticCode = new ParalyzeCode(); break;
            case "amnesia": geneticCode = new AmnesiaCode(); break;
            case "dance": geneticCode = new DanceCode(); break;
            case "bear": geneticCode = new BearCode(); break;
            default: handleWrongTypeError(type, GENETIC_CODES);
        }

        // check if flag is missing
        String flag = handleMissingFlagError("lv", input);

        String virologistId = ""; Virologist virologist = null;
        String laboratoryId = ""; Laboratory laboratory = null;

        // set destination based on flag
        if(flag.equals("-v")){
            virologistId = getNextArgument(input);
            virologist = handleDoesNotExistError(virologistId, virologistHashMap);
            virologist.getBackpack().add(geneticCode);
        }else {
            laboratoryId = getNextArgument(input);
            laboratory = (Laboratory) handleDoesNotExistError(laboratoryId, fieldHashMap);
            laboratory.setGeneticCode(geneticCode);
        }

        // register object
        String geneticCodeId = registerObject(geneticCode, geneticCodeHashMap, idCounter, Prefixes.GeneticCode.toString());
        String destination = virologistId.isEmpty() ? laboratoryId : virologistId;

        // print creation
        System.out.println("GeneticCode created:");
        System.out.println("ID: " + geneticCodeId);
        System.out.println("Type: " + type);
        System.out.println("Destination: " + destination);
    }

    private void createAgent(ArrayList<String> input) {
        String agentType = getNextArgument(input);
        Agent agent = null;

        // handle type of agent error
        if(!agentType.equals("vaccine") && !agentType.equals("virus"))
            handleWrongTypeError(agentType, "vaccine or virus.");


        String geneticCodeType = getNextArgument(input);

        // create agent based on agent type and genetic code type
        if(agentType.equals("vaccine")) {
            switch(geneticCodeType) {
                case "paralyze": agent = new Vaccine(new ParalyzeCode()); break;
                case "amnesia": agent = new Vaccine(new AmnesiaCode()); break;
                case "dance":  agent = new Vaccine(new DanceCode()); break;
                case "bear": agent = new Vaccine(new BearCode()); break;
                default: handleWrongTypeError(geneticCodeType, GENETIC_CODES);
            }
        }else {
            switch(geneticCodeType) {
                case "paralyze": agent = new ParalyzeVirus();break;
                case "amnesia": agent = new AmnesiaVirus();break;
                case "dance": agent = new DanceVirus();break;
                case "bear": agent = new BearVirus();break;
                default: handleWrongTypeError(geneticCodeType, GENETIC_CODES);
            }
        }

        // check if virologist exists and try to put agent into backpack
        String virologistId = getNextArgument(input);
        Virologist virologist = handleDoesNotExistError(virologistId, virologistHashMap);
        boolean successful = virologist.getBackpack().getAgentPocket().addAgent(agent);

        // register object
        String agentID = registerObject(agent, agentHashMap, idCounter, Prefixes.Agent.toString());

        // print creation
        System.out.println("Agent created:");
        System.out.println("ID: " + agentID);
        System.out.println("Type: " + agentType);
        System.out.println("GeneticCode: " + geneticCodeType);
        System.out.println("Virologist: " + virologistId);
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
    }

    private void move(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();

        // get start and destination field ids
        String startId = getObjectId(virologist.getField(), fieldHashMap);
        String destinationId = getNextArgument(input);

        // get destination or print error
        Field destination = handleDoesNotExistError(destinationId, fieldHashMap);

        int virusCount = virologist.getViruses().size();

        virologist.move(destination);

        // check if virologist moved
        if(destination != virologist.getField()) {
            destinationId = startId;
        }

        // print result
        System.out.println("Virologist moved:");
        System.out.println("Virologist: " + getObjectId(virologist, virologistHashMap));
        System.out.println("Origin: " + startId);
        System.out.println("Destination: " + destinationId);

        // handle bear infection
        if(getFieldTypeBasedOnId(destinationId).equals("Laboratory")) {
            Laboratory laboratory = (Laboratory)handleDoesNotExistError(destinationId, fieldHashMap);
            boolean successful = virusCount != virologist.getViruses().size();
            GeneticCode code = laboratory.getGeneticCode();

            Agent agent = new BearVirus();

            if(successful)
                agent = virologist.getViruses().get(virusCount);

            if(code != null && code.getType().equals("bear")) {
                handleBearCreated(virologist, agent, successful);
            }
        }
    }

    private void pickDropEquipment(ArrayList<String> input, String command) {
        // pick and drop actions are very similar, so handled in same function
        boolean pickAction = command.equals("pickupequipment");

        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        // get next argument or print error if missing
        String equipmentId = getNextArgument(input);

        // get equipment and field or print error
        Equipment equipment = handleDoesNotExistError(equipmentId, equipmentHashMap);
        Field field = virologist.getField();
        String fieldId = getObjectId(field, fieldHashMap);

        // check if toss/pick was successful
        boolean successful = pickAction ? virologist.pickUpEquipment(equipment) : virologist.toss(equipment);

        // print result
        System.out.println( pickAction ? "Equipment added to inventory:" : "Equipment dropped:");
        System.out.println("Virologist: " + virologistId);
        System.out.println("Equipment: " + equipmentId);
        System.out.println("Field: " + fieldId);
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
    }

    private void learnGeneticCode(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        handleWrongArgumentCountError(0,0, input);

        Field field = virologist.getField();
        String fieldId = getObjectId(field, fieldHashMap);
        String fieldType = getFieldTypeBasedOnId(fieldId);

        boolean successful = false;
        String geneticCodeId = "null";

        if(fieldType.equals("Laboratory")) {
            Laboratory laboratory = (Laboratory)field;
            GeneticCode geneticCode = laboratory.getGeneticCode();
            geneticCodeId = getObjectId(geneticCode, geneticCodeHashMap);
            successful = virologist.learn(geneticCode);
        }

        // print result
        System.out.println("Genetic code learned:");
        System.out.println("Virologist: " + virologistId);
        System.out.println("Laboratory: " + fieldId);
        System.out.println("GeneticCode: " + geneticCodeId );
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
    }

    private void useEquipment(ArrayList<String> input) { // TODO:
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        String equipmentId = getNextArgument(input);
        Equipment equipment = handleDoesNotExistError(equipmentId, equipmentHashMap);

        if(!virologist.getActiveEquipments().contains(equipment))
            printError("Current virologist does not have equipment " + equipmentId + ".");

        // now it is safe to cast
        ActiveEquipment activeEquipment = (ActiveEquipment) equipment;

        String targetId = getNextArgument(input);
        Virologist target = handleDoesNotExistError(targetId, virologistHashMap);

        String virusID = getNextArgument(input);
        if (virusID != null) {
            Agent agentToApply = ConsoleControllerHelper.handleDoesNotExistError(virusID, agentHashMap);
            if (activeEquipment instanceof Gloves && agentToApply instanceof Virus) {
                ((Gloves) activeEquipment).setUsedVirus((Virus) agentToApply);
            }
        }

        boolean result = virologist.use(activeEquipment, target);
        boolean isWornOut = activeEquipment.isWornOut();

        // print result
        System.out.println("Equipment used on Virologist:");
        System.out.println("Equipment: " + equipmentId);
        System.out.println("Virologist: " + virologistId);
        System.out.println("Target: " + targetId);
        System.out.println("Result: " + (result ? "Successful" : "Failed"));
        if (isWornOut && result) System.out.println("Inf: Last usage!");
    }

    private void craftAgent(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        // get agent and code types
        String agentType = getNextArgument(input);
        String codeType = getNextArgument(input);
        GeneticCode code = virologist.getCodeByType(codeType);

        Agent agent = null;
        String agentId = null;

        // if virologist owns code than try creating agent
        if(code != null) {
            if(agentType.equals("vaccine")) {
                agent = virologist.getBackpack().createVaccine(code);
            }else {
                agent = virologist.getBackpack().createVirus(code);
            }
        }

        // register agent
        if(agent != null) {
            agentId = registerObject(agent, agentHashMap, idCounter, Prefixes.Agent.toString());
        }

        // print result
        System.out.println("Agent crafted:");
        System.out.println("ID:" + agentId);
        System.out.println("Type: " + agentType);
        System.out.println("GeneticCode: " + codeType);
        System.out.println("Result: " + (agent != null ? "Successful" : "Failed"));
    }

    private void useAgent(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        // check if agent exists
        String agentId = getNextArgument(input);
        Agent agent = handleDoesNotExistError(agentId, agentHashMap);

        //check if target virologist exists
        String targetId = getNextArgument(input);
        Virologist target = handleDoesNotExistError(targetId, virologistHashMap);

        int virusCount = target.getViruses().size();
        int vaccineCount = target.getProtectionBank().size();
        virologist.use(agent, target);
        int virusCountAfter = target.getViruses().size();
        int vaccineCountAfter = target.getProtectionBank().size();

        boolean successful = (virusCount != virusCountAfter) || (vaccineCount != vaccineCountAfter);

        // print result
        System.out.println("Agent used on virologist:");
        System.out.println("Agent: " + agentId);
        System.out.println("Virologist: " + virologistId);
        System.out.println("Target: " + targetId);
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
    }

    private void pickUpMaterial(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        handleWrongArgumentCountError(0,0, input);

        Field field = virologist.getField();
        String fieldId = getObjectId(field, fieldHashMap);
        String fieldType = getFieldTypeBasedOnId(fieldId);

        int currentNuc = virologist.getBackpack().getElementBank().getNucleotide();
        int currentAmi = virologist.getBackpack().getElementBank().getAminoAcid();

        virologist.pickUpMaterial();

        int pickedNuc = virologist.getBackpack().getElementBank().getNucleotide() - currentNuc;
        int pickedAmi = virologist.getBackpack().getElementBank().getAminoAcid() -currentAmi;


        // print result
        System.out.println("Material picked up:");
        System.out.println("Field: " + fieldId);
        System.out.println("Nucleotide: " + pickedNuc);
        System.out.println("AminoAcid: " + pickedAmi);
        System.out.println("Virologist: " + virologistId);
    }

    private void startTurn(ArrayList<String> input) {
        // check if virologist exists
        String virologistId = getNextArgument(input);
        Virologist virologist = handleDoesNotExistError(virologistId, virologistHashMap);

        // set current virologist and reorder
        TurnHandler.setActiveVirologist(virologist);

        System.out.println(virologistId + "'s turn started.");
    }

    private void endTurn(ArrayList<String> input) {
        // get current virologist
        Virologist current = TurnHandler.getActiveVirologist();
        String currentId = getObjectId(current, virologistHashMap);
        ArrayList<Virologist> order = TurnHandler.GetOrder();

        // calculate next virologists idx
        int idx = order.indexOf(current);
        int nextIdx =  idx < order.size() - 1 ? idx+1 : 0;

        Virologist next = order.get(nextIdx);
        String nextId = getObjectId(next, virologistHashMap);

        TurnHandler.setActiveVirologist(next);

        // print result
        System.out.println(currentId+"'s turn ended. Next virologist is " + nextId + ".");
    }

    private void status(ArrayList<String> input) {
        for(String id : input) {
            String prefix = id.replaceAll("\\d", "");

            if(FIELD_PREFIXES.contains(prefix))
                printStatus(handleDoesNotExistError(id, fieldHashMap), false);

            switch (prefix) {
                case "V": printStatus(handleDoesNotExistError(id, virologistHashMap)); break;
                case "EB": printStatus(handleDoesNotExistError(id, elementBankHashMap)); break;
                case "GC": printStatus(handleDoesNotExistError(id, geneticCodeHashMap)); break;
                case "A": printStatus(handleDoesNotExistError(id, agentHashMap)); break;
                case "E": printStatus(handleDoesNotExistError(id, equipmentHashMap)); break;
                case "B": printStatus(handleDoesNotExistError(id, backpackHashMap)); break;
                default:
            }
            System.out.println();
        }
    }

    private void toggle(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);


        String equipmentId = getNextArgument(input);
        Equipment equipment = handleDoesNotExistError(equipmentId, equipmentHashMap);
        virologist.toggle(equipment);

        String info = virologist.getWornEquipment().contains(equipment) ? "wear" : "unwear";

        // print result
        System.out.println("Equipment toggled on Virologist:");
        System.out.println("Equipment: " + equipmentId);
        System.out.println("Virologist: " + virologistId);
        System.out.println("Inf: " + info);
    }

    private void runScript(ArrayList<String> input) {
        String path = getNextArgument(input);
        String outfile = getNextArgument(input);

        if(outfile == null){
            ErrorPrinter.printError("Please add filename for output.");
        }

        if(outfile.contains(fileSeparator)) {
            ErrorPrinter.printError("File name cannot contain " + fileSeparator + "in it!");
        }

        outfile = "rcs" + fileSeparator + "customtestoutputs" + fileSeparator + outfile;

        String script = "";
        String fullPath = System.getProperty("user.dir") + fileSeparator + path;

        System.setOut(new DoublePrintStream(System.out, outfile));

        try {
            script = new String(Files.readAllBytes(Paths.get(fullPath)));
        } catch (IOException e) {
            System.out.println("An error occurred while reading a testscript from " + fullPath + "!");
        }

        String[] lines = script.split("\n");
        for(String line : lines){
            if(!line.isEmpty()) {
                line = line.replaceAll("\r", "");
                handleCommands(splitParameters(line));
            }
        }
        reset();
    }

    private void search(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        Field field = virologist.getField();
        String fieldId = getObjectId(field, fieldHashMap);

        if(virologist.isParalyzed())
            ErrorPrinter.printError("You are paralyzed.");

        printStatus(field, true);
    }
    
    private void setRandom(ArrayList<String> input) { // TODO
        String yesOrNo = getNextArgument(input).toLowerCase();

        Boolean yesOrNoBool = null;
        Integer optionChoiceInt = null;

        if(yesOrNo.equals("random")) {
            MyRandom.getInstance().setChoiceDeterministic(false);
        }else {
            MyRandom.getInstance().setYesOrNoDeterministic(true);
            switch (yesOrNo) {
                case "true": yesOrNoBool = true; break;
                case "false": yesOrNoBool = false; break;
                default: yesOrNoBool = null;
            }
        }

        String optionChoice = getNextArgument(input);
        if(optionChoice.equals("random")) {
            MyRandom.getInstance().setChoiceDeterministic(false);
        }else {
            try {
                MyRandom.getInstance().setChoiceDeterministic(false);
                optionChoiceInt = Integer.parseInt(optionChoice);
            }catch (NumberFormatException e) {
                printError("Possible choices are random or a number");
            }
        }

        if(yesOrNoBool != null) MyRandom.getInstance().setYesOrNo(yesOrNoBool);
        if(optionChoiceInt != null) MyRandom.getInstance().setChosenNumber(optionChoiceInt);

        // print result
        System.out.println("Random behaviour changed:");
        System.out.println("yesOrNoType: " + capitalizeString(yesOrNo));
        System.out.println("choiceType: " + capitalizeString(optionChoice));
    }

    // Status print help methods
    private void printStatus(Virologist virologist) {
        // put derived classes into super arrays
        ArrayList<Equipment> activeWearing =  createSuperArrayList(virologist.getActiveEquipments());
        ArrayList<Agent> activeViruses = createSuperArrayList(virologist.getViruses());

        System.out.println("Virologist:");
        System.out.println("ID: " + getObjectId(virologist, virologistHashMap));
        System.out.println("Field: " + getObjectId(virologist.getField(), fieldHashMap));
        System.out.println("Backpack: " + getObjectId(virologist.getBackpack(), backpackHashMap));
        System.out.println("Wearing: " + joinWithComma(virologist.getWornEquipment(), equipmentHashMap));
        System.out.println("ActiveWearing: " + joinWithComma(activeWearing, equipmentHashMap));
        System.out.println("ActiveViruses: " + joinWithComma(activeViruses, agentHashMap));
        System.out.println("ProtectionBank: " + joinWithComma(virologist.getProtectionBank(), geneticCodeHashMap));
    }

    private void destroy(ArrayList<String> input) {
        String fieldId = getNextArgument(input);
        Field field = handleDoesNotExistError(fieldId, fieldHashMap);

        field.destroy();

        boolean isStorehouse = false;
        if(field instanceof StoreHouse)
            isStorehouse = true;
        System.out.println(isStorehouse ? "Elements in storehouse destroyed." : "Field is not storehouse.");
    }

    private void handleBearCreated(Virologist virologist, Agent virus, boolean successful) {
        String virusId = registerObject(virus, agentHashMap, idCounter, Prefixes.Agent.toString());

        System.out.println("\nAgent created:");
        System.out.println("ID: " + virusId);
        System.out.println("Type: Virus");
        System.out.println("GeneticCode: Bear");
        System.out.println("Virologist: " + getObjectId(virologist, virologistHashMap));
        System.out.println("Result: Successful");
        System.out.println();
        System.out.println("Agent used on Virologist:");
        System.out.println("Agent: " + virusId);
        System.out.println("Virologist: " + getObjectId(virologist, virologistHashMap));
        System.out.println("Target: " + getObjectId(virologist, virologistHashMap));
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
    }

    private void printStatus(Field field, boolean searched) {
        String id = getObjectId(field, fieldHashMap);
        String type = getFieldTypeBasedOnId(id);

        GeneticCode geneticCode = null; String geneticText = "null";
        ArrayList<Equipment> equipments = null; String equipmentText = "null";
        ElementBank elements = null; String elementsText = "null";

        if (type.equals("Laboratory")) {
            Laboratory laboratory = (Laboratory) field;
            geneticCode = laboratory.getGeneticCode();
            geneticText = getObjectId(geneticCode, geneticCodeHashMap);
        }
        else if(type.equals("SafeHouse")) {
            SafeHouse safeHouse = (SafeHouse) field;
            equipments = safeHouse.getEquipments();
            equipmentText = joinWithComma(equipments, equipmentHashMap);
        }else if(type.equals("StoreHouse")){
            StoreHouse storeHouse = (StoreHouse) field;
            elements = storeHouse.getElements();
            elementsText = getObjectId(elements, elementBankHashMap);
        }

        System.out.println((searched ? "Virologist searched:" : "Field:"));
        System.out.println((searched ? "Field: " : "ID: ") + id);
        System.out.println("Type: " + capitalizeString(type));
        System.out.println("Neighbours: " + joinWithComma(field.getNeighbours(), fieldHashMap));
        System.out.println("Virologists: " + joinWithComma(field.getVirologists(), virologistHashMap));
        System.out.println("GeneticCodes: " + geneticText);
        System.out.println("Equipments: " + equipmentText);
        System.out.println("Elements: " + elementsText);
    }

    private void printStatus(ElementBank elementBank) {
        System.out.println("ElementBank:");
        System.out.println("ID: " + getObjectId(elementBank, elementBankHashMap));
        System.out.println("Nucleotide: " + elementBank.getNucleotide());
        System.out.println("AminoAcid: " + elementBank.getAminoAcid());
        System.out.println("NucleotideSize: " + elementBank.getNucleotideMaxSize());
        System.out.println("AminoAcidSize: " + elementBank.getAminoAcidMaxSize());
    }

    private void printStatus(GeneticCode geneticCode) {
        System.out.println("GeneticCode:");
        System.out.println("ID: " + getObjectId(geneticCode, geneticCodeHashMap));
        System.out.println("GeneticCode: " + capitalizeString(geneticCode.getType()));
    }

    private  void printStatus(Equipment equipment) {
        System.out.println("Equipment:");
        System.out.println("ID: " + getObjectId(equipment, equipmentHashMap));
        System.out.println("Type: " + capitalizeString(equipment.getType()));
    }

    private void printStatus(Agent agent) {
        System.out.println("Agent:");
        System.out.println("ID: " + getObjectId(agent, agentHashMap));
        System.out.println("Name:" + capitalizeString(agent.getGeneticCode().getType()));
    }

    private void printStatus(Backpack backpack) {
        System.out.println("Backpack:");
        System.out.println("ID: " + getObjectId(backpack, backpackHashMap));
        System.out.println("Equipments: " + joinWithComma(backpack.getEquipmentPocket().getEquipmentHolder(), equipmentHashMap));
        System.out.println("Agents: " + joinWithComma(backpack.getAgentPocket().getAgentHolder(), agentHashMap));
        System.out.println("ElementBank: " + getObjectId(backpack.getElementBank(), elementBankHashMap));
    }

    public void runTest() {
        System.out.println("Choose a test to run! Enter a number between 0 and 38!");
        System.out.println(
                "0: Exit\n" +
                        "1: Virologist steps on a new field, and searches for another virologist and finds one\n" +
                        "2: Virologist steps on a new field, and searches for other virologist but can’t find any\n" +
                        "3: Virologist searches in a laboratory that contains dance genetic code\n" +
                        "4: Virologist searches in a laboratory that contains an already learnt dance genetic code\n" +
                        "5: Virologist searches in a laboratory that doesn’t contain genetic code\n" +
                        "6: Virologist steps into infected laboratory and gets infected by BearVirus\n" +
                        "7: Virologist steps into infected laboratory but wears a cloak and it doesn’t block the infection\n" +
                        "8: Virologist steps into infected laboratory but they are vaccinated against BearVirus\n" +
                        "9: Virologist searches in a storehouse and collects elements\n" +
                        "10: Virologist searches in an empty storehouse\n" +
                        "11: Virologist with a full bag searches in a storehouse\n" +
                        "12: Virologist searches a safehouse that contains an axe and picks it up\n" +
                        "13: Virologist searches an empty safehouse\n" +
                        "14: Virologist moves\n" +
                        "15: Virologist moves while affected by DanceVirus\n" +
                        "16: Virologist moves while affected by ParalyzeVirus\n" +
                        "17: Virologist moves while affected by AmnesiaVirus\n" +
                        "18: Virologist uses ParalyzeVirus on another Virologist, who’s not vaccinated and without equipment\n" +
                        "19: Virologist uses AmnesiaVirus on another Virologist who’s not vaccinated but wears cape, the cape blocks the virus\n" +
                        "20: Virologist uses AmnesiaVirus on another Virologist who’s not vaccinated but wears cape, the cape doesn’t block the virus\n" +
                        "21: VirologistA uses DanceVirus on VirologistB who’s not vaccinated but wears Gloves. Virologist B applies DanceVirus with gloves\n" +
                        "22: Virologist wants to create a ParalyzeVirus, but doesn't have enough elements\n" +
                        "23: Virologist creates an AmnesiaVaccine and uses it on itself\n" +
                        "24: Virologist starts to wear a bag\n" +
                        "25: Virologist takes off a bag\n" +
                        "26: Virologist can’t take off bag\n" +
                        "27: Virologist can’t wear an axe, because worn equipments are full\n" +
                        "28: Virologist uses glove for the third time\n" +
                        "29: Virologist uses a sharp axe on another virologist\n" +
                        "30: Virologist uses blunt axe on another virologist\n" +
                        "31: Virologist wants to toggle bag, but the Virologist isn't in a Safehouse\n" +
                        "32: Virologist tosses a cloak from the backpack to a Safehouse\n" +
                        "33: Virologist tosses a cloak from the backpack to a Field\n" +
                        "34: Virologist robs another Virologist\n" +
                        "35: Virologist can’t rob an enemy because they are not paralyzed\n" +
                        "36: VirologistA tries to use DanceVirus on VirologistB but VirologistA is under the effect of Paralyze Virus\n" +
                        "37: VirologistA infects VirologistB with BearVirus. The VirologistB is not vaccinated, and doesn’t wear any equipment. VirologistB turns to bear\n" +
                        "38: VirologistA infects VirologistB with BearVirus. The VirologistB doesn’t wear any equipment, but is vaccinated against bearvirus.");

        while (true) {
            try {
                String userInput = scanner.nextLine();
                int choice = Integer.parseInt(userInput);

                if (choice == 0)
                    return;


                if (choice >= 1 && choice <= 38) {
                    // if the user's choice is valid read the test script from the corresponding file
                    ArrayList<String> args = new ArrayList<String>();
                    args.add("rcs" + fileSeparator + "testscripts" + fileSeparator + "test" + userInput + ".txt");
                    String outfile = "rcs" + fileSeparator + "testoutputs" + fileSeparator + "test" + userInput + "_out.txt";
                    System.setOut(new DoublePrintStream(System.out, outfile));
                    runScript(args);
                }
                else {
                    // if the user's choice is invalid start the read process all over
                    throw new NumberFormatException("Invalid input! Enter a number between 0 and 38!");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Incorrect number format! Enter a number between 0 and 38!");
            }
        }
    }
}

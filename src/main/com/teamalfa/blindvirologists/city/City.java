package main.com.teamalfa.blindvirologists.city;

import main.com.teamalfa.blindvirologists.agents.GeneticCodeBank;
import main.com.teamalfa.blindvirologists.agents.genetic_code.*;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.city.fields.Laboratory;
import main.com.teamalfa.blindvirologists.city.fields.SafeHouse;
import main.com.teamalfa.blindvirologists.city.fields.StoreHouse;
import main.com.teamalfa.blindvirologists.equipments.Bag;
import main.com.teamalfa.blindvirologists.equipments.Cloak;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Axe;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Gloves;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

import java.util.ArrayList;
import java.util.Random;

import java.lang.Math;
public class City {
    private static final City instance;
    private final ArrayList<Equipment> allEquipment = new ArrayList<>();
    private static final ArrayList<Field> allFields = new ArrayList<>();
    private static final ArrayList<Laboratory> allLaboratories = new ArrayList<>();
    private static final ArrayList<StoreHouse> allStoreHouses = new ArrayList<>();
    private static final ArrayList<SafeHouse> allSafeHouses = new ArrayList<>();
    private static final Random ran = new Random();

    //Creates one object in the beginning of the game.
    static {
        instance = new City();
    }

    //getter
    public static City getInstance() {
        return instance;
    }


    /**
     * ctr
     */
    private City() {
        //this.GenerateMap();
        allEquipment.add(new Gloves());
        allEquipment.add(new Axe());
        allEquipment.add(new Cloak());
        allEquipment.add(new Bag(50));
    }

    /**
     * Generates the HEXAGON map the players can play on.
     * With random generating random number of fields between 5 & 25
     */
    public void GenerateMap(){
        // get number of all possible genetic codes
        int numberOfCodes = GeneticCodeBank.getInstance().getCodes().size();

        // create laboratories with codes
        for(int i = 0; i < numberOfCodes; i++) {
            GeneticCode currentCode = GeneticCodeBank.getInstance().getCodes().get(i);

            int labCountWithCurrentCode = 0;

            // create laboratories with current genetic code
            while(labCountWithCurrentCode < numberOfCodes) {
                Laboratory lab = new Laboratory();
                lab.setGeneticCode(currentCode);
                allLaboratories.add(lab);
                labCountWithCurrentCode++;
            }
        }

        // create empty laboratories
        int numberOfLabs = allLaboratories.size();
        for(int j = 0; j < numberOfLabs; j++)
            allLaboratories.add(new Laboratory());


        // create safe houses with equipments
        int numberOfEquipments = allEquipment.size();

        // iterate through all possible equipments
        for (Equipment equipment : allEquipment) {
            int safeCountWithCurrentEquipment = 0;

            // create safe houses with current equipment
            while (safeCountWithCurrentEquipment < numberOfEquipments) {
                SafeHouse safeHouse = new SafeHouse();
                safeHouse.add(equipment);
                allSafeHouses.add(safeHouse);
                safeCountWithCurrentEquipment++;
            }
        }

        // create empty safe houses
        int numberOfSafe = allSafeHouses.size();
        for (int j = 0; j < numberOfSafe; j++)
            allSafeHouses.add(new SafeHouse());

        // create store houses with random element numbers
        while(allStoreHouses.size() < allLaboratories.size() / 2) {
            ElementBank elements = new ElementBank(ran.nextInt(50), ran.nextInt(50));
            StoreHouse storeHouse = new StoreHouse();
            storeHouse.setElements(elements);
            allStoreHouses.add(storeHouse);
        }


        // create empty storehouses
        int numberOfStoreHouses = allStoreHouses.size();
        for(int i = 0; i < numberOfStoreHouses; i++)
            allStoreHouses.add(new StoreHouse());

        // create fields
        int numberOfFields = allLaboratories.size() + allSafeHouses.size() + allStoreHouses.size();
        for(int i = 0; i < numberOfFields; i++)
            allFields.add(new Field());

        connectFields();
    }

    /**
     * Connects the genrated fields,
     */
    private static void connectFields(){
        // create mixed array list including every field
        ArrayList<Field> allFieldsMixed = new ArrayList<>();
        allFieldsMixed.addAll(allFields);
        allFieldsMixed.addAll(allLaboratories);
        allFieldsMixed.addAll(allSafeHouses);
        allFieldsMixed.addAll(allStoreHouses);

        // calculate width with square root of all fields
        int width = (int)Math.sqrt(allFieldsMixed.size());

        Field previousField = null;
        ArrayList<Field> previousLine = null;

        // iterate through all fields until empty
        while(!allFieldsMixed.isEmpty()) {
            // list for current line
            ArrayList<Field> currentLine = new ArrayList<>();

            // fill current line with random fields from all fields
            for(int i = 0; i < width; i++) {
                Field currentField = getRandom(allFieldsMixed);

                // when could not get random field break out of loop
                if(currentField == null)
                    break;

                // add current field to current line
                currentLine.add(currentField);

                // connect current field with previous field
                if(previousField != null)
                    previousField.setNeighbour(currentField);

                // connect current field with field from previous line with same idx
                if(previousLine != null) {
                    previousLine.get(i).setNeighbour(currentField);

                    // if it's not the last field in the line then connect
                    // with next field from previous line
                    if(i < width - 1)
                        previousLine.get(i+1).setNeighbour(currentField);
                }
                // save current field to previous field
                previousField = currentField;
            }
            // save current line to previous line
            previousLine = currentLine;
        }
    }

    public void generateTestMap() {
        // get number of all possible genetic codes
        int numberOfCodes = GeneticCodeBank.getInstance().getCodes().size();

        // create laboratories with codes
        for(GeneticCode currentCode : GeneticCodeBank.getInstance().getCodes()) {
            int labCountWithCurrentCode = 0;

            // create laboratories with current genetic code
            while(labCountWithCurrentCode < numberOfCodes) {
                Laboratory lab = new Laboratory();
                lab.setGeneticCode(currentCode);
                allLaboratories.add(lab);
                labCountWithCurrentCode++;
            }
        }

        // create safe houses with equipments
        int numberOfEquipments = allEquipment.size();

        // iterate through all possible equipments
        for (Equipment equipment : allEquipment) {
            int safeCountWithCurrentEquipment = 0;

            // create safe houses with current equipment
            while (safeCountWithCurrentEquipment < numberOfEquipments) {
                SafeHouse safeHouse = new SafeHouse();
                safeHouse.add(equipment);
                allSafeHouses.add(safeHouse);
                safeCountWithCurrentEquipment++;
            }
        }

        // create store houses with random element numbers
        while(allStoreHouses.size() < allLaboratories.size() / 2) {
            ElementBank elements = new ElementBank(25, 25);
            StoreHouse storeHouse = new StoreHouse();
            storeHouse.setElements(elements);
            allStoreHouses.add(storeHouse);
        }


        allFields.add(new Field());

        connectFields();
    }

    private static Field getRandom(ArrayList<Field> mixedFields){
        if(mixedFields.isEmpty())
            return null;

        int idx = mixedFields.size() != 1 ? ran.nextInt(mixedFields.size()-1) : 0;
        return mixedFields.remove(idx);
    }

    public static ArrayList<Laboratory> getAllLaboratories(){
        return allLaboratories;
    }

    public static ArrayList<SafeHouse> getAllSafeHouses(){
        return allSafeHouses;
    }

    public static ArrayList<StoreHouse> getAllStoreHouses(){
        return allStoreHouses;
    }
    public static ArrayList<Field> getAllFields(){ return allFields; }
}

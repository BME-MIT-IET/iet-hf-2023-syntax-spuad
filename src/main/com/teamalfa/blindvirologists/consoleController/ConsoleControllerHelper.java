package main.com.teamalfa.blindvirologists.consoleController;

import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ConsoleControllerHelper {

    public static final ArrayList<String> GENETIC_CODES = new ArrayList<>(Arrays.asList("paralyze", "amnesia", "dance", "bear"));
    public static final ArrayList<String> FIELD_TYPES = new ArrayList<>(Arrays.asList("laboratory", "safehouse", "storehouse"));
    public static final ArrayList<String> EQUIPMENT_TYPES = new ArrayList<>(Arrays.asList("cloak", "axe", "bag", "gloves"));
    public static final ArrayList<String> FIELD_PREFIXES = new ArrayList<>(Arrays.asList("F", "L", "ST", "SA"));

    public static ArrayList<String> getParametersUntilFirstDash(ArrayList<String> parameters) {
        // this method slices the parameters which are before a - flag.

        ArrayList<String> slicedParameters = new ArrayList<>();
        for(String parameter : parameters) {
            if(!parameter.contains("-")) {
                slicedParameters.add(parameter);
            }else break;
        }
        return slicedParameters;
    }

    public static <T> ArrayList<T> handleManyDoNotExistError(ArrayList<String> listOfIds, HashMap<String, T> hashMap) {
        ArrayList<T> existingObjects = new ArrayList<>();
        for(String id : listOfIds) {
            existingObjects.add(handleDoesNotExistError(id, hashMap));
        }
        return existingObjects;
    }

    public static <T> T handleDoesNotExistError(String idToCheck, HashMap<String, T> hashMap) {
        if(idToCheck == null)
            ErrorPrinter.printError("Missing object id.");
        T objectFound = hashMap.get(idToCheck);
        if(objectFound == null)
            ErrorPrinter.doesntExistError(idToCheck);
        return objectFound;
    }

    public static <T> String registerObject(
            T object,
            HashMap<String, T> objectHolder,
            HashMap<String, Integer> idCounter,
            String type
    ) {
        String id = type + idCounter.get(type).toString();
        objectHolder.put(id, object);

        int updatedCounter = idCounter.get(type) + 1;
        idCounter.replace(type, updatedCounter);
        return id;
    }

    public static String capitalizeString(String inp) {
        return inp.substring(0, 1).toUpperCase() + inp.substring(1);
    }

    public static boolean checkCorrectFormat(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public static int[] handleNucleotideAminoAcidQuantityFormat(ArrayList<String> input) {
        if(!input.isEmpty() && checkCorrectFormat("n\\d+a\\d+", input.get(0))) {
            String parameter = input.remove(0);

            String aminoPart = parameter.substring(parameter.indexOf('a'));
            String nucleoPart = parameter.replace(aminoPart, "");

            int[] quantities = new int[2];
            quantities[0] = Integer.parseInt(aminoPart.replaceAll("\\D+", ""));
            quantities[1] = Integer.parseInt(nucleoPart.replaceAll("\\D+", ""));

            return quantities;
        }
        ErrorPrinter.printError("Element quantity format should be n{number}a{number}.");
        return null;
    }

    public static int[] handleNucleotideAminoAcidSizeFormat(ArrayList<String> input){
        if(!input.isEmpty() && checkCorrectFormat("ns\\d+as\\d+", input.get(0))) {
            String parameter = input.remove(0);

            String aminoPart = parameter.substring(parameter.indexOf("as"));
            String nucleoPart = parameter.replace(aminoPart, "");

            int[] quantities = new int[2];
            quantities[0] = Integer.parseInt(aminoPart.replaceAll("\\D+", ""));
            quantities[1] = Integer.parseInt(nucleoPart.replaceAll("\\D+", ""));

            return quantities;
        }
        ErrorPrinter.printError("Element size format should be ns{number}as{number}.");
        return null;
    }

    public static String handleMissingFlagError(String flags, ArrayList<String> input){
        ArrayList<String> formattedFlags = new ArrayList<>();
        String flagToReturn = null;

        for(int i = 0; i < flags.length(); i++) {
            formattedFlags.add("-" + flags.charAt(i));
        }

        if(!input.isEmpty()) {
            for(String flag : formattedFlags) {
                if(flag.equals(input.get(0))) {
                    flagToReturn = input.remove(0);
                }
            }
        }
        if(flagToReturn == null)
            ErrorPrinter.printError("Missing flag(s) " + formattedFlags + ".");
        return flagToReturn;
    }

    public static String handleWrongArgumentCountError(Integer min, Integer max, ArrayList<String> input) {
        if(input.size() < min || input.size() > max){
            ErrorPrinter.printWrongArgumentCountError();
        }
        return !input.isEmpty() ? input.remove(0) : null;
    }

    public static String getNextArgument(ArrayList<String> input) {
        return !input.isEmpty() ? input.remove(0) : null;
    }

    public static <T> String getObjectId(T object, HashMap<String, T> hashMap) {
        for(String id : hashMap.keySet())
            if(hashMap.get(id) == object)
                return id;

        return null;
    }

    public static <T> ArrayList<String> getManyObjectIds(ArrayList<T> objects, HashMap<String, T> hashMap) {
        ArrayList<String> idsToReturn = new ArrayList<>();
        for(T object : objects)
            idsToReturn.add(getObjectId(object, hashMap));
        return idsToReturn;
    }

    public static Virologist handleCurrentVirologistError() {
        Virologist virologist = TurnHandler.getActiveVirologist();
        if(virologist == null)
            ErrorPrinter.printError("There are no virologists yet.");
        return virologist;
    }

    public static <T> String joinWithComma(ArrayList<T> objects, HashMap<String, T> hashMap) {
        return objects.isEmpty() || objects == null ? "null" : String.join(", ",getManyObjectIds(objects, hashMap));
    }

    public static <T> ArrayList<T> createSuperArrayList(ArrayList<? extends T> list) {
        ArrayList<T> listToReturn = new ArrayList<>();
        for(T object : list)
            listToReturn.add(object);

        return listToReturn;
    }

    public static String getFieldTypeBasedOnId(String id) {
        String prefix = id.replaceAll("\\d", "").toLowerCase();
        String type = "";
        switch (prefix) {
            case "l":  type = "Laboratory"; break;
            case "st": type = "StoreHouse"; break;
            case "sa": type = "SafeHouse"; break;
            default: type = "Field";
        }
        return type;
    }

    public static boolean printHelp(ArrayList<String> input) {
        if(input.size() == 2 && input.get(1).equals("help")) {
            String helpMsg;

            switch(input.get(0)) {
                case "createfield": helpMsg = "createfield [neighbouring field IDs] [-t <laboratory/storehouse/safehouse>]"; break;
                case "createvirologist": helpMsg = "createvirologist <field ID>"; break;
                case "createelements": helpMsg =  "createelements n<number>a<number> ns<number>as<number> <-v virolgoist Id or -s safehouse ID->"; break;
                case "createequipment": helpMsg = "createequipment <cloak/bag/gloves/axe> <-s safehouse ID/-v virologist ID>"; break;
                case "creategeneticcode": helpMsg = "creategeneticcode <paralyze/amnesia/dance/bear> <- laboratory ID/-v virologist ID>"; break;
//                case "createagent": createAgent(input); break;
//                case "move": move(input); break;
//                case "pickupequipment": pickUpEquipment(input); break;
//                case "dropequipment": dropEquipment(input); brea
//                case "learngeneticcode": learnGeneticCode(input); break;
//                case "useequipment": useEquipment(input); break;
//                case "craftagent": craftAgent(input); break;
//                case "useagent": useAgent(input); break;
//                case "pickupmaterial": pickUpMaterial(input); break;
//                case "startturn": startTurn(input); break;
//                case "status": status(input); break;
//                case "toggle": toggle(input); break;
//                case "runscript": runScript(input); break;
//                case "search": search(input); break;
//                case "setrandom": setRandom(input); break;
//                case "exit": return true;
                default: helpMsg = "";
            }
            if(!helpMsg.isEmpty()){
                System.out.println(helpMsg);
                return true;
            }
        }
        return false;
    }
}

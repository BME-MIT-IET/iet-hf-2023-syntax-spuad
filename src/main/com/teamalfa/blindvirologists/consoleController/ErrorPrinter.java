package main.com.teamalfa.blindvirologists.consoleController;

import java.util.ArrayList;

public class ErrorPrinter {

    public static void printError(String msg) throws IllegalArgumentException {
        throw new IllegalArgumentException("ERROR: " + msg);
    }

    public static  void doesntExistError(String objectId) {
        printError(objectId.isEmpty() ? "Missing object ID." : objectId + " does not exist.");
    }
    public static void tooManyParametersError() {
        printError("Too many parameters.");
    }

    public static void handleWrongTypeError(String kind, String options) {
        String front = kind.isEmpty() ? "Missing keyword." : "Wrong key word: '" + kind + "'.";
        printError(front + " Possible choices are " + options);
    }

    public static void handleWrongTypeError(String kind, ArrayList<String> listOfOptions) {
        String options = String.join(", ", listOfOptions) + ".";
        handleWrongTypeError(kind, options);
    }

    public static void printWrongArgumentCountError() {
        printError("Wrong argument count.");
    }
}

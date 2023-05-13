package main.com.teamalfa.blindvirologists;

import GUI.view.frames.menuFrames.MenuFrame;
import main.com.teamalfa.blindvirologists.consoleController.ConsoleController;

import java.util.Scanner;

public class Main {
    // SET THIS TO CHANGE THE STARTUP MODE TO GRAPHIC / CONSOLE
    private static final boolean GRAPHIC = true;

    private static void console() {
        ConsoleController controller = new ConsoleController();

        System.out.println("Would you like to play the game or run tests?");
        System.out.println("1. Play Game");
        System.out.println("2. Run Tests");

        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            try {
                int choice = Integer.parseInt(inputScanner.nextLine());
                if (choice == 1) {
                    controller.startProgram();
                    return;
                }
                if (choice == 2) {
                    controller.runTest();
                    return;
                } else {
                    throw new NumberFormatException("Invalid input!");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Incorrect number format! Enter either '1' or '2'!");
            }
        }

    }

    private static void graphic() {
        new MenuFrame();
    }

    public static void main(String[] args) {
        // start the program in graphic or console mode
        if (GRAPHIC)
            graphic();
        else
            console();
    }
}

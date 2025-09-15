package org.example;

import org.example.view.ConsoleIO;
import org.example.view.MenuController;

public class App {

    public static void main(String[] args) {

        // Initialize Variables
        ConsoleIO console = new ConsoleIO();
        MenuController menu = new MenuController();
        final String MAIN_MENU = """
                Main Menu
                  1. Display Cart
                  2. Remove an Item
                  3. Add an Item
                  4. Checkout
                  5. Exit""";
        boolean running = true;
        int userInput;

        // Main Loop
        while(running){
            userInput = console.getIntegerInputInRange(MAIN_MENU, 1, 5);
            running = menu.handleAction(userInput);
        }

        // Exit Message
        console.writeMessage("Goodbye!");

    }

}

package org.example.view;

import java.util.Scanner;

public class ConsoleIO {

    private Scanner console;

    public ConsoleIO(){
        console = new Scanner(System.in);
    }

    /**
     * Prints message to the screen.
     * This is a simple replacement for System.out.println()
     *
     * @param message The string to be displayed
     */
    public void writeMessage(String message){
        System.out.println(message);
    }

    /**
     * Gets a string input from the user
     *
     * @param prompt Message to be displayed to the user
     * @return A String inputted by the user
     */
    public String getInput(String prompt){
        writeMessage(prompt);
        return console.nextLine();
    }

    /**
     * Gets a whole number Integer input from the user. This method
     * will also ensure an integer is entered and not a string, double,
     * or any other input format.
     *
     * @param prompt Message to be displayed to the user
     * @return An integer inputted by the user
     */
    public int getIntegerInput(String prompt){
        String response;
        int input;
        while(true){
            response = getInput(prompt);
            try{
                input = Integer.parseInt(response);
                if(input < 0){
                    throw new NumberFormatException(); // negative numbers are never allowed
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a proper positive whole number integer.");
            } catch (Exception e) {
                System.out.println("Please enter a valid response.");
            }
        }
        return input;
    }

    /**
     * Prints the prompt and gets user input that is within the given range
     * with both the minimum and maximum inclusive
     *
     * @param prompt String to be printed to the user
     * @param minimum minimum value the user can input
     * @param maximum maximum valur the user can input
     * @return the inputted value from the user
     */
    public int getIntegerInputInRange(String prompt, int minimum, int maximum){
        int input;
        while(true){
            input = getIntegerInput(prompt);
            if(input < minimum || input > maximum){
                System.out.println("Please enter a valid number within the range " + minimum + " - " + maximum);
            } else{
                break;
            }
        }
        return input;
    }

}

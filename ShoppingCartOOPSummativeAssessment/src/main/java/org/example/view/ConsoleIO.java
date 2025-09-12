package main.java.org.example.view;

import java.util.Scanner;

public class ConsoleIO {

    private Scanner console;

    public ConsoleIO(){
        console = new Scanner(System.in);
    }

    public void writeMessage(String message){
        System.out.println(message);
    }

    public String getInput(String prompt){
        writeMessage(prompt);
        String input = console.nextLine();
        return input;
    }

    public int getIntegerInput(String prompt){
        String response = getInput(prompt);
        int input;
        while(true){
            try{
                input = Integer.parseInt(response);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a proper whole number integer.");
            } catch (Exception e) {
                System.out.println("Please enter a valid response.");
            }
        }
        return input;
    }

    public double getDoubleInput(String prompt){
        String response = getInput(prompt);
        double input;
        while(true){
            try{
                input = Double.parseDouble(response);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a proper whole number integer.");
            } catch (Exception e) {
                System.out.println("Please enter a valid response.");
            }
        }
        return input;
    }

}

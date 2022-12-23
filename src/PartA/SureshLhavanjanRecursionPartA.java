package PartA;

import java.util.Scanner; //Imports scanner class
import java.util.StringTokenizer; //Imports StringTokenizer class

public class SureshLhavanjanRecursionPartA {

    /*
    Assignment: Recursion Assignment Part A
    Teacher/Course: Mr. Payne/ICS4U1-01
    Created By: Lhavanjan Suresh
    Date: December 22, 2022

    Models decaying bounce of a ball in TWO(2) recursive ways, via TWO(2) 
    different recursive methods. Bounce1 models the decaying bouncing of a 
    meatball that decays by a decimal proportion after each bounce and Bounce2 is
    similar as Bounce1, however, a final threshold is provided.
    */
    
    public static void main(String[] args) {

        //Variables
        int curBounce = 0, numBounce;
        double curHeight, decayFactor, threshold;
        String input;
        boolean loop = true;
       
        Scanner Keyboard = new Scanner(System.in); // Starts scanner
       
        // while true
        while (loop) {
            // Display a menu of options for the user to choose from
            System.out.println("\n1. Simulate n times.\n2. Simulate to threshold.\n3. Exit.\n"); //Menu of options for user to pick between each method.
            System.out.print("Your option: ");
            input = Keyboard.nextLine(); // Gets players input
            System.out.println("");

            // If player input is 1
            if (input.equals("1")) {

                do {
                    loop = false; // Set loop to false
                    System.out.println("Please enter number of bounces (integer), starting height in metres (integer), and a decay factor (a decimal) all seperated by ','.");
                    input = Keyboard.nextLine(); // Grabs players input
                    System.out.println("");
                    //Tokenizes input with the delimiter of a comma (",")
                    StringTokenizer st = new StringTokenizer(input, ",");

                    //If there is only 3 tokens
                    if (st.countTokens() == 3) {

                        //Parse each string token into integers and double
                        numBounce = Integer.parseInt(st.nextToken());
                        curHeight = Double.parseDouble(st.nextToken());
                        decayFactor = Double.parseDouble(st.nextToken());
                        System.out.printf("Current Height:\t\tAfter Bounce: \n"); //Formatting to run only once before the method prints out its data.
                        //Calls bounce1 method passing all tokens as parameters plus curBounce(equals 0)
                        bounce1(curBounce, numBounce, curHeight, decayFactor);
                    } else {
                        loop = true; // Set loop to true
                    }
                } while (loop); // Do-while loop is equal to true
                loop = true; // Set loop to true

            // Else if player input is 2
            } else if (input.equals("2")) {

                do {
                    loop = false; // set loop to false
                    System.out.println("Please enter starting height in metres (integer), a decay factor (a decimal) and a final height threshold (a decimal) all seperated by ','.");
                    input = Keyboard.nextLine(); // Grab players input
                    System.out.println("");
                    //Tokenizes input with the delimiter of a comma (",")
                    StringTokenizer st = new StringTokenizer(input, ",");

                    //If there is only 3 tokens
                    if (st.countTokens() == 3) {

                        //Parse each string token into integers and double
                        curHeight = Integer.parseInt(st.nextToken());
                        decayFactor = Double.parseDouble(st.nextToken());
                        threshold = Double.parseDouble(st.nextToken());

                        System.out.printf("Current Height:\t\tAfter Bounce: \n");
                        //Calls bounce2 method passing all tokens as parameters plus curBounce(equals 0)
                        bounce2(curBounce, curHeight, decayFactor, threshold);
                    } else {
                        loop = true; // Set loop to true
                    }
                } while (loop); // Do-while loop is equal to true
                loop = true; // Set loop to true

            // Else if players input is 3
            } else if (input.equals("3")) {
                loop = false; // Set loop to false
            } else {
                System.out.println("Please write either 1,2 or 3.");
            }
        }
    }

    /**
     * Recursive method to bounce the ball 'n' amount of times
     * @param curBounceIn the current bounce number
     * @param numBounceIn the total number of bounces
     * @param curHeightIn the current height of the ball
     * @param decayFactorIn the factor by which the height of the ball is reduced on each bounce
     * @return all parameters to bounce1 until base case is reached
     */
    public static double bounce1(int curBounceIn, int numBounceIn, double curHeightIn, double decayFactorIn) {
        //Base Case
        if (curBounceIn > numBounceIn) {
            System.out.println("Done.");
            return 1; //Collapses the stack.
        }
        curHeightIn = curHeightIn - decayFactorIn * curHeightIn; //Lowers height by the decay value.
        curBounceIn++;

        System.out.printf(curHeightIn + "\t\t" + curBounceIn + "\n");

        return bounce1(curBounceIn, numBounceIn, curHeightIn, decayFactorIn);//Calls on the method again.
    }

    /**
     * Recursive method to bounce the ball until it reaches a certain height threshold
     * @param curBounceIn the current bounce number
     * @param curHeightIn the current height of the ball
     * @param decayFactorIn the factor by which the height of the ball is reduced on each bounce
     * @param thresholdIn the height threshold at which the ball should stop bouncing
     * @return all parameters to bounce2 until base case is reached
     */
    public static double bounce2(int curBounceIn, double curHeightIn, double decayFactorIn, double thresholdIn) {

        if (curHeightIn < thresholdIn) {
            System.out.println("Done.");
            return 1;
        }

        curHeightIn = curHeightIn - decayFactorIn * curHeightIn; //Lowers height by the decay value.
        curBounceIn++;
        System.out.printf(Math.round(curHeightIn * 100000) / 100000.0 + "\t\t\t" + curBounceIn + "\n");

        return bounce2(curBounceIn, curHeightIn, decayFactorIn, thresholdIn); //Calls on the method again.
    }

}
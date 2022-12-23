package PartB;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

/*
This is the panel where the crabs animation is called. This is the brains of the
operation. All the movement and painting is done here.
 */
public class DrawingArea extends javax.swing.JPanel {

    //Global Variables for sliders, (a, x and y), sound, and timer
    int endDistance = 800;
    int speed = 50;
    int a, x = 0, y = 0;
    double per;
    String bkgSound = "sound.wav";

    // Object to play the background sound
    SoundPlayer bkgPlayer = new SoundPlayer();
    Timer t1; // Timer object to control the movement of the bug

    /**
     * Creates new form DrawingArea
     */
    public DrawingArea() {
        initComponents();
        // Initialize the timer object with the specified speed and action listener
        t1 = new Timer(speed, new DrawingArea.TimerListener());
    }

    /**
     * Method to save coordinates to global x and y to draw on the panel
     *
     * @param points points list of x and y coordinates for the path
     * @param curIndex current index in the points list
     */
    public void drawBug(ArrayList<Integer> points, int curIndex) {

        try {
            // Get the current x and y coordinates from the points list
            x = points.get(curIndex);
            y = points.get(curIndex + 1);

            // Modify the x and y coordinates based on the percentage value of the slider
            if (per > 0.5) {
                x = x - 50;
                y = y / 4;
            }

        } catch (Exception e) {
            // If there are no more points, stop the timer
            t1.stop();
        }
        // If the bug has reached the end distance, stop the timer
        if (x + 15 > endDistance) {
            t1.stop();
        }
    }

    /**
     * Overridden paintComponent method to draw the panel and its contents
     *
     * @param g Graphics object to draw on the panel
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Load the images of the underwater background and crab
        Image underwater = Toolkit.getDefaultToolkit().getImage("Underwater.jpg");
        Image crabRight = Toolkit.getDefaultToolkit().getImage("CrabRight-2.png");

        //Draws underwater background and crab onto panel
        g.drawImage(underwater, 0, 0, this);
        g.drawImage(crabRight, x, -y + 500, this);

        // If the y coordinate is 0 and the x coordinate is not 0 and the timer is running, play the bounce sound
        if (y == 0 && x != 0 && t1.isRunning() == true) {
            bkgPlayer.play(bkgSound);
        }
        // Draw a rectangle at the end distance to indicate the end of the path (wall)
        g.fillRect(endDistance, 0, 10, 600);
    }

    /**
     * Inner class to implement the ActionListener interface and control the
     * movement of the bug
     */
    private class TimerListener implements ActionListener {

        // List to store the x and y coordinates of the bug's path
        ArrayList<Integer> points = new ArrayList();

        /**
         * Method to handle the timer event
         *
         * @param ae ActionEvent object
         */
        @Override
        public void actionPerformed(ActionEvent ae) {

            double additionFactorIn;

            // Check if the points list is empty
            if (points.isEmpty() == true) {

                // Initialize variables
                int startDistance = 0;
                additionFactorIn = endDistance * per;
                int recursiveLimit = 0;
                // If the percentage value is 0, add 0,0 to the points list and set a to 0
                if (per == 0) {
                    points.add(0);
                    points.add(0);
                    a = 0;
                } else {
                    // Calculate the points for the path
                    points = part1and2and3and4and5and6(startDistance, endDistance, additionFactorIn, points, per, recursiveLimit);
                }
                // Print the path to the console (for debugging purposes)
                if (a == 2) {
                    for (int a = 1; points.size() > a; a = a + 2) {
                        for (int b = 0; points.get(a) > b; b++) {
                            System.out.print("*");
                        }
                        System.out.print("\n");
                    }
                }
            }
            // Update x and y coordinates of the crab using the current position from the points list
            drawBug(points, a);
            // Increment the current position in the points list
            a = a + 2;
            // If the timer is running, repaint the panel
            if (t1.isRunning() == true) {
                repaint();
            } else {
                // If the timer is not running, clear the points list
                points.clear();
            }
        }
    }

    /**
     * Method to start the animation
     */
    public void start() {
        // Initialize the current position in the points list to 2 since (0,0) is already painted
        a = 2;
        // Divide the percentage value by 100
        per = per / 100;
        // Start the timer
        t1.start();
        // Set the delay of the timer to the specified speed
        t1.setDelay(speed);
        // Play the bounce sound
        bkgPlayer.play(bkgSound);
    }

    /**
     * Recursive method to calculate the points for the crab's path
     *
     * @param startDistanceIn starting distance of the crab's movement
     * @param endDistanceIn end distance of the bug's movement
     * @param additionFactor amount to add to the start distance on each
     * iteration
     * @param pointsIn list of x and y coordinates for the bug's path
     * @param per percentage value of the slider
     * @param recursiveLimit limit for the number of recursive calls
     * @return list of x and y coordinates for the bug's path
     */
    private static ArrayList<Integer> part1and2and3and4and5and6(double startDistanceIn, int endDistanceIn, double additionFactor, ArrayList pointsIn, double per, int recursiveLimit) {

        // Initialize variables for x and y coordinates and the increment value
        double x = startDistanceIn;
        double y = 0;
        double increment = additionFactor / 20;

        //recursiveLimit used for per value < 0.5
        // Check if the start distance has reached the end distance or if the number of recursive calls has exceeded the limit
        if (startDistanceIn >= endDistanceIn - 35 || recursiveLimit > 3) {
            // Return the points list if the above condition is true
            return pointsIn;
        } else {
            // If x and y are both 0, add 0,0 to the points list
            if (x == 0 && y == 0) {
                pointsIn.add(0);
                pointsIn.add(0);
            }
            // Loop through the coordinates until the x coordinate has reached the end value for the current iteration
            while (x <= (startDistanceIn + additionFactor)) {

                // Increment the x and update the y coordinates using the quadratic equation
                x = x + increment;
                y = -0.008 * (x - startDistanceIn) * (x - (startDistanceIn + additionFactor)); //"a" value low to keep in frame
                // Check if the y coordinate is greater than or equal to 0
                if (y >= 0) {
                    int yInt = (int) Math.round(y);
                    int xInt = (int) Math.round(x);
                    pointsIn.add(xInt);
                    pointsIn.add(yInt);
//                    if (xInt < startDistanceIn + additionFactor) {
////                        for (int a = 0; a < yInt; a++) {
////                            System.out.print("*");
////                        }
//                    }
//                    System.out.print("\n");
                }
            }
            // Update the start distance and addition factor for the next iteration
            startDistanceIn = startDistanceIn + additionFactor;
            additionFactor = additionFactor * per;
            // Increment the recursive limit
            recursiveLimit++;

            // Recursively call the method with the updated start distance, addition factor, and recursive limit
            return part1and2and3and4and5and6(startDistanceIn, endDistanceIn, additionFactor, pointsIn, per, recursiveLimit);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

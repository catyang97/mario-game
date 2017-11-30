/**
 * Super Mario Bros
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        
        // Top-level frame in which game components live
        final JFrame frame = new JFrame("SUPER MARIO BROS");
        frame.setLocation(500, 500);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Level");
        status_panel.add(status);

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // When the Reset Button is clicked, the court is reset
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        
        // When the Instructions Button is clicked, the instructions are displayed in a dialog
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(control_panel, 
                        "<html>Goal: Collect as many coins as possible and avoid hitting Goomba."
                        + "<br>Use the arrow keys to control Mario. <br>"
                        + "Hit Item Blocks to collect items. Super Mushrooms make Mario grow <br>"
                        + "while Mini Mushrooms shrink him. While on the ground, click the <br>"
                        + "down key to smash the ground.</html> ", 
                        "Instructions", JOptionPane.PLAIN_MESSAGE);
                
                // reset the game
                court.reset();
            }
        });
        
        // Add the buttons to the control panel
        control_panel.add(reset);
        control_panel.add(instructions);
        
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
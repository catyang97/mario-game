import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.*;


/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private Mario mario; 
    private Goomba goomba;
        
    private boolean playing = false; // whether the game is running
    private JLabel points; // keeps track of game state- number of points

    // Game constants
    private static final int COURT_WIDTH = 500;
    private static final int COURT_HEIGHT = 500;
    private static final int MARIO_VELOCITY_X = 7;
    private static final int MARIO_VELOCITY_Y= 10;
    private static final int BLOCK_SIZE = 50;
    private static int MARIO_SIZE = 45;
    private static final int GOOMBA_SIZE = 40;

    // Update interval for timer, in milliseconds
    private static final int INTERVAL = 35;
  
    // Calculate how many blocks fit in the court
    private static final int NUM_BLOCKS_W = COURT_WIDTH / BLOCK_SIZE;
    private static final int NUM_BLOCKS_H = COURT_HEIGHT / BLOCK_SIZE;
    
    // Collections for blocks and items
    private Block[][] blocks;
    private LinkedList<Item> items1 = new LinkedList<Item>();
    private LinkedList<Item> items2 = new LinkedList<Item>();
    private LinkedList<Item> items3 = new LinkedList<Item>();
    private LinkedList<Item> collect = new LinkedList<Item>();

    // Start positions for the characters
    private static final int GOOMBA_X = COURT_WIDTH - 10; 
    private static final int GOOMBA_Y = COURT_HEIGHT - (2 * BLOCK_SIZE + GOOMBA_SIZE);
    
    private static final int MARIO_X = 200; 
    private static final int MARIO_Y = COURT_HEIGHT - (2 * BLOCK_SIZE + MARIO_SIZE);
    
    private boolean smash = false; // whether or not to break Ground blocks
    private int score = 0;
    
    // File Reading/Writing for High Scores
    File highScores = new File("HighScores.txt");
    BufferedWriter bw = null;
    BufferedReader br = null;
    private TreeMap<Integer, TreeSet<String>> scoreMap = new TreeMap<Integer, TreeSet<String>>();

    
    public GameCourt(JLabel points) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();
        setFocusable(true);

        // This key listener allows Mario to move as long as an arrow key is pressed, 
        // by changing the square's velocity accordingly.
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    mario.v_x = -MARIO_VELOCITY_X;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    mario.v_x = MARIO_VELOCITY_X;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    mario.v_y = MARIO_VELOCITY_Y;
                    smash = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                    mario.v_y = -MARIO_VELOCITY_Y;
            }

            public void keyReleased(KeyEvent e) {
                mario.v_x = 0;
                mario.v_y = 0;
            }
        });

        this.points = points;
    }

    
    /**
     * Reset the game to its initial state.
     */
    public void reset() {
        playing = true;

        // Initialize characters and 2D array for game board
        mario = new Mario(MARIO_X, MARIO_Y, COURT_WIDTH, COURT_HEIGHT);
        goomba= new Goomba(GOOMBA_SIZE, GOOMBA_X, GOOMBA_Y, COURT_WIDTH, COURT_HEIGHT);          
        blocks = new Block[NUM_BLOCKS_H][NUM_BLOCKS_W];
        
        collect.clear();
        score = 0;

        // Populate 2D array / game board
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length - 2; j++) {
                if ((i == 2 && j == 4) || (i == 4 && j == 4) || (i == 6 && j == 4)) {
                    blocks[i][j] = new BreakBlock(i * BLOCK_SIZE , j * BLOCK_SIZE, BLOCK_SIZE);
                } else if ((i == 1 && j == 4) || (i == 5 && j == 4)) {
                    blocks[i][j] = new SolidBlock(i * BLOCK_SIZE , j * BLOCK_SIZE, BLOCK_SIZE);
                } else if ((i == 2 && j == 3) || (i == 5 && j == 1) || (i == 8 && j == 3)) {
                    blocks[i][j] = new ItemBlock(i * BLOCK_SIZE , j * BLOCK_SIZE, BLOCK_SIZE);
                } else {   
                    blocks[i][j] = new Sky(i * BLOCK_SIZE , j * BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
        
        for (int i = 0; i < blocks.length; i++) {
            for (int j = blocks[0].length - 2; j < blocks[0].length; j++) {
                blocks[i][j] = new Ground(i * BLOCK_SIZE , j * BLOCK_SIZE, BLOCK_SIZE);
            }
        }
        
        // Add random Items to each of the ItemBlocks at different frequencies
        for (int i = 0; i < 10; i++) {
            double random = Math.random();
            if (random <= 0.5) {
                items1.add(i, new Coin(115, 125, 25));
            } else if (random <= 0.8) {
                items1.add(i, new MiniMushroom(115, 127, 20));
            } else {
                items1.add(i, new SuperMushroom(110, 121, 35));
            }
        }
        
        for (int i = 0; i < 10; i++) {
            double random = Math.random();
            if (random <= 0.5) {
                items2.add(i, new Coin(265, 25, 25));
            } else if (random <= 0.8) {
                items2.add(i, new MiniMushroom(265, 27, 20));
            } else {
                items2.add(i, new SuperMushroom(260, 21, 35));
            }
        }
        
        for (int i = 0; i < 10; i++) {
            double random = Math.random();
            if (random <= 0.5) {
                items3.add(i, new Coin(415, 125, 25));
            } else if (random <= 0.8) {
                items3.add(i, new MiniMushroom(415, 127, 20));
            } else {
                items3.add(i, new SuperMushroom(410, 121, 35));
            }
        }
     
        // Read in the High Scores file and put information in map from scores to names
        try {
            FileReader read = new FileReader(highScores);
            br = new BufferedReader(read);
            
            String current;
            
            while ((current = br.readLine()) != null) {
                int space = 0;
                
                char[] arr = new char[current.length()];
                arr = current.toCharArray();
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i] == ' ') {
                        space = i;
                    } 
                }
                
                char[] words = new char[space];
                for (int i = 0; i < words.length; i++) {
                    words[i] = arr[i];
                }
                String name = new String(words);
                
                char[] nums = new char[arr.length - words.length - 1];
                for (int i = 0; i < nums.length; i++) {
                    nums[i] = arr[i + words.length + 1];
                }
                
                String num = new String(nums);
                int n = Integer.parseInt(num);
                
                TreeSet<String> set = new TreeSet<String>();
                if (scoreMap.containsKey(n)) {
                    set = scoreMap.get(n);
                }
                
                set.add(name);
                scoreMap.put(n, set);
            }
        } catch (IOException e) {
            
        } finally {
            
        }
        
        points.setText("Score: " + score);

        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing) {
            // advance the characters in their current direction.
            mario.move();
            goomba.move();

            // make the Goomba bounce off walls
            goomba.bounce(goomba.hitWall());
            
            // Detects Mario's collisions with different Blocks
            for (int i = 0; i < blocks.length; i++) {
                for (int j = 0; j < blocks[0].length; j++) {
                    Block block = blocks[i][j];

                    if (block instanceof Sky) {
                        continue;                        
                    } else if (block instanceof BreakBlock && block.ifHit(mario)) {
                        blocks[i][j] = new Sky(i * BLOCK_SIZE , j * BLOCK_SIZE, BLOCK_SIZE);
                    } else if (block instanceof Ground && smash == true && block.ifHit(mario)) {
                        blocks[i][j] = new Sky(i * BLOCK_SIZE , j * BLOCK_SIZE, BLOCK_SIZE);
                        smash = false;
                    } else if (block instanceof ItemBlock && block.ifHit(mario)) {
                        int randomNum = (int)(Math.random() * 10); 
                        if (collect.size() == 0) {
                            if (i == 2) {
                                Item it = items1.get(randomNum);
                                collect.add(it);
                            } else if (i == 5) {
                                Item it = items2.get(randomNum);
                                collect.add(it);
                            } else {
                                Item it = items3.get(randomNum);
                                collect.add(it);
                            }
                        }
                        mario.bounce(mario.hitObj(block));
                    } else {
                        mario.bounce(mario.hitObj(block));
                    }
                }
            }
            
            // Detects if Mario has hit an item
            for (Item it: collect) {
                Rectangle rect = it.getShape();
                Rectangle mar = mario.getShape();
                if (rect.intersects(mar)) {
                    it.pickedUp(mario);
                    if (it instanceof Coin) {
                        score++;
                        points.setText("Score: " + score);
                    }
                    collect.remove(it);
                }
            }
            
            // check for the game end conditions
            if (mario.intersects(goomba)) {
                playing = false;
                points.setText("You lose!");
                
                // Adds the score and the player to the High Scores board by adding to map
                String name = JOptionPane.showInputDialog("Enter name:");
                
                TreeSet<String> set = new TreeSet<String>();
                if (scoreMap.containsKey(score)) {
                    set = scoreMap.get(score);
                }
                
                set.add(name);
                scoreMap.put(score, set);
                
                try {
                    FileWriter write = new FileWriter(highScores);
                    bw = new BufferedWriter(write);
                    
                    int size = scoreMap.size();
       
                    for (int i = 0; i < size; i++) {
                        int last = scoreMap.lastKey();
                        TreeSet<String> set2 = new TreeSet<String>();
                        set2 = scoreMap.get(last);
                        
                        for (String s : set2) {
                            bw.write(s);
                            bw.write(' ');
                            bw.write("" + last);
                            bw.newLine();
                        }
                        scoreMap.remove(last);
                    }
                    
                } catch (IOException e) {
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch(Exception ex) { 
                    }  
                }    
            }

            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                blocks[i][j].draw(g);
            }
        }
 
        for (Item it: collect) {
            it.draw(g);
        }
        
        mario.draw(g);
        goomba.draw(g);   

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}


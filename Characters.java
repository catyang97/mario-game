import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

public abstract class Characters {

    /** A Character in the game court. They have a position, 
     *  velocity, size and bounds. Their velocity controls how they 
     *  move; their position should always be within their bounds.
     */

    // Current position of the character
    public int pos_x; 
    public int pos_y;

    // Size of character in pixels
    public int width;
    public int height;
    
    // Velocity: number of pixels to move every time move() is called
    public double v_x;
    public double v_y;

    // Upper bounds of the area in which the object can be positioned 
    public int max_x;
    public int max_y;
        
    public double a_y;
        
    // File to be turned into the img
    public String img_file;       
    private BufferedImage img;


    /**
     * Constructor: if no acceleration is input, defaults to 0
     */
    public Characters(double v_x, double v_y, int pos_x, int pos_y, int width, int height, 
            int court_width, int court_height, String file) {
                this(v_x, v_y, pos_x,pos_y, width, height, court_width, court_height, 0, file);
            }

    public Characters(double v_x, double v_y, int pos_x, int pos_y, int width, int height, 
            int court_width, int court_height, double a_y, String file) {
        this.v_x = v_x;
        this.v_y = v_y;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.width = width;
        this.height = height;
            
        // take the width and height into account when setting the 
        // bounds for the upper left corner of the object.
        this.max_x = court_width - width;
        this.max_y = court_height - height;
        
        this.a_y = a_y;
            
        img_file = file;

        try {
            if (img == null) {
                img = ImageIO.read(new File(img_file));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
            
    }
        
    // Draws the image using Graphics g
    public void draw(Graphics g) {
        g.drawImage(img, pos_x, pos_y, width, height, null);
    }
        
    /**
     * Moves the object by its velocity.  Ensures that the character does
     * not go outside its bounds by clipping.
     */
    public void move(){
        pos_x += v_x;
        pos_y += v_y;
        v_y += a_y;
        clip();
    }

    /**
     * Prevents the object from going outside of the bounds of the area 
     * designated for the character. 
     */ 
    public void clip(){
        if (pos_x < 0) {
            pos_x = 0;
        } else if (pos_x > max_x) {
            pos_x = max_x;
        }
        if (pos_y < 0) {
            pos_y = 0;
        } else if (pos_y > max_y) {
            pos_y = max_y;
        }
    }
         
    /** 
     * Determine whether the game object will hit a wall in the next time step. 
     * If so, return the direction of the wall in relation to the character
     *  
     * @return direction of impending wall, null if all clear.
     */
    public Direction hitWall() {
        if (pos_x + v_x < 0) {
            return Direction.LEFT;
        } else if (pos_x + v_x > max_x) {
            return Direction.RIGHT;
        }
        if (pos_y + v_y < 0) {
            return Direction.UP;
        } else if (pos_y + v_y > max_y) {
            return Direction.DOWN;
        } else {
            return null;
        }
    }
    
    /**
     * Returns the bounding box of the object.
     * @return Rectangle with the positions and dimensions of the character.
     */
    public Rectangle getShape() {
        return new Rectangle(pos_x, pos_y, width, height);
    }
        
    /** Update the velocity of the character in response to hitting
     *  an obstacle in the given direction. If the direction is
     *  null, this method has no effect on the object. */        
    public abstract void bounce(Direction d);

}

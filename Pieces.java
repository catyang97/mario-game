import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public abstract class Pieces {
    
    private int size;
    private int x;
    private int y;
    
    private BufferedImage img;
    
    public Pieces(int x, int y, int size, String file) {
        this.size = size;
        this.x = x;
        this.y = y;
        
        try {
            if (img == null) {
                img = ImageIO.read(new File(file));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    
    // Draws the image using Graphics g
    public void draw(Graphics g) {
        g.drawImage(img, x, y, size, size, null);
    }
    
    /**
     * Returns the bounding box of the piece.
     * @return Rectangle with the positions and dimensions of the piece.
     */
    public Rectangle getShape() {
        return new Rectangle(x, y, size, size);
    }
}

    
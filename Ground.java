import java.awt.Rectangle;

public class Ground extends Block{
    
    private static final String img_file = "Ground.png";
    private int size;
    private int x;
    private int y;
    
    public Ground(int x, int y, int size) {
        super(x, y, size, img_file);
        
        this.size = size;
        this.x = x;
        this.y = y;

    }
    
    public boolean ifHit(Characters obj) {
        Rectangle character = obj.getShape();
        // Ground is hit from the top
        return character.intersectsLine(x, y, x + size, y);
    }

}
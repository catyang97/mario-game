import java.awt.Rectangle;

public class BreakBlock extends Block{
    
    private static String img_file = "Brick_Block.png";
    private int size;
    private int x;
    private int y;

    public BreakBlock(int x, int y, int size) {
        super(x, y, size, img_file);
        
        this.size = size;
        this.x = x;
        this.y = y;
    }

    public boolean ifHit(Characters obj) {
        Rectangle character = obj.getShape();
        // hit if the bottom of the block is hit
        return character.intersectsLine(x, y + size, x + size, y + size);
    }
}

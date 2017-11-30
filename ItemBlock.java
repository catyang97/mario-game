import java.awt.Rectangle;


public class ItemBlock extends Block{
    
    private static final String img_file = "ItemBlock.png";
    private int size;
    private int x;
    private int y;
    
    public ItemBlock(int x, int y, int size) {
        super(x, y, size, img_file);
        
        this.size = size;
        this.x = x;
        this.y = y;

    }

    public boolean ifHit(Characters obj) {
        Rectangle character = obj.getShape();
        // ItemBlock is hit from the bottom
        return character.intersectsLine(x, y + size, x + size, y + size);
    }

}
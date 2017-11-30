public class SolidBlock extends Block{
    
    private static final String img_file = "Solid_Block.png";
  
    public SolidBlock(int x, int y, int size) {
        super(x, y, size, img_file);
    }

    // Nothing occurs
    public boolean ifHit(Characters obj) {
        return false;
    }

}

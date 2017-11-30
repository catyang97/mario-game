public class Sky extends Block {

    private static final String img_file = "Sky.png";

    public Sky(int x, int y, int size) {
        super(x, y, size, img_file);
    }
    
    // Nothing occurs
    public boolean ifHit(Characters obj) {
        return false;
    }
}

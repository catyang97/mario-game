public class SuperMushroom extends Item{

    private static final String img_file = "SuperMushroom.png";

    public SuperMushroom(int x, int y, int size) {
        super(x, y, size, img_file);  
    }    
    
    // Enlarges Mario
    public void pickedUp(Mario mar) {
        mar.changeSize(mar.width + 10);
    }
}



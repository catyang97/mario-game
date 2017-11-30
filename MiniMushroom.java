public class MiniMushroom extends Item {

    private static String file = "MiniMushroom.png";

    public MiniMushroom(int x, int y, int size) {
        super(x, y, size, file);
    }    
   
    // Shrinks Mario
    public void pickedUp(Mario mar) {
        mar.changeSize(mar.width - 15);
    }

}

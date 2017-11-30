public class Coin extends Item {
    private static final String img_file = "Coin.png";
    
    public Coin(int x, int y, int size) {
        super(x, y, size, img_file);
    }

    @Override
    public void pickedUp(Mario mar) {
    }

}
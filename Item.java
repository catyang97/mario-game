public abstract class Item extends Pieces implements Comparable<Item>{
    public int size; 
    public int x;
    public int y;
    
    public Item(int x, int y, int size, String file) {
        super(x, y, size, file);
        this.size = size;
        this.x = x;
        this.y = y;
    }
    
    /**
     * Determines what happens if Mario picks up/collides with the item
     * @param obj Mario
     */
    public abstract void pickedUp(Mario obj);
    
    // Items are comparable
    public int compareTo(Item o) {
        return 0;
    }
}

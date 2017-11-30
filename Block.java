public abstract class Block extends Pieces {
        
    public int size; 
    public int x;
    public int y;
    
    public Block(int x, int y, int size, String file) {
        super(x, y, size, file);
        this.size = size;
        this.x = x;
        this.y = y;
    }
    
    /**
     * Determines what counts as a collision with the block
     * @param obj Character that collides with the block
     * @return if the Block is hit
     */
    public abstract boolean ifHit(Characters obj);
}

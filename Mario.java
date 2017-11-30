/**
 * A Character displayed with a Mario image
 */

public class Mario extends Characters {
    public static final String img_file = "MarioSprite.png";
    
    // Initial speeds
    public static final double INIT_VEL_X = 0;
    public static final double INIT_VEL_Y = 0;
    public static final double INIT_ACC_Y = 1.0;
    
    public static int size = 45;
    
    public Mario(int initX, int initY, int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, initX, initY, size, size, courtWidth,
              courtHeight, INIT_ACC_Y, img_file);
    }
    
    /**
     * Changes Mario's size
     * @param newSize: the size that Mario is changed to
     */
    public void changeSize(int newSize) {
        width = newSize;
        height = newSize;
    }
    
    /**
     * Determine whether Mario with intersect with a block in the
     * next time step.
     * @param obj : the block
     * @return whether an intersection will occur.
     */
    public boolean willIntersect(Block obj){
        double next_x = pos_x + v_x;
        double next_y = pos_y + v_y;
        double next_obj_x = obj.x;
        double next_obj_y = obj.y;
        return (next_x + width >= next_obj_x
                && next_y + height >= next_obj_y
                && next_obj_x + obj.size >= next_x 
                && next_obj_y + obj.size >= next_y);
    }
              
    /** Determine whether Mario will intersect with a block in the next
     * time step. If so, return the direction of the block in relation to Mario.
     * @return direction of Block, null if all clear.
     */        
    public Direction hitObj(Block other) {
        if (this.willIntersect(other)) {
            double dx = other.x + other.size / 2 - (pos_x + width /2);
            double dy = other.y + other.size / 2 - (pos_y + height/2);
            
            double theta = Math.acos(dx / (Math.sqrt(dx * dx + dy * dy)));
            double diagTheta = Math.atan2(height / 2, width / 2);
            
            if (theta <= diagTheta ) {
                return Direction.RIGHT;
            } else if ( theta > diagTheta && theta <= Math.PI - diagTheta ) {
                if ( dy > 0 ) {
                    return Direction.DOWN;
                } else {
                    return Direction.UP;
                }
            } else {
                return Direction.LEFT;
            }
        } else {
            return null;
        }
    }
        
    // Mario bounces when he hits the sides of the wall or Blocks but bounces
    // minimally when he lands down 
    public void bounce(Direction d) {
        if (d == null) return;
        switch (d) {
        case UP:    v_y = Math.abs(v_y); break; 
        case DOWN:  v_y = 0.5; break;
        case LEFT:  v_x = Math.abs(v_x); break;
        case RIGHT: v_x = -Math.abs(v_x); break;
        }
    }
    
    /**
     * Determine whether Mario currently intersecting another Character
     * @param obj : other Character
     * @return whether Mario intersects the other Character
     */
    public boolean intersects(Characters obj){
        return (getShape().intersects(obj.getShape()));
    }
    
}



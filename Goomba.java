/**
 * A Character displayed with a Goomba image
 */

public class Goomba extends Characters {
    public static final String img_file = "Goomba.png";

    public static final double INIT_VEL_X = 1;
    public static final double INIT_VEL_Y = 0;
    
    public Goomba(int size, int initX, int initY, int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, initX, initY, size, size, courtWidth,
                courtHeight, img_file);
    }
    
    // Goomba changes directions when it hits the walls horizontally
    public void bounce(Direction d) {
        if (d == null) return;
        switch (d) {
        case UP:    v_y = Math.abs(v_y); break;  
        case DOWN:  v_y = -Math.abs(v_y); break;
        case LEFT:  v_x = Math.abs(v_x); break;
        case RIGHT: v_x = -Math.abs(v_x); break;
        }
    }
}



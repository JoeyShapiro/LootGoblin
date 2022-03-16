import java.awt.image.BufferedImage;

public class Enemy extends Entity {

    Enemy(int nx, int ny, int sp, BufferedImage s) {
        super(nx, ny, sp, s);
    }

    Enemy(int nx, int ny, int sp, String s) {
        super(nx, ny, sp, s);
    }

    public void act() {
        velocityX = speed;

        x += velocityX;
        y += velocityY;
    }
}

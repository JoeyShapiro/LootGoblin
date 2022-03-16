import java.awt.image.BufferedImage;

import javax.swing.JLabel;

public class Entity {
    int x;
    int y;
    int speed;
    int velocityX;
    int velocityY;
    BufferedImage sprite;

    Entity(int nx, int ny, int sp, BufferedImage s) {
        x = nx;
        y = ny;
        velocityX = 0;
        velocityY = 0;
        speed = sp;
        sprite = s;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setPos(int nx, int ny) { x = nx; y = ny; }

    public BufferedImage getSprite() { return sprite; }

    public boolean isNextTo(JLabel that) {
        int THRESHOLD = 32;

        if (this.x + THRESHOLD >= that.getX() && this.x - THRESHOLD <= that.getX()) // i guessed it (kinda) and got this right
            if (this.y + THRESHOLD >= that.getY() && this.y - THRESHOLD <= that.getY())
                return true;

        return false;
    }
}

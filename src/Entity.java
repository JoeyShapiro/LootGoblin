import java.awt.image.BufferedImage;

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
}

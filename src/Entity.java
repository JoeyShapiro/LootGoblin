import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Entity {
    int x;
    int y;
    int speed;
    int velocityX;
    int velocityY;
    JLabel sprite;

    int health;
    int mana;
    int gold;

    Entity() {
        x = -1;
        y = -1;
        velocityX = 0;
        velocityY = 0;
        speed = 0;
        sprite = new JLabel("");
    }

    Entity(int nx, int ny, int sp, BufferedImage s) {
        x = nx;
        y = ny;
        velocityX = 0;
        velocityY = 0;
        speed = sp;
        sprite = new JLabel(new ImageIcon(s));
        health = 100;
        gold = 0;
        mana = 100;
    }

    Entity(int nx, int ny, int sp, String s) {
        x = nx;
        y = ny;
        velocityX = 0;
        velocityY = 0;
        speed = sp;
        if (new File(s).exists())
            sprite = new JLabel(new ImageIcon(s)); // should i accept string or image
        else
            sprite = new JLabel(s);
        health = 100;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setPos(int nx, int ny) { x = nx; y = ny; }

    public JLabel getSprite() { return sprite; }
    public void setSpritePos(int x, int y) { sprite.setBounds(x, y, 32, 32); }
    public void reDraw() { setSpritePos(x, y); }

    public boolean isNextTo(JLabel that) {
        int THRESHOLD = 32;

        if (this.x + THRESHOLD >= that.getX() && this.x - THRESHOLD <= that.getX()) // i guessed it (kinda) and got this right
            if (this.y + THRESHOLD >= that.getY() && this.y - THRESHOLD <= that.getY())
                return true;

        return false;
    }

    public boolean isInBounds() { // maybe not here, but keep for later
        if (x - 1 >= 0 && x + 1 <= 1280)
            if (y -1 >= 0 && y + 1 <= 720)
                return true;

        return false;
    }

    public boolean isTouching(Item that) {
        
        
        return false;
    }

    // is Colliding, but i like the name better :P
    public boolean isTouchingOrWorse(JLabel that) {
        if (this.x+32 >= that.getX() && this.x-32 <= that.getX())
            if (this.y+32 >= that.getY() && this.y-32 <= that.getY())
                return true;

        return false;
    }

    public boolean isAt(int point[]) {
        if (x+32 >= point[0] && x-32 <= point[0])
            if (y+32 >= point[1] && y-32 <= point[1])
                return true;

        return false;
    }
}

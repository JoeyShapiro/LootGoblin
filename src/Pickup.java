import java.io.File;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Pickup { // im not sure if i should do () here, or in main, but this is more fun :P
    JLabel sprite;
    int x, y;
    Consumer<Entity> acton; // no, ONLY player can ONLY pickup, tiles will need something else
    int ID;
    String name;

    public Pickup() {
        x = -1;
        y = -1;
        sprite = new JLabel("");
    }

    public Pickup(int nx, int ny, String s, Consumer<Entity> a) {
        x = ny;
        y = ny;
        sprite = new JLabel(s);
        acton = a;
    }

    public Pickup(int id, String n, String s, Consumer<Entity> a) {
        x = -1;
        y = -1;
        name = n;
        if (new File(s).exists())
            sprite = new JLabel(new ImageIcon(s)); // should i accept string or image
        else
            sprite = new JLabel(s);
        acton = a;
        ID = id; // forgot this one
    }

    public Pickup(int id, String n, JLabel s, Consumer<Entity> a) {
        x = -1;
        y = -1;
        name = n;
        sprite = s;
        acton = a;
        ID = id; // forgot this one
    }

    public void actOnEntity(Entity e) {
        acton.accept(e);
    }

    public void setPos(int nx, int ny) {
        sprite.setBounds(nx, ny, 32, 32);
    }

    public void reDraw() { setPos(x, y); }

    public Pickup cloneDeep() {
        return new Pickup(ID, name, sprite, acton); // smarter :)
    }
}

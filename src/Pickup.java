import java.util.function.Consumer;
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
        sprite = new JLabel();
    }

    public Pickup(int nx, int ny, String s, Consumer<Entity> a) {
        x = ny;
        y = ny;
        sprite = new JLabel(s);
        acton = a;
    }

    public Pickup(int ID, String n, String s, Consumer<Entity> a) {
        x = -1;
        y = -1;
        name = n;
        sprite = new JLabel(s);
        acton = a;
    }

    public void actOnEntity(Entity e) {
        acton.accept(e);
    }

    public void setPos() {

    }

    public void reDraw() {

    }

    public Pickup cloneDeep() {
        return new Pickup(ID, name, sprite.getText(), acton); // smarter :)
    }
}

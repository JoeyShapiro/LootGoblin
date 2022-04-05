import java.util.function.Consumer;
import javax.swing.JLabel;

public class Tile {
    boolean isCollidable = false;
    JLabel sprite;
    int x, y;
    Consumer<Entity> acton;
    
    public Tile() {
        x = -1;
        y = -1;
        sprite = new JLabel(""); // is this right or should i load by numbers
    }

    public Tile(int nx, int ny, String s, Consumer<Entity> a) {
        x = nx;
        y = ny;
        sprite = new JLabel(s);
        acton = a;
    }

    public void setPos(int nx, int ny) {
        sprite.setBounds(nx, ny, 32, 32);
    }

    public void reDraw() {
        setPos(x, y);
    }

    public void actOnEntity(Entity e) {
        acton.accept(e);
    }

    public Tile cloneDeep() {
        return new Tile(x, y, sprite.getText(), acton); // smarter :)
    }
}

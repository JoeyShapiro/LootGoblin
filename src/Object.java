import javax.swing.JLabel;

// class for items in the world
public class Object { // maybe change this whole sceme around, i really need to find someone
    JLabel sprite;
    Item item;
    int x, y;

    public Object() {
        item = ITEMS.getItem(0);
        sprite = new JLabel("");
        x = -1;
        y = -1;
    }

    public Object(Item i, int nx, int ny) {
        item = i;
        sprite = new JLabel(""+item.ID);
        x = nx;
        y = ny;
    }

    public Object(String s, Item i, int nx, int ny) {
        item = i;
        sprite = new JLabel(s);
        x = nx;
        y = ny;
    }

    public void spawn() { // maybe

    }

    public void setPos(int nx, int ny) {
        // x = nx; y = ny;
        sprite.setBounds(nx, ny, 32, 32);
    }

    public void reDraw() { setPos(x, y); }
}

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Sprite {
    private JLabel label;

    public Sprite(String file) {
        label = new JLabel(new ImageIcon(file));
    }

    public Sprite(String text, boolean dummy) {
        label = new JLabel(text);
    }

    public void setPos(int x, int y) {
        label.setBounds(x, y, 32, 32);
    }
}

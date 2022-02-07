import java.awt.image.BufferedImage;

public class Item {
    int ID = -1; // sure maybe like this, this shows its bugged
    String name = "???";
    BufferedImage sprite;
    String flavorText = "testing testing 123";
    //String itemType;
    int price;
    int width;
    int height; // do i make list of items in here, keep this, or create items elsewhere, no to bulky

    public Item() { // maybe have one to set zero, this seems best practice, this is called normally
        ID = 0; // 0 is gimmick item, to be null
        name = "";
        flavorText = "";
        price = 0;
        width = 1; // what would happen if zero
        height = 1;
    }

    public BufferedImage getSprite() { return sprite; }
}

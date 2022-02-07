import java.awt.image.BufferedImage;

public class Item {
    int ID;
    String name;
    BufferedImage sprite;
    String flavorText;
    //String itemType;
    int price;
    int width;
    int height;

    public Item() {
        ID = -1;
        name = "???";
        flavorText = "testing testing 123";
        price = 0;
        width = 1; // what would happen if zero
        height = 1;
    }
}

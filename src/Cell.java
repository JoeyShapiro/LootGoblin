public class Cell {
    int x; // might not need these
    int y;

    Item[] items = new Item[16];
    Enemy[] enemies = new Enemy[16];
    String info;

    public Cell() {
        info = "?";
    }

    public void addInfo(String learned) {

    }
}

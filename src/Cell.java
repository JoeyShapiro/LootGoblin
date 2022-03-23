import java.util.Random;

public class Cell {
    int x; // might not need these
    int y;

    int MAX_STUFF = 16;
    int chanceItem;
    int chanceEntity;
    Item[] items = new Item[MAX_STUFF];
    Object[] objects = new Object[MAX_STUFF];
    Enemy[] enemies = new Enemy[MAX_STUFF];
    String info;
    boolean discovered = false;// cheaper find, think false is redundant

    public Cell() {
        info = "";
        chanceItem = 10;
        chanceEntity = 10;
    }

    public Cell(int ci, int ce) {
        info = "";
        chanceItem = ci;
        chanceEntity = ce;
        spawnStuff();
    }

    public void infoAdd(String learned) {
        if (info.contains(learned)) // if the character is already there, dont readd it.
            return;

        info += learned;
    }

    public void infoRm(String gone) {
        info.replace(gone, "");
    }

    public void discover() {
        discovered = true;
    }

    public String infoGet() {
        if (!discovered) // if unknown
            return "?";
        return info;
    }

    // INIT
    public void spawnStuff() { // should this go here
        Random rng = new Random();

        for (int i=0; i<MAX_STUFF; i++) {
            if (rng.nextInt(99) < chanceItem) { // 10%
                objects[i].item = ITEMS.getItemRandom();
                infoAdd("i");
            }
            if (rng.nextInt(99) < chanceEntity) {
                enemies[i] = new Enemy(0, 0, 5, "e");
                infoAdd("e");
            }
        }
    }

    // CONSTANT
    public void reDraw(Player player) {
        for (int i=0; i<MAX_STUFF; i++) {
            if (objects[i].item.ID != 0)
                objects[i].setPos(i*32, i*32); // might not be right, do .reDraw, then place, someowhere else change pos
            enemies[i].setSpritePos(enemies[i].x, enemies[i].y); // maybe make ()
        }
        //picLabel.setLocation(player.x, player.y);
        player.setSpritePos(player.x, player.y); // setLocation uses thing i dont know name of (uses '-' in graph)
        //player.reDraw(); // -> setSpritePos(x, y);
    }

    public void tick(Player player) {
        player.x += player.velocityX; // change to player.tick
        player.y += player.velocityY;
        // for (Enemy enemy : enemies)
        //     enemy.act(this); // needs this for collisions and stuff
    }

    // public void tryPickup(Player player) { // do i really need this much func->func ... in std
    //     for (int i=0; i<16; i++) { // i need to do sprite, and find way to link items
    //         if (player.isNextTo(objects[i].sprite) && objects[i].item.ID != 0) {
    //             boolean worked = inventory.tryAutoItem(objects[i].item); // can this be in if statement
    //             if (worked) {
    //                 System.out.println("Collected item: " + objects[i].item.name);
    //                 objects[i].item = new Item(); // maybe make object.remove();
    //                 objects[i].sprite.setText(""); // might be best way
    //             } else {
    //                 System.out.println("Inventory is full");
    //             }
    //             refreshInv(); // just need gui
    //             return; // so you dont pick up multiple
    //         }
    //     }
    // }
}

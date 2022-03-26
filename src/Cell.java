import java.util.Random;

public class Cell {
    int x; // might not need these
    int y;

    int MAX_STUFF = 16;
    int chanceItem;
    int chanceEntity;
    Object[] objects = new Object[MAX_STUFF];
    Enemy[] enemies = new Enemy[MAX_STUFF];
    String info;
    boolean discovered = false;// cheaper find, think false is redundant
    Player player;

    public Cell() {
        info = "";
        chanceItem = 10;
        chanceEntity = 10;
    }

    public Cell(int ci, int ce) {
        info = "";
        chanceItem = ci;
        chanceEntity = ce;

        // set objects to new, because i guess making array doesnt work
        for (int i = 0; i < objects.length; i++) {
            objects[i] = new Object();
            enemies[i] = new Enemy();
        }
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
                objects[i] = new Object(ITEMS.getItemRandom(), rng.nextInt(1280), rng.nextInt(720));
                infoAdd("i");
            }
            if (rng.nextInt(99) < chanceEntity) {
                enemies[i] = new Enemy(rng.nextInt(1280), rng.nextInt(720), 5, "e");
                infoAdd("e");
            }
        }
    }

    // CONSTANT
    public void tick(Player p) {
        player = p;
        player.x += player.velocityX; // change to player.tick
        player.y += player.velocityY;
        for (Enemy enemy : enemies)
            enemy.act(this); // needs this for collisions and stuff
    }

    // i dont think this should be here, uses "false" data
    public void tryPickup(Player p, Inventory inventory) { // do i really need this much func->func ... in std
        player = p;
        
    }

    public boolean isEntity(int point[]) {
        if (player.isAt(point))
            return true;
        // for (int i = 0; i < MAX_STUFF; i++) {
        //     if (enemies[i].isAt(point)) // lol, checking for self
        //         return true;
        //     // objects are etherial
            
        // }

        return false;
    }
}

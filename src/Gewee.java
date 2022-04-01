import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gewee extends JLayeredPane implements ActionListener{ // maybe make static, only need one?? how work?
    static JPanel menu = new JPanel(new BorderLayout());
    static JPanel game = new JPanel();
    static JLayeredPane menuInventory = new JLayeredPane(/*new BorderLayout()*/); // i doht care, its a game
    static boolean isIn = false;
    int WHAT = 32; // the y starts this much early, but doesnt say, unless you click, im gussing y cnts bar

    JLabel menuItemHeld = new JLabel();
    JLabel[][] menuInvItems = new JLabel[16][16];

    Inventory inventory = new Inventory(16, 16);
    Player player;

    // Item[] cellItems = new Item[16]; Hurray no longer need thiese things
    // JLabel[] labelItems = new JLabel[16];
    // Enemy enemy;

    boolean isPaused = false; // should this be here or somewhere else
    JLabel pause;

    Console console = new Console();
    JPanel menuConsole = new JPanel();
    JTextField menuConsoleCommand;

    // stats ui
    JPanel menuStats = new JPanel();
    JLabel statsHealth;

    int mapX = 0, mapY = 0, mapMAX = 4;
    JPanel menuMap = new JPanel();
    JLabel[][] mapCells = new JLabel[mapMAX][mapMAX];
    Cell[][] cells = new Cell[mapMAX][mapMAX];
    // current cell stuff
    int MAX_STUFF = 16;
    Object[] objects = new Object[MAX_STUFF]; // i do need to load it, so i can load the sprites into panel, otherwise annoyting..?
    Enemy[] enemies = new Enemy[MAX_STUFF]; // i could gimmick it, but that looks bad and is not as fun. (all cells have same amt so keep 16 in panel and change location)
    Pickup[] pickups = new Pickup[MAX_STUFF];

    public Gewee(/* int width, int height */) throws IOException {
        this.setLayout(null);
        game.setLayout(new BorderLayout());
        game.setBounds(0, 0, 1280, 720);
        menu.setBounds(0, 0, 1280, 720);
        menu.setLayout(null); // now it needs this, bugs out with LayeredPanel
        menu.setOpaque(false);

        // pause
        pause = new JLabel("PAUSED");
        pause.setBounds(640, 100, 256, 100);
        pause.setForeground(Color.RED);
        pause.setVisible(false);
        pause.setFont(new Font("Serif", Font.PLAIN, 32));

        // console menu
        menuConsole.setLayout(null);
        menuConsole.setBounds(0, 720-(720/3), 1280-(1280/2), 720-(720/3));
        menuConsole.setVisible(console.isOpen);
        menuConsole.setBackground(Color.GRAY);

        // stats menu (maybe make only popup, or should i always keep up, at least some of it)
        menuStats.setLayout(null);
        menuStats.setBounds(1280-(1280/5), 256, 256, 256);
        menuStats.setBackground(new Color(0, 0, 0, 100));
        menuStats.setVisible(true);
        menuStats.setFont(new Font("Serif", Font.PLAIN, 32));
        statsHealth = new JLabel("Health: ");
        statsHealth.setBounds(0, 0, 256, 32);
        menuStats.add(statsHealth);

        // minimap
        menuMap.setLayout(null);
        menuMap.setBounds(1280-(1280/5), 0, 256, 256);
        menuMap.setBackground(new Color(0, 0, 0, 100));
        int menuCellSize = 256/mapMAX;
        for (int i=0; i<mapMAX; i++)
            for(int j=0; j<mapMAX; j++) {
                cells[i][j] = new Cell(); // have to do it first, and this is efficient, rather than another loop
                mapCells[i][j] = new JLabel(cells[i][j].infoGet(), SwingConstants.CENTER); // also sets player pos
                mapCells[i][j].setBounds(menuCellSize*i, menuCellSize*j, menuCellSize, menuCellSize);
                menuMap.add(mapCells[i][j]);
            }
        //genMap(); // come back to, (linked-list, how sort)
        
        // inventory
        menuInventory.setLocation(300, 100); // this must be before panel is added
        menuInventory.setLayout(null); // have to set it here

        JLabel invBackplate = new JLabel(new ImageIcon(ImageIO.read(new File("res/invBackplate.png"))));
        invBackplate.setBounds(0,0,512,512); // i hate swing
        for (int i=0; i<16; i++)
            for(int j=0; j<16; j++) { // maybe move eslewhere
                menuInvItems[i][j] = new JLabel(String.valueOf(inventory.getItem(i, j).ID));
                if (inventory.getItem(i, j).ID == 0)
                    menuInvItems[i][j].setText(""); // maybe not efficient
                menuInvItems[i][j].setBounds(32*i, 32*j, 32, 32);
                menuInventory.add(menuInvItems[i][j], 2, 0);
            }
        menuItemHeld.setVisible(false);
        menuItemHeld.setSize(32, 32);
        menuInventory.add(invBackplate, 1, 0/*, BorderLayout.WEST*/);
        menuInventory.add(menuItemHeld, 3, 0);
        menuInventory.setSize(700, 512);
        menuInventory.setBackground(Color.LIGHT_GRAY);
        menuInventory.setOpaque(true);
        menuInventory.setVisible(false);

        // for (int i=0; i<16; i++)
        //     for (int j=0; j<16; j++)
        //         inventory[i][j] = (i+j*16)+1; // creates a unique number for each, just memeing
        //         // +1 because 0 is gimmick item, to be NULL
        
        menu.add(menuInventory);
        game.setBackground(Color.GREEN); // if opaque, no color
        
        player = new Player(0, 0, 5, ImageIO.read(new File("res/player.png")));
        game.add(player.getSprite());

        // enemy = new Enemy(500, 500, 2, "e");
        // game.add(enemy.getSprite());

        add(game, 1, 0);
        add(menu, 2, 0);
        add(pause, 3, 0); // make this esc menu at some point
        add(menuMap, 4, 0);
        add(menuStats, 5, 0);
        add(menuConsole, 6, 0);

        genMap(); // first, wait
        loadCell(cells[mapX][mapY], false); // so it has something to read
        cells[0][0].discover(); // put here, discover at the beginning, you start here
        refreshMap();
    }

    public void reDraw() { // change name, move to Cell
        //picLabel.setLocation(player.x, player.y);
        player.setSpritePos(player.x, player.y); // setLocation uses thing i dont know name of (uses '-' in graph)
        for (int i = 0; i < MAX_STUFF; i++) {
            if (objects[i].x != -1 && objects[i].item.ID != 0) // change to isAlive or something clever
                objects[i].reDraw();
            if (enemies[i].x != -1)
                enemies[i].reDraw();
            if (pickups[i].x != -1 && pickups[i].ID != 0) // i think 1 of these is redundant
                pickups[i].reDraw();
        }
        statsHealth.setText("Health: " + player.health);
    }

    public void tick() {
        if (isPaused) {

            return;
        } // if the game is paused, stop ticks
        cells[mapX][mapY].tick(player); // this works, so keep for now i guess.

        checkCell();
    }

    public void toggleInventory() {
        inventory.isOpen = !inventory.isOpen; // smart, thank you
        menuInventory.setVisible(inventory.isOpen);
    }

    public void togglePause() {
        pause.setVisible(isPaused);
    }

    public void toggleConsole() {
        console.isOpen = !console.isOpen;
        isPaused = console.isOpen; // smart, will copy consoel
        togglePause(); // needs to be this way
        menuConsole.setVisible(console.isOpen);

        if (console.isOpen) {
            menuConsoleCommand = new JTextField(32);
            menuConsoleCommand.setBounds(0, 180, 1280-(1280/2), 32);
            menuConsoleCommand.addActionListener(this); // smarter
            menuConsole.add(menuConsoleCommand);
        } else
            menuConsole.remove(menuConsoleCommand);
    }

    public void tryPickup() { // do i really need this much func->func ... in std
        for (int i=0; i<16; i++) { // i need to do sprite, and find way to link items
            if (player.isNextTo(objects[i].sprite) && objects[i].item.ID != 0) {
                boolean worked = inventory.tryAutoItem(objects[i].item); // can this be in if statement
                if (worked) {
                    System.out.println("Collected item: " + objects[i].item.name);
                    objects[i].item = new Item(); // maybe make object.remove();
                    objects[i].sprite.setText(""); // might be best way
                } else {
                    System.out.println("Inventory is full");
                }
                refreshInv(); // just need gui
                return; // so you dont pick up multiple
            }
            if (player.isNextTo(pickups[i].sprite) && pickups[i].ID != 0) {
                System.out.println("Pickuped: " + pickups[i].name);
                pickups[i].actOnEntity(player);
                pickups[i].sprite.setText(""); // has to be first, otherwise it "sticks"
                pickups[i] = new Pickup();
                return; // so you dont pick up multiple
            }
        }
    }

    public void refreshInv() { // should be here i think
        for (int i=0; i<16; i++)
                for(int j=0; j<16; j++) { // i think this is rigth /* inventory.getItem(i, j).ID != inventory.itemHeld.ID */
                    if (inventory.getItem(i, j).ID != 0 && inventory.getItemIndex(i, j) != inventory.heldIndex) // still feel i can do smarter
                        menuInvItems[i][j].setText(String.valueOf(inventory.getItem(i, j).ID/*.getSprite()*/));
                    else
                        menuInvItems[i][j].setText("");
                }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = menuConsoleCommand.getText(); // amybe add all in console, to allow for more options and change
        String output = console.tryRun(command);
        menuConsoleCommand.setText("");
        System.out.println(output); // yeabh add to Console, to properly deal with, should multiple things hande ui, or just path to it
    }

    // function to randomly generate the map
    public void genMap() { // i need to mapCells too, RIGHT this function can take time because it only happens once
        Random rng = new Random();
        cells[0][0] = new Cell(25, 25, 25); // empty cell (should be empty, but not for testing)
        int CELLS_THRESH = 8;
        int cells_cnt = 0;
        for (int i = 0; i < mapMAX; i++)
            for (int j = 0; j < mapMAX; j++) { // i think this is smart by saving checks, using "gimmick", idk
                // sliced bread wont work, needs object and create connections in one
                boolean up = false;
                boolean down = false;
                boolean left = false;
                boolean right = false;

                if (i > 0 && cells[i-1][j].info != "") // so smart, first check if can, then check if is, abuses order of ifs
                    up = true;
                if (i < mapMAX-1 && cells[i+1][j].info != "")
                    down = true;
                if (j > 0 && cells[i][j-1].info != "")
                    left = true;
                if (j < mapMAX-1 && cells[i][j+1].info != "")
                    right = true;
                
                if (up || down || left || right) // maybe make if all are true, then higher chance
                    for (int k = 0; k < i; k++) // increase chances as it goes through loop
                        if (rng.nextInt(i+1) == 0) { // cant be "0" kinda dumb
                            int cid = 0;
                            if (up)
                                cid+=8;
                            if (down) // as of now (in mapCells) this may always be false
                                cid+=4;
                            if(left)
                                cid+=2;
                            if(right)
                                cid+=1;
                            cells[i][j] = new Cell(10, 10, 10);// CELLS.map(cells[i][j], up, down left, right);
                            cells_cnt++;
                            break; // break if already created cell? so it doesnt recreate
                        }  
            }
        // check if there are enough cells
        if (cells_cnt < CELLS_THRESH) { // is this bad
            System.out.println("Too few cells, re-genning");
            genMap();
        }
        // check for path to end
        System.out.println("Generated map");
        // then here create different cells based on its neighbors
        //refreshMap(); // i dont think this needs to be here, cause it has to load at the very end anyway, after cell is loaded
    }

    public void checkCell() {
        //cells[mapX][mapY].info = "?"; // smart, maybe come back to, caused issue
        boolean changedCell = false; // should i load the cell or just do cell.doStuff(), objects = cell.objects would be fun, but cell.reDraw seems btr

        if (player.x < 0 && mapX > 0) { // maybe make block size
            player.x = 0; //1280; // you cant go left anymore
            //mapX--;
            //changedCell = true;
        } else if (player.x > 1280 && mapX < mapMAX-1) { // should be minus one, because of index mis-allign
            player.x = 0;
            mapX++;
            changedCell = true;
        } else if (player.y < 0 && mapY > 0) { // should not be ">="
            player.y = 720;
            mapY--;
            changedCell = true;
        } else if (player.y > 720 && mapY < mapMAX-1) { // if "<=" will go to MAX++
            player.y = 0;
            mapY++;
            changedCell = true;
        }

        if (changedCell) { // lighten the load
            cells[mapX][mapY].discover();
            //cells[mapX][mapY].info = "@"; // maybe just change ui, and not cell, how to change back to what is known
            refreshMap();
            loadCell(cells[mapX][mapY], true);
        }
    }

    public void loadCell(Cell cell, boolean isReload) {
        // unload old stuff
        if (isReload) // if removing old assets, ie. not on startup
            for (int i = 0; i < MAX_STUFF; i++) {
                game.remove(objects[i].sprite);
                game.remove(enemies[i].sprite);
                game.remove(pickups[i].sprite);
            }

        // use new cell stuff
        objects = cell.objects;
        enemies = cell.enemies;
        pickups = cell.pickups;

        // load new stuff
        for (int i = 0; i < MAX_STUFF; i++) {
            game.add(objects[i].sprite);
            game.add(enemies[i].sprite);
            game.add(pickups[i].sprite);
        }
    }

    public void refreshMap() {
        for (int i=0; i<mapMAX; i++)
            for(int j=0; j<mapMAX; j++) {
                mapCells[i][j].setText(cells[i][j].infoGet());
                if (i == mapX && j == mapY) // smart
                    mapCells[i][j].setText("@");
                if (i < mapX) // shows you cant go to these locations anymore. maybe do grey
                    mapCells[i][j].setForeground(Color.RED);
            }
    }
}

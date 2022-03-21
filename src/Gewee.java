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

    Item[] cellItems = new Item[16];
    JLabel[] labelItems = new JLabel[16];
    Enemy enemy;

    boolean isPaused = false; // should this be here or somewhere else
    JLabel pause;

    Console console = new Console();
    JPanel menuConsole = new JPanel();
    JTextField menuConsoleCommand;

    int mapX = 0, mapY = 0, mapMAX = 4;
    JPanel menuMap = new JPanel();
    JLabel[][] mapCells = new JLabel[mapMAX][mapMAX];

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

        // minimap
        menuMap.setLayout(null);
        menuMap.setBounds(1280-(1280/5), 0, 256, 256);
        menuMap.setBackground(new Color(0, 0, 0, 100));
        int menuCellSize = 256/mapMAX;
        for (int i=0; i<mapMAX; i++)
            for(int j=0; j<mapMAX; j++) {
                mapCells[i][j] = new JLabel("?", SwingConstants.CENTER);
                mapCells[i][j].setBounds(menuCellSize*i, menuCellSize*j, menuCellSize, menuCellSize);
                menuMap.add(mapCells[i][j]);
            }

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

        for (int i=0; i<16; i++) {
            cellItems[i] = new Item();
            //cellItems[i].ID = i;
        }

        for (int i=0; i<16; i++) { // is this ref
            cellItems[i] = ITEMS.HATCHET.cloneDeep();
            labelItems[i] = new JLabel(""+cellItems[i].ID);
            labelItems[i].setBounds(i*32, i*32, 32, 32);
            game.add(labelItems[i]);
        }
        
        menu.add(menuInventory);
        game.setBackground(Color.GREEN); // if opaque, no color
        
        player = new Player(0, 0, 5, ImageIO.read(new File("res/player.png")));
        game.add(player.getSprite());

        enemy = new Enemy(500, 500, 2, "e");
        game.add(enemy.getSprite());

        add(game, 1, 0);
        add(menu, 2, 0);
        add(pause, 3, 0); // make this esc menu at some point
        add(menuMap, 4, 0);
        add(menuConsole, 5, 0);
    }

    public void reDraw() { // change name
        for (int i=0; i<16; i++)
            if (cellItems[i].ID != 0)
                labelItems[i].setBounds(i*32, i*32, 32, 32);
        //picLabel.setLocation(player.x, player.y);
        player.setSpritePos(player.x, player.y); // setLocation uses thing i dont know name of (uses '-' in graph)
        enemy.setSpritePos(enemy.x, enemy.y); // maybe make ()
    }

    public void tick() {
        if (isPaused) {

            return;
        } // if the game is paused, stop ticks

        player.x += player.velocityX; // change to player.tick
        player.y += player.velocityY;
        enemy.act(this);
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
            if (player.isNextTo(labelItems[i]) && cellItems[i].ID != 0) {
                boolean worked = inventory.tryAutoItem(cellItems[i]); // can this be in if statement
                if (worked) {
                    System.out.println("Collected item: " + cellItems[i].name);
                    cellItems[i] = new Item();
                    labelItems[i].setText(""); // might be best way
                } else {
                    System.out.println("Inventory is full");
                }
                refreshInv();
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

    public boolean isEntity(int point[]) {
        if (player.isAt(point))
            return true;
        //System.out.println("Gewee.isEntity()");

        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = menuConsoleCommand.getText(); // amybe add all in console, to allow for more options and change
        String output = console.tryRun(command);
        menuConsoleCommand.setText("");
        System.out.println(output); // yeabh add to Console, to properly deal with, should multiple things hande ui, or just path to it
    }

    // function to randomly generate the map
    public void genMap() {

    }
}

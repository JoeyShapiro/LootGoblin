import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

public class Gewee extends JLayeredPane { // maybe make static, only need one?? how work?
    static JPanel menu = new JPanel(new BorderLayout());
    static JPanel game = new JPanel();
    static JLayeredPane menuInventory = new JLayeredPane(/*new BorderLayout()*/); // i doht care, its a game
    static boolean isIn = false;
    int WHAT = 32; // the y starts this much early, but doesnt say, unless you click, im gussing y cnts bar

    JLabel menuItemHeld = new JLabel();
    JLabel[][] menuInvItems = new JLabel[16][16];

    Inventory inventory = new Inventory(16, 16);
    Player player;
    JLabel picLabel;
    Item[] cellItems = new Item[16];
    JLabel[] labelItems = new JLabel[16];

    public Gewee(/* int width, int height */) throws IOException {
        this.setLayout(null);
        game.setLayout(new BorderLayout());
        game.setBounds(0, 0, 1280, 720);
        menu.setBounds(0, 0, 1280, 720);
        menu.setLayout(null); // now it needs this, bugs out with LayeredPanel
        menu.setOpaque(false);

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
        picLabel = new JLabel(new ImageIcon(player.getSprite()));
        game.add(picLabel);

        add(game, 1, 0);
        add(menu, 2, 0);
    }

    public void reDraw() { // change name
        for (int i=0; i<16; i++)
            if (cellItems[i].ID != 0)
                labelItems[i].setBounds(i*32, i*32, 32, 32);
        //picLabel.setLocation(player.x, player.y);
        picLabel.setBounds(player.x, player.y, 32, 32); // setLocation uses thing i dont know name of (uses '-' in graph)
    }

    public void tick() {
        player.x += player.velocityX; // change to player.tick
        player.y += player.velocityY;
    }

    public void toggleInventory() {
        inventory.isOpen = !inventory.isOpen; // smart, thank you
        menuInventory.setVisible(inventory.isOpen);
    }

    public void tryPickup() { // do i really need this much func->func ... in std
        for (int i=0; i<16; i++) { // i need to do sprite, and find way to link items
            if (player.isNextTo(labelItems[i]) && cellItems[i].ID != 0) {
                boolean worked = inventory.tryPlaceItem(5, 10, cellItems[i]); // can this be in if statement
                if (worked) {
                    System.out.println("Collected item: " + cellItems[i].name);
                    cellItems[i] = new Item();
                    labelItems[i].setText("collected");
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
                for(int j=0; j<16; j++) { // i think this is rigth
                    if (inventory.getItem(i, j).ID != 0 && inventory.getItem(i, j).ID != inventory.itemHeld.ID) // still feel i can do smarter
                        menuInvItems[i][j].setText(String.valueOf(inventory.getItem(i, j).ID/*.getSprite()*/));
                    else
                        menuInvItems[i][j].setText("");
                }
    }
}

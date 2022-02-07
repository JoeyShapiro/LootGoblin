import java.awt.*;
import java.io.File;
import java.time.Instant;

import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class App extends Frame implements KeyListener, MouseInputListener {
    static int x = 0;
    static int y = 0;
    static int speed = 5;
    static int velocityX = 0;
    static int velocityY = 0;
    static double tickSpeed = 10;
    // find better way
    static JFrame frame = new JFrame("Loot Goblin");
    static JPanel panel = new JPanel(new BorderLayout());
    static JLayeredPane menuInventory = new JLayeredPane(/*new BorderLayout()*/); // i doht care, its a game
    static boolean isOpen = false;
    static boolean isIn = false;
    static int[][] inventory = new int[16][16]; // lookup table might be easier
    static int WHAT = 32; // the y starts this much early, but doesnt say, unless you click, im gussing y cnts bar
    static int itemHeld = 0;

    static JLabel menuItemHeld = new JLabel();
    static JLabel[][] menuInvItems = new JLabel[16][16];
    
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        String os = System.getProperty("os.name");
        System.out.println(os);
        // System.getProperties().list(System.out); OP
        if (os == "Mac OS X") {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.application.name", "Loot Goblin");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1280, 720));

        App k = new App();
		frame.addKeyListener(k);
        frame.addMouseListener(k);
        frame.addMouseMotionListener(k); // annoying as hell
        
        menuInventory.setLocation(300, 100); // this must be before panel is added
        menuInventory.setLayout(null); // have to set it here
        JLabel invBackplate = new JLabel(new ImageIcon(ImageIO.read(new File("res/invBackplate.png"))));
        invBackplate.setBounds(0,0,512,512); // i hate swing
        for (int i=0; i<16; i++)
            for(int j=0; j<16; j++) { // maybe move eslewhere
                menuInvItems[i][j] = new JLabel(String.valueOf((i+j*16)+1));
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
        panel.add(menuInventory);

        panel.setBackground(Color.GREEN); // if opaque, no color

        frame.add(panel);
        Player player = new Player(0, 0, 5, ImageIO.read(new File("res/player.png")));
        JLabel picLabel = new JLabel(new ImageIcon(player.getSprite()));
        panel.add(picLabel);


        //4. Size the frame.
        frame.pack();
        //5. Show it.
        frame.setVisible(true);
        menuInventory.setVisible(false);

        for (int i=0; i<16; i++)
            for (int j=0; j<16; j++)
                inventory[i][j] = (i+j*16)+1; // creates a unique number for each, just memeing
                // +1 because 0 is gimmick item, to be NULL

        Instant now = Instant.now();
        while (true) {
            now = tick(now); // super cleaver !?
            picLabel.setLocation(x, y);
            frame.repaint();
            invRedraw();
        }
    }

    public static Instant tick(Instant then) {
        Instant now = Instant.now();
        Instant nowNew = then; // only change when greater than ts, otherwise is only 0 or 1 becuase it runs as fast as possible
        if ((now.toEpochMilli() - then.toEpochMilli()) >= tickSpeed) {
            x += velocityX;
            y += velocityY;
            nowNew = Instant.now(); // this is fine
        }
        
        return nowNew;
    }

    public static void invRedraw() {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("key typed: " + e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key pressed: " + e.getKeyChar());
        if (!isOpen) { // move if not in inventory (maybe find better way)
                 if (e.getKeyChar() == 'w') // up (symmetric :P)
                velocityY = -speed;
            else if (e.getKeyChar() == 's') // down
                velocityY = speed;
            else if (e.getKeyChar() == 'a') // left
                velocityX = -speed;
            else if (e.getKeyChar() == 'd') // right
                velocityX = speed;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("key released: " + e.getKeyChar());
             if (e.getKeyChar() == 'w') // up
            velocityY = 0;
        else if (e.getKeyChar() == 's') // down
            velocityY = 0;
        else if (e.getKeyChar() == 'a') // left
            velocityX = 0;
        else if (e.getKeyChar() == 'd') // right
            velocityX = 0;
        else if (e.getKeyChar() == 'f') // pickup
            System.out.println("PICKUP");
        else if (e.getKeyChar() == 'e') // inventory
            toggleInventory();
    }

    public static void toggleInventory() {
        isOpen = !isOpen; // smart, thank you
        menuInventory.setVisible(isOpen);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse clicked: " + e.getPoint());
        int x = e.getX(); // i think this is more efficient
        int y = e.getY()-WHAT; // deal with whatever that is
        // check if in inventory
        if (isOpen && x > 300 && x < 1000 && y > 100 && y < 612) {
            // im clever, this will get the item place in inv, seems convoluded though
            int invX = ((x-((x-300)%32))-300)/32; // super confusing, must write down
            int invY = ((y-((y-100)%32))-100)/32; // divison, duh

            System.out.println("item @ (" + invX + "," + invY + "): " + inventory[invX][invY]);
            int tmp = inventory[invX][invY]; // honestly i guessed, and got the buffer right
            inventory[invX][invY] = itemHeld;
            itemHeld = tmp;
            menuItemHeld.setText(String.valueOf(itemHeld));
            if (itemHeld == 0) { // if buffer is null
                menuItemHeld.setVisible(false);
            } else {
                menuItemHeld.setVisible(true);
                System.out.println(itemHeld);
            }
            // update ui, only update one taht is changed, smarte
            if (inventory[invX][invY] != 0 && inventory[invX][invY] != itemHeld) // still feel i can do smarter
                menuInvItems[invX][invY].setText(String.valueOf(inventory[invX][invY]/*.getSprite()*/));
            else
                menuInvItems[invX][invY].setText("");
        } else { // maybe this way, will fire if inv open and outside space, but might be fine

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mouse pressed" + e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouse Released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouse entered");
        isIn = true; // maybe but how to get start
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouse exited");
        isIn = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println("mouse dragged");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("mouse moved"); // annoying
        if (itemHeld != 0)
            menuItemHeld.setLocation(e.getX()-300, e.getY()-100);
    }
}

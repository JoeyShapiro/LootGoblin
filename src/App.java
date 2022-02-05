import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Instant;

import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.*;

public class App implements KeyListener {
    static int x = 0;
    static int y = 0;
    static int speed = 5;
    static int velocityX = 0;
    static int velocityY = 0;
    static double tickSpeed = 10;
    // find better way
    static JFrame frame = new JFrame("Loot Goblin");
    static JPanel panel = new JPanel(new BorderLayout());
    static GridLayout invLayout = new GridLayout(16, 16);
    static JPanel inventory = new JPanel(invLayout); // i doht care, its a game
    static boolean isOpen = false;
    
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

        JPanel[] itemPanels = new JPanel[16*16];
        for (int i=0; i<16; i++) {
            for (int j=0; j<16; j++) {
                //JLabel itemBackplate = new JLabel(new ImageIcon(ImageIO.read(new File("res/itemBackplate.png"))));
                itemPanels[i*j] = new JPanel();
                //itemPanels[i*j].setLocation(i*16, j*16);
                //itemPanels[i*j].add(itemBackplate); // background colors dont work, but this does
                inventory.add(itemPanels[i*j]);
            }
        }
        
        inventory.setLocation(300, 100); // this must be before panel is added
        invLayout.setHgap(1);
        invLayout.setVgap(1);
        inventory.setSize(700, 500);
        inventory.setBackground(Color.LIGHT_GRAY);
        panel.add(inventory);

        panel.setBackground(Color.GREEN); // if opaque, no color

        frame.add(panel);
        BufferedImage myPicture = ImageIO.read(new File("res/player.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panel.add(picLabel);


        //4. Size the frame.
        frame.pack();
        //5. Show it.
        frame.setVisible(true);
        inventory.setVisible(false);

        Instant now = Instant.now();
        while (true) {
            now = tick(now); // super cleaver !?
            picLabel.setLocation(x, y);
            frame.repaint();
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

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("key typed: " + e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key pressed: " + e.getKeyChar());
             if (e.getKeyChar() == 'w') // up (symmetric :P)
            velocityY = -speed;
        else if (e.getKeyChar() == 's') // down
            velocityY = speed;
        else if (e.getKeyChar() == 'a') // left
            velocityX = -speed;
        else if (e.getKeyChar() == 'd') // right
            velocityX = speed;
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
            openInventory();
    }

    public static void openInventory() {
        isOpen = !isOpen; // smart, thank you
        inventory.setVisible(isOpen);
    }
}

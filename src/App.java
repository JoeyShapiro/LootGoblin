import java.awt.*;
import java.time.Instant;
import javax.swing.*;

public class App extends Frame {
    static double tickSpeed = 10;
    // find better way
    static JFrame frame = new JFrame("Loot Goblin");
    static Gewee gui; // should this hold game too, knows what to put, then game handles how it runs, holds now, move to Game
    
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
        
        gui = new Gewee();
        frame.add(gui);

        KbmListener kin = new KbmListener(gui);
		frame.addKeyListener(kin);
        frame.addMouseListener(kin);
        frame.addMouseMotionListener(kin); // annoying as hell

        //4. Size the frame.
        frame.pack();
        //5. Show it.
        frame.setVisible(true);

        Instant now = Instant.now();
        while (true) {
            now = tick(now); // super cleaver !?
            gui.reDraw();
            frame.repaint();
        }
    }

    public static Instant tick(Instant then) {
        Instant now = Instant.now();
        Instant nowNew = then; // only change when greater than ts, otherwise is only 0 or 1 becuase it runs as fast as possible
        if ((now.toEpochMilli() - then.toEpochMilli()) >= tickSpeed) {
            gui.tick();
            nowNew = Instant.now(); // this is fine
        }
        
        return nowNew;
    }
}

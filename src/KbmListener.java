import java.awt.event.*;
import javax.swing.event.MouseInputListener;

public class KbmListener implements KeyListener, MouseInputListener {
    Gewee gui;

    public KbmListener(Gewee g) {
        gui = g;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("key typed: " + e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key pressed: " + e.getKeyChar());
        if (!gui.inventory.isOpen) { // move if not in inventory (maybe find better way)
                 if (e.getKeyChar() == 'w') // up (symmetric :P)
                gui.player.velocityY = -gui.player.speed;
            else if (e.getKeyChar() == 's') // down
                gui.player.velocityY = gui.player.speed;
            else if (e.getKeyChar() == 'a') // left
                gui.player.velocityX = -gui.player.speed;
            else if (e.getKeyChar() == 'd') // right
                gui.player.velocityX = gui.player.speed;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("key released: " + e.getKeyChar());
             if (e.getKeyChar() == 'w') // up
            gui.player.velocityY = 0;
        else if (e.getKeyChar() == 's') // down
            gui.player.velocityY = 0;
        else if (e.getKeyChar() == 'a') // left
            gui.player.velocityX = 0;
        else if (e.getKeyChar() == 'd') // right
            gui.player.velocityX = 0;
        else if (e.getKeyChar() == 'f') // pickup
            gui.tryPickup();
        else if (e.getKeyChar() == 'e') // inventory
            gui.toggleInventory();
        else if (e.getKeyChar() == '`') // console
            gui.toggleConsole();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse clicked: " + e.getPoint());
        int x = e.getX(); // i think this is more efficient
        int y = e.getY()-gui.WHAT; // deal with whatever that is
        // check if in inventory
        if (gui.inventory.isOpen && x > 300 && x < 812 && y > 100 && y < 612) { // this was x<100 & y<612, to big, how did i not notice
            // im clever, this will get the item place in inv, seems convoluded though
            int invX = gui.inventory.getInvX(x); // super confusing, must write down
            int invY = gui.inventory.getInvY(y); // divison, duh
            gui.inventory.tryInventory(invX, invY, gui.inventory.itemHeld);
            gui.menuItemHeld.setText(String.valueOf(gui.inventory.itemHeld.ID));
            if (gui.inventory.itemHeld.ID == 0) { // if buffer is null
                gui.menuItemHeld.setVisible(false);
            } else {
                gui.menuItemHeld.setVisible(true);
                System.out.println(gui.inventory.itemHeld.ID);
            }
            // update ui, only update one taht is changed, smarte
            // Item itemChanged = gui.inventory.itemHeld;
            // if (gui.inventory.itemHeld.ID == 0)
            //     itemChanged = gui.inventory.getItem(invX, invY); // has to be up here
            //for (int i=invX; i<itemChanged.width+invX; i++)
            //for(int j=invY; j<itemChanged.height+invY; j++)
            gui.refreshInv();
        } else if (gui.inventory.itemHeld.ID != 0 && gui.inventory.isOpen && x > 812 && x < 1000 && y > 512 && y < 612) { // find cleaner way to organize
            for (int i = 0; i < gui.MAX_STUFF; i++) { // make function for this stuff
                if (gui.objects[i].item.ID == 0) {
                    gui.objects[i].item = gui.inventory.itemHeld;
                    gui.inventory.itemHeld = new Item();
                    gui.objects[i].setPos(gui.player.x, gui.player.y);
                    gui.menuItemHeld.setVisible(false);
                    break;
                }
            }
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
        // isIn = true; // maybe but how to get start
        gui.isPaused = false;
        gui.togglePause(); // should be here i think, best way, otherwise has to keeps checking
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouse exited");
        // isIn = false;
        gui.isPaused = true;
        gui.togglePause();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("mouse dragged");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("mouse moved"); // annoying
        if (gui.inventory.itemHeld.ID != 0)
            gui.menuItemHeld.setLocation(e.getX()-300, e.getY()-100);
    }
}

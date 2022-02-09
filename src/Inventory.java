public class Inventory {
    int MAX_ITEMS = 256; // it is mathmatically impossible to have more than this
    int[][] indexMap; // inventory
    Item[] items; // items list
    Item itemHeld;
    public boolean isOpen; // should this be here or in GUI (gew-ee)
    int itemCnt; // is this best

    public Inventory(int w, int h) {
        indexMap = new int[w][h];
        items = new Item[MAX_ITEMS];
        itemCnt = 0; // 0 is null i think, in the table
        for (int i=0; i<256; i++) // needs this i guess
            items[i] = new Item();
        
        itemHeld = new Item();
        for (int i=0; i<16; i++)
            for (int j=0; j<16; j++)
                indexMap[i][j] = 0/*(i+j*16)+1*/;
        
        Item itemTEST = new Item();
        itemTEST.ID = 5;
        tryPlaceItem(0, 0, itemTEST);
    }

    public Item getItem(int invX, int invY) {
        int index = indexMap[invX][invY];

        return items[index];
    }

    public void swapItem(int invX, int invY) {
        System.out.println("swap");
        // im clever, this will get the item place in inv, seems convoluded though
        int index = indexMap[invX][invY];

        System.out.println("item @ (" + invX + "," + invY + "): " + items[index].ID);
        // Item tmp = items[index]; // honestly i guessed, and got the buffer right
        // items[index] = itemHeld;
        // itemHeld = tmp;
        Item tmp = getItem(invX, invY);
        items[indexMap[invX][invY]] = itemHeld;
        itemHeld = tmp;
    }

    public boolean tryPlaceItem(int invX, int invY, Item item) {
        /* // want to try with vector and using (compare item locations)? rather than index
             ---- W ----- invX
            |   |   
            |--[i i]                (invX, invY) => lcoation of topleft index, width/height show size
            H  [i i]                 might be reversed or something, goes down each y then x
            |  [i i]                 as long as it works right
            |       (invX, invY)     w and x are same
            invY
        */

        System.out.println("place");
        // check if can fit, and nothing in way
        for (int i=0; i<item.width; i++)
            if (indexMap[i][invY] != 0) // should be items[indexMap[i][invY]].ID != 0, or something else, im 0 is null
                return false;

        for (int i=0; i<item.height; i++)
            if (indexMap[invX][i] != 0)
                return false;

        // place in array
        itemCnt++; // do here, then it uses it
        items[itemCnt] = item; // cnt doesnt make to much sense, should be listID or something

        for (int i=0; i<item.width; i++)
            for (int j=0; j<item.height; j++)
                indexMap[i][j] = itemCnt;

        itemHeld = new Item();

        return true;
    }

    public void removeItem(int index) { // just to clean it up, in multiple functions
        System.out.println("remove");
        // handle all by parts, to show what is happening, can be used by part
        for (int i=0; i<16; i++)
            for(int j=0; j<16; j++)
                indexMap[i][j] = 0;
        
        items[index] = new Item();
    }

    public Item removeNgetItem(int invX, int invY) { // just do index here
        System.out.println("removeNget");
        int index = indexMap[invX][invY];
        Item itemGone = items[index].cloneDeep(); // might just be pnt

        System.out.println("item ID before removed: " + items[index].ID);
        removeItem(index);
        System.out.println("item ID after removed: " + items[index].ID);
        return itemGone;
    }

    public boolean trySwapItem(int invX, int invY, Item itemNew) {
        System.out.println("try swap");
        boolean worked = false;
        Item itemOld = removeNgetItem(invX, invY);
        worked = tryPlaceItem(invX, invY, itemNew);
        if (!worked) { // if it failed, need to check i think
            tryPlaceItem(invX, invY, itemOld); // didnt work, replace old item
            return worked;
        }
        itemHeld = itemOld; // held can not be empty, this is because of rules in tryInv

        return worked;
    }

    public boolean tryInventory(int invX, int invY, Item itemNew) { // crappy name, handles everything (maybe change to itemHeld, because know it)
        /*  I H truth table for inv and item held (inventory, item held) 0=nothing 1=something
            0 0 if inventory is empty and item held is empty
            0 1 if inventory is empty and item held is not empty
            1 0 if inventory is not empty and item is empty
            1 1 if inventory is not empty and held is not empty
        */
        System.out.println("item @ (" + invX + "," + invY + "): ID: " + items[indexMap[invX][invY]].ID);
        Item itemOld = getItem(invX, invY);
        boolean worked = false;
             if (itemOld.ID == 0 && itemHeld.ID != 0) // 0 1 if holding item and inventory spot is empty
            worked = tryPlaceItem(invX, invY, itemNew);
        else if (itemOld.ID != 0 && itemHeld.ID == 0) { // 1 0 if not holding an item and inventory spot is not empty
            itemHeld = removeNgetItem(invX, invY);
            worked = true;
        } else if (itemOld.ID == 0 && itemHeld.ID == 0) // 0 0 if both are empty
            worked = true; // sure... you did nothing... congrats
        else if (itemOld.ID != 0 && itemHeld.ID != 0) // 1 1 if both have something, the worst
            worked = trySwapItem(invX, invY, itemNew);
        // else // figure out DEBUG

        System.out.println(worked);
        return worked;
    }

    // here for now
    public int getInvX(int x) { return ((x-((x-300)%32))-300)/32; } // super confusing, must write down
    public int getInvY(int y) { return ((y-((y-100)%32))-100)/32; } // divison, duh
}

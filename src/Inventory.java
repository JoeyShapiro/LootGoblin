public class Inventory {
    int MAX_ITEMS = 256; // it is mathmatically impossible to have more than this
    int[][] indexMap; // inventory
    Item[] items; // items list
    Item itemHeld;
    int intheld = 0;
    public boolean isOpen; // should this be here or in GUI (gew-ee)

    public Inventory(int w, int h) {
        indexMap = new int[w][h];
        items = new Item[MAX_ITEMS];
        itemHeld = new Item();
        for (int i=0; i<16; i++)
            for (int j=0; j<16; j++)
                indexMap[i][j] = (i+j*16)+1;
    }

    public int getItem(int x, int y) {
        int invX = ((x-((x-300)%32))-300)/32; // super confusing, must write down
        int invY = ((y-((y-100)%32))-100)/32; // divison, duh

        int index = indexMap[invX][invY];

        return index;
    }

    public void swapItem(int x, int y) {
        // im clever, this will get the item place in inv, seems convoluded though
        int invX = ((x-((x-300)%32))-300)/32; // maybe move elsewhere, this isnt inv its ui
        int invY = ((y-((y-100)%32))-100)/32; 
        int index = indexMap[invX][invY];

        System.out.println("item @ (" + invX + "," + invY + "): " + items[index]);
        // Item tmp = items[index]; // honestly i guessed, and got the buffer right
        // items[index] = itemHeld;
        // itemHeld = tmp;
        int tmp = indexMap[invX][invY];
        indexMap[invX][invY] = intheld;
        intheld = tmp;
    }

    public int getInvX(int x) { return ((x-((x-300)%32))-300)/32; }
}

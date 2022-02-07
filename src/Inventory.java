public class Inventory {
    int MAX_ITEMS = 256; // it is mathmatically impossible to have more than this
    int[][] IndexMap; // inventory
    Item[] Items; // items list
    Item itemHeld;
    boolean isOpen; // should this be here or in GUI (gew-ee)
}

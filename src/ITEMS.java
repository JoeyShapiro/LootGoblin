public class ITEMS { // i feel there is a more "standard" way of doing this
    // (ID, Name, Flavor Text, Price, Width, Height)
    // should id auto-inc ??
    private static int lid = 1; // why not, this way it keeps ids clean, try with 1 (skip nothgin)
    // Debug
    public Item NOTHING = new Item();
    public Item DEBUG = new Item(lid++, "2DTest", "TPI TPI TPI", 100, 2, 3);
    // Weapons
    public static Item HATCHET = new Item(lid++, "Hatchet", "A small axe for small axe needs", 10, 1, 2);
    // Consumables
    public Item TINCTURE_HEALTH = new Item();
    public Item RAT_BURGER = new Item(lid++, "Rat Burger", "Squekers?, is that you? Oh god", 3, 1, 1);
    // Materials?
    public Item WOOD = new Item();
    // Amore :P
    public Item HELMET_WOOD = new Item(lid++, "Wooden Helm of Fire", "It seemed like a good idea at the time", 5, 1, 1);
    // Junk
    public Item BLODDY_RAG = new Item(lid++, "Bloody Rag", "Let's hope it's not your's", 1, 1, 1);
    // Misc? like coins and stuff, these will need special rules

    // maybe better
    private static Item[] LIST_ITEMS = { // dont need NAME anymore
        // Debug
        new Item(),
        new Item(lid++, "2DTest", "TPI TPI TPI", 100, 2, 3),
        // Weapons
        new Item(lid++, "Hatchet", "A small axe for small axe needs", 10, 1, 2),
        // Consumables
        new Item(),
        new Item(lid++, "Rat Burger", "Squekers?, is that you? Oh god", 3, 1, 1),
        // Materials?
        new Item(),
        // Amore :P
        new Item(lid++, "Wooden Helm of Fire", "It seemed like a good idea at the time", 5, 1, 1),
        // Junk
        new Item(lid++, "Bloody Rag", "Let's hope it's not your's", 1, 1, 1)
    };

    // FUNCTIONS
    public Item spawnRandomItem() { // should this be here, or somewhere else
        return NOTHING;
    }

    public Item spawnItem(int id) {
        return LIST_ITEMS[id].cloneDeep();
    }
}

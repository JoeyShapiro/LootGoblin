public class TILES {
    private static Tile[] LIST_TILES = { // should i do something without x,y. this is fine
        new Tile(),
    };

    public static Tile getTile(int id) {
        return LIST_TILES[id].cloneDeep();
    }

    public static Tile[][] getTiles(int shape[][]) {
        
    }
}

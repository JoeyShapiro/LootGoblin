public class TILES {
    private static Tile[] LIST_TILES = { // should i do something without x,y. this is fine
        new Tile(),
        new Tile("f", null, false), // floor
        new Tile("w", null, true) // wall
    };

    public static Tile getTile(int id) {
        return LIST_TILES[id].cloneDeep();
    }

    public static Tile[][] getTiles(int shape[][]) { //  could be list, then  use x,y to place
        Tile[][] tmp = new Tile[32][22]; // this might be reversed. 

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 22; j++) {
                tmp[i][j] = getTile(shape[j][i]); // have to i htink. or load tmp differently
                //tmp[i][j].setPos(i*32, j*32); //doesnt work
            }
        }

        return tmp;
    }
}

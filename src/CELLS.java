import java.util.Random;

public class CELLS {
    private static int lid = 1;
    private static Random rng = new Random();
    // all the cells because this is the best way i can think of
    // +--- 32 ---
    // |
    // 22
    // |
    private static int[][] none = { // maybe store in file, then open it when creating, then close
        { 1 } // little cleaner and maybe smarter to do ints
    };

    private static Cell[] LIST_CellS = { // should i do something without x,y. this is fine
        new Cell(0, 0, 0, TILES.getTiles(none)), // Null
        new Cell() // right, i think its only the start
        // left
        // horizontal
        // ,
        // ,-
        // -,
        // -,-
        // '
        // '-
        // -'
        // -'-
        // |
        // |-
        // -|
        // +
    };

    public static Cell getCell(int id) {
        return LIST_CellS[id].cloneDeep();
    }
}

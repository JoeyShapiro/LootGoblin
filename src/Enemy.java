import java.awt.image.BufferedImage;

public class Enemy extends Entity {

    Enemy() {
        super();
    }
    
    Enemy(int nx, int ny, int sp, BufferedImage s) {
        super(nx, ny, sp, s);
    }

    Enemy(int nx, int ny, int sp, String s) {
        super(nx, ny, sp, s);
    }


    public void act(Cell cell) { // is this inefficient
        // prelim
        velocityX = 0;
        velocityY = 0;

        int possibleMoves[][] = {{x-1, y}, {x+1, y}, {x, y}, {x, y-1}, {x, y+1}}; // would x-- move the enemey pos
        int legalMoves[][] = new int[5][2];
        int legalCnt = 0;
        for (int possibleMove[] : possibleMoves) {
            if (!cell.isEntity(possibleMove)) {
                legalMoves[legalCnt][0] = possibleMove[0];
                legalMoves[legalCnt++][1] = possibleMove[1]; // ++ here
            }
        }

        int bestMove[] = new int[2];
        double bestH = Double.POSITIVE_INFINITY; // INFINITY!!!!
        for (int legalMove[] : legalMoves) {
            int xPrime = legalMove[0];
            int yPrime = legalMove[1];
            double h = Math.sqrt(((xPrime - cell.player.x)*(xPrime - cell.player.x)) + ((yPrime - cell.player.y)*(yPrime - cell.player.y))); // euclidean (EFFICIENCY) // copied from SlicedBread
            if (h <= bestH) {
                bestH = h;
                bestMove[0] = xPrime;
                bestMove[1] = yPrime;
            }
        }
        
        if (bestMove[0] == x-1) // find smarter way
            velocityX = -speed;
        else if (bestMove[0] == x+1)
            velocityX = speed;
        if (bestMove[1] == y-1)
            velocityY = -speed;
        else if (bestMove[1] == y+1)
            velocityY = speed;

        x += velocityX;
        y += velocityY;
    }
}

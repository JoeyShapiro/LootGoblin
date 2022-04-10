public class Console {
    boolean isOpen;
    String[] log;
    Gewee gui;

    public Console(Gewee g) {
        isOpen = false;
        log = new String[6];
        gui = g;
    }

    public String tryRun(String command) {
        String output = "";

        String[] args = command.split(" ");

        // find better way
        if (args[0] == "spawn") { // spawn item.ID x y // spawn on me, spawn in me, spawn on (2,1), spawn in (2,1)??
            int id = Integer.parseInt(args[1]);
            int x = Integer.parseInt(args[2]);
            int y = Integer.parseInt(args[3]);
            
            spawnItemAt(id, x, y);
        } else if (args[0] == "exit") {
            gui.exit();
        }

        return output;
    }

    public String spawnItemAt(int id, int x, int y) {
        String output = "";

        

        return output;
    }
}

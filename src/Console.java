public class Console {
    boolean isOpen;

    public Console() {
        isOpen = false;
    }

    public String tryRun(String command) {
        String output = "";

        // find better way
        if (command.startsWith("spawnItemAt"))
            spawnItemAt(command.substring(12, 13));

        return output;
    }

    public String spawnItemAt(String item) {
        String output = "";

        

        return output;
    }
}

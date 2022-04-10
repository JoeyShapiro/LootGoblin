import java.util.Random;

public class PICKUPS {
    private static int lid = 1;
    private static Random rng = new Random();

    private static Pickup[] LIST_PICKUPS = { // should i do something without x,y. this is fine
        new Pickup(),
        new Pickup(lid++, "Teleport", "./res/teleport.png", e -> {e.x = rng.nextInt(1280); e.y = rng.nextInt(720); 
            System.out.println("Teleported entity to : (" + e.x + ", " + e.y + ")");}),
        new Pickup(lid++, "Health", "./res/health.png", e -> {e.health += 5; 
            System.out.println("Healed entity for 5");}) // cant have exit here, could spawn at random. could rng.next(lid-1)
    };

    public static Pickup getPickup(int id) {
        return LIST_PICKUPS[id].cloneDeep();
    }

    public static Pickup getPickupRandom() {
        return getPickup(rng.nextInt(lid));
    }

    public static Pickup spawnPickupRandom(int nx, int ny) {
        Pickup clone = getPickup(rng.nextInt(lid));
        clone.x = rng.nextInt(nx);
        clone.y = rng.nextInt(ny);
        return clone;
    }
}

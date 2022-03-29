import java.util.Random;

public class PICKUPS {
    private static int lid = 1;
    private static Random rng = new Random();

    private static Pickup[] LIST_PICKUPS = { // should i do something without x,y. this is fine
        new Pickup(),
        new Pickup(lid++, "Teleport", "t", e -> {e.x = rng.nextInt(1280); e.y = rng.nextInt(720);}),
        new Pickup(lid++, "Health", "h", e -> {e.health += 5;})
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

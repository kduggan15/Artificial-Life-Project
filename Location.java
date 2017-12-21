import java.util.*;

public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        set(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Location> getAdjacents()
    {
        ArrayList<Location> temp = new ArrayList<Location>();
        temp.add(new Location(x + 1, y));
        temp.add(new Location(x - 1, y));
        temp.add(new Location(x, y + 1));
        temp.add(new Location(x, y - 1));

        temp.add(new Location(x + 1, y + 1));
        temp.add(new Location(x - 1, y - 1));
        temp.add(new Location(x - 1, y + 1));
        temp.add(new Location(x + 1, y - 1));
        return temp;
    }

}
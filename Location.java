import java.util.*;

public class Location 
{
    private int x;
    private int y;

    // Initializes a Location with x and y values.
    public Location(int x, int y) {
        set(x, y);
    }

    // Returns the x value of this Location.
    public int getX() {
        return x;
    }

    // Returns the y value of this Location.
    public int getY() {
        return y;
    }

    // Sets the x and y values of this Location.
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

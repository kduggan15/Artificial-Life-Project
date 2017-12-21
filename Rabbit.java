import java.util.*;

public class Rabbit extends Animal
{
    public Rabbit(Cell myCell, int x, int y)
    {
        super(myCell, x, y);
        symbol = '!';
    }
    public void act()
    {
        age += 1;
        ArrayList<Location> possibleMoves = getValidLocations();
        Random r = new Random();
        Location choice = getEmptyLocations(possibleMoves).get(r.nextInt(possibleMoves.size()));
        move(choice);
    }
}
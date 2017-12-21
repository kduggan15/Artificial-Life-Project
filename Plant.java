public class Plant extends Organism
{
    public Plant(Cell myCell, int x, int y)
    {
        super(myCell, x, y);
        age = 0;
        energy = 20;
        symbol = '#';
    }

    public void act()
    {
        age += 1;
    }
}

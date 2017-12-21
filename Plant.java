public class Plant extends Organism{
    static public double sproutRate = 0.75;

    public Plant(int x, int y)
    {
        myLocation.set(x,y);
        age = 0;
        energy = 20;
        symbol = '#';
    }

    public void act()
    {
        age += 1;
    }
}

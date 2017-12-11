public class Plant extends Organism{
    static public double sproutRate = 0.75;

    public Plant(int x, int y){
        energy = 20;
        symbol = '#';
        locX = x;
        locY = y;
    }
}

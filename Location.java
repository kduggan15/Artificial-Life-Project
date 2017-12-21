public class Location
{
    private int x;
    private int y;
    public Location(int x, int y)
    {
        set(x,y);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void set(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}

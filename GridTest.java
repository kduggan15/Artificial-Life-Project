public class GridTest
{
    public static void main(String[] args)
    {
        Grid world = new Grid();
        while(!world.extinction())
        {
            world.daytime();
            System.out.println(world.toString());
        }
    }
}

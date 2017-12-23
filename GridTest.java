public class GridTest
{
    public static void main(String[] args)
    {
        Grid world = new Grid();
        while(!world.allAnimalsHaveDied())
        {
            System.out.println(world.toString());
            world.daytime();
        }
        System.out.println(world.summarizeStatistics());
    }
}

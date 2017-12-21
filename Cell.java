public class Cell
{

    public enum Terrain { PLAINS, MOUNTAINS, WATER; }
    private final Grid myGrid;
    private final Terrain terrain;
    private final Location location;
    private Organism inhabitant;

    public Cell(Grid myGrid, Terrain terrain, Location location)
    {
        this.myGrid = myGrid;
        this.terrain = terrain;
        this.location = location;
        inhabitant = null;
    }

    public Organism getInhabitant()
    {
        return inhabitant;
    }

    public void setInhabitant(Organism inhabitant)
    {
        this.inhabitant = inhabitant;
    }
    
    public void empty()
    {
        inhabitant = null;
    }

    public Grid getMyGrid()
    {
        return myGrid;
    }
}

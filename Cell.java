public class Cell
{

    public enum Terrain { PLAINS, MOUNTAINS, WATER; }
    private final Grid myGrid;
    private final Terrain terrain;
    private Organism inhabitant;

    public Cell(Grid myGrid, Terrain terrain)
    {
        this.myGrid = myGrid;
        this.terrain = terrain;
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

    public Grid getMyGrid()
    {
        return myGrid;
    }
}

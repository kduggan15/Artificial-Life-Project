public class Cell
{
    public enum Terrain { PLAINS, MOUNTAINS, ROCKS; }
    private Grid myGrid;
    private final Terrain terrain;
    private Location location;
    private Organism inhabitant;

    // Initializes a Cell that is empty (no inhabitant).
    public Cell(Grid myGrid, Terrain terrain, Location location)
    {
        this.myGrid = myGrid;
        this.terrain = terrain;
        this.location = location;
        inhabitant = null;
    }

    // Returns the inhabitant of this Cell (null if there is no inhabitant).
    public Organism getInhabitant()
    {
        return inhabitant;
    }

    // Sets an inhabitant for this Cell.
    public void setInhabitant(Organism inhabitant)
    {
        this.inhabitant = inhabitant;
    }
    
    // Removes the inhabitant in this Cell.
    public void empty()
    {
        inhabitant = null;
    }

    // Returns a reference to the Grid that this Cell exists in.
    public Grid getMyGrid()
    {
        return myGrid;
    }
    
    // Returns a reference to the Location of this Cell.
    public Location getLocation()
    {
        return location;
    }

    // Returns the terrain of this Cell.
    public Terrain getTerrain()
    {
        return terrain;
    }
}

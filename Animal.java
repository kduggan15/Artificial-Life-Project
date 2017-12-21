import java.util.*;

public abstract class Animal extends Organism
{
    public Animal(Cell myCell, int x, int y)
    {
        super(myCell, x, y);
    }
    public ArrayList<Location> getValidLocations()
    {
        ArrayList<Location> possibleMoves = myLocation.getAdjacents();
        for(int i = 0; i < possibleMoves.size(); i++)
        {
            if(possibleMoves.get(i).getX() < 0 || possibleMoves.get(i).getX() >= Grid.GRIDSIZE ||
                    possibleMoves.get(i).getY() < 0 || possibleMoves.get(i).getY() >= Grid.GRIDSIZE)
            {
                possibleMoves.remove(i);
                i = 0;
            }
        }
        return possibleMoves;
    }
    public ArrayList<Location> getEmptyLocations(ArrayList<Location> input)
    {
        for(int i = 0; i < input.size(); i++)
        {
            if(!(myCell.getMyGrid().getCell(input.get(i)).getInhabitant() instanceof Organism))
            {
                input.remove(i);
                i = 0;
            }
        }
        return input;
    }
    public void move(Location m)
    {
        Cell oldCell = myCell;
        Cell newCell = myCell.getMyGrid().getCell(m);
        newCell.setInhabitant(this);
        oldCell.setInhabitant(null);
        myCell = newCell;
    }
}

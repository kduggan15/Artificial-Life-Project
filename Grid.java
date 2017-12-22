import java.util.HashSet;
import java.util.Random;
import java.util.*;

public class Grid
{
    public static final int GRIDSIZE = 16;
    private Cell [][] board = new Cell[GRIDSIZE][GRIDSIZE];
    private int turnCount;
    private boolean paused;
    private int plantChance = 40;
    private int plantDailyChance = 3;
    private int herbivoreChance = 20;
    private int carnivoreChance = 10;

    public Grid()
    {
        int totalSpaces = GRIDSIZE * GRIDSIZE;
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                board[i][j] = new Cell(this, Cell.Terrain.PLAINS, new Location(i,j));
                Random r = new Random();
                double roll = r.nextInt(100);
                if(r.nextInt(100) < plantChance)
                {
                    board[i][j].setInhabitant(new Plant(board[i][j]));
                }
                else if(r.nextInt(100) < herbivoreChance)
                {
                    board[i][j].setInhabitant(new Rabbit(board[i][j]));
                }
                else if(r.nextInt(100) < carnivoreChance)
                {

                }

            }
        }
    }

    public boolean extinction()
    {
        for(int i = 0; i < GRIDSIZE; i++)
            for(int j = 0; j < GRIDSIZE; j++)
                if(board[i][j].getInhabitant() != null)
                    return false;
        return true;

    }

    public void moveAnimal(Cell a, Cell b)
    {
        a.setInhabitant(b.getInhabitant() ); // Moves animal's reference from a to b
        b.empty(); // Removes animal's reference from a
    }
    
    public ArrayList<Cell> getAdjacentCells(Cell a)
    {
        int x = a.getLocation().getX();
        int y = a.getLocation().getY();
        int max = GRIDSIZE - 1;
        ArrayList<Cell> result;
        
        if(x != 0)
        {
            if(y != 0)
                result.add(board[x - 1][y - 1]);
            result.add(board[x - 1][y]);
            if(y !=  max)
                result.add(board[x - 1][y + 1]);
        }
        if(x != max)
        {
            if(y != 0)
                result.add(board[x + 1][y - 1]);
            result.add(board[x + 1][y]);
            if(y != max)
                result.add(board[x + 1][y + 1]);
        }
        if(y != 0)
            result.add(board[x][y - 1]);
        if(y != max)
            result.add(board[x][y + 1]);
        
        return result;
    }

    public void daytime()
    {
        // Initialize ArrayList mustAct
        ArrayList<Organism> mustAct = new ArrayList<Organism>();
        
        // Animals are gathered, then act
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                if(board[i][j].getInhabitant() instanceof Animal)
                {
                    mustAct.add(board[i][j].getInhabitant());
                }
            }
        }
        while(!mustAct.isEmpty())
        {
            mustAct.get(0).live();
            mustAct.remove(0);
        }
        
        // Plants that were not devoured by Herbivores age
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                if(board[i][j].getInhabitant() instanceof Plant)
                    mustAct.add(board[i][j].getInhabitant());
            }
        }
        while(!mustAct.isEmpty())
        {
            mustAct.get(0).live();
            mustAct.remove(0);
        }
        
        // Day count incremented
        turnCount += 1;
    }
        
            
    
    @Override
    public String toString()
    {
        String output = "Day " + turnCount + "\n";
        for(int i = 0; i < GRIDSIZE; i++)
            output += "--";
        output += "\n";
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                if(board[i][j].getInhabitant() instanceof Organism)
                    output += board[i][j].getInhabitant().symbol + " ";
                else
                    output += "  ";
            }
            output += "\n";
        }
        return output;
    }
}

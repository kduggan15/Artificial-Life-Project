import java.util.HashSet;
import java.util.Random;
import java.util.*;

public class Grid
{
    private final int GRIDSIZE = 32;
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
                board[i][j] = new Cell(this, Cell.Terrain.PLAINS);
                Random r = new Random();
                double roll = r.nextInt(100);
                if(r.nextInt(100) < plantChance)
                {
                    board[i][j].setInhabitant(new Plant(i, j));
                }
                else if(r.nextInt(100) < herbivoreChance)
                {

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

    public Cell getCell(int i, int j)
    {
        return board[i][j];
    }

    public void daytime()
    {
        // Initialize ArrayList mustAct
        ArrayList<Organism> mustAct = new ArrayList<Organism>();
        
        // Carnivores are gathered, then act
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                if(board[i][j].getInhabitant() instanceof Carnivore)
                    mustAct.add(board[i][j].getInhabitant());
            }
        }
        while(!mustAct.isEmpty())
        {
            mustAct.get(0).live();
            mustAct.remove(0);
        }
        
        // Herbivores that survived the Carnivore action are gathered, then act
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                if(board[i][j].getInhabitant() instanceof Herbivore)
                    mustAct.add(board[i][j].getInhabitant());
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

import java.util.HashSet;
import java.util.Random;
import java.util.*;

public class Grid
{
    public static final int GRIDSIZE = 16;
    private Cell [][] board = new Cell[GRIDSIZE][GRIDSIZE];
    private int turnCount;
    private boolean paused;
    private int plantChance = 30;
    private int plantDailyChance = 1;
    private int rabbitChance = 10;
    private int foxChance = 5;
    private int wolfChance = 3;

    public Grid()
    {
        int totalSpaces = GRIDSIZE * GRIDSIZE;
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                board[i][j] = new Cell(this, Cell.Terrain.PLAINS, new Location(i,j));
                Random random = new Random();
                if(random.nextInt(100) < plantChance)
                {
                    board[i][j].setInhabitant(new Plant(board[i][j]));
                }
                else if(random.nextInt(100) < rabbitChance)
                {
                    board[i][j].setInhabitant(new Rabbit(board[i][j]));
                }
                else if(random.nextInt(100) < foxChance)
                {
                    board[i][j].setInhabitant(new Fox(board[i][j]));
                }
                else if(random.nextInt(100) < wolfChance)
                {
                    board[i][j].setInhabitant(new Wolf(board[i][j]));
                }
            }
        }
    }

    public boolean allAnimalsHaveDied()
    {
        for(int i = 0; i < GRIDSIZE; i++)
            for(int j = 0; j < GRIDSIZE; j++)
                if(board[i][j].getInhabitant() instanceof Animal)
                    return false;
        return true;
    }

    public void moveAnimal(Cell a, Cell b)
    {
        b.setInhabitant(a.getInhabitant()); // Moves animal's reference from a to b
        a.empty(); // Removes animal's reference from a
    }
    
    public ArrayList<Cell> getAdjacentCells(Cell a)
    {
        int x = a.getLocation().getX();
        int y = a.getLocation().getY();
        int max = GRIDSIZE - 1;
        ArrayList<Cell> result = new ArrayList<Cell>();
        
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

    public void spawnPlants()
    {
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for (int j = 0; j < GRIDSIZE; j++)
            {
                if(board[i][j].getInhabitant() == null)
                {
                    Random random = new Random();
                    if (random.nextInt(100) < plantDailyChance)
                    {
                        board[i][j].setInhabitant(new Plant(board[i][j]));
                    }
                }
            }
        }
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
        for(int i = 0; i < mustAct.size(); i++)
        {
            mustAct.get(i).live();
        }
        mustAct.clear();


        // Plants that were not devoured by Herbivores age
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                if(board[i][j].getInhabitant() instanceof Plant)
                    mustAct.add(board[i][j].getInhabitant());
            }
        }
        for(int i = 0; i < mustAct.size(); i++)
        {
            mustAct.get(i).live();
        }
        mustAct.clear();

        // Spawn new plants
        spawnPlants();
        
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

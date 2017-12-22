import java.util.HashSet;
import java.util.Random;
import java.util.*;

public class Grid
{
    // The size of the square ecosystem.
    public static final int GRIDSIZE = 16;
    private Cell [][] board = new Cell[GRIDSIZE][GRIDSIZE];
    
    // The number of days that have passed.
    private int turnCount;
    private boolean paused;
    
    // The spawn rates of the ecosystem. A balanced ecosystem is a good one!
    private int plantChance = 30;
    private int plantDailyChance = 1;
    private int rabbitChance = 20;
    private int foxChance = 10;
    private int wolfChance = 7;
    private int lionChance = 5;

    // Some fun statistics.
    private int plantsEaten;
    private int rabbitsMunched;
    private int foxesDevoured;
    private int wolvesFeastedOn;

    public void initializeStatistics()
    {
        // Initialize statistics.
        plantsEaten = 0;
        rabbitsMunched = 0;
        foxesDevoured = 0;
        wolvesFeastedOn = 0;
    }

    public Grid()
    {
        initializeStatistics();

        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                // Spawn all cells.
                board[i][j] = new Cell(this, Cell.Terrain.PLAINS, new Location(i,j));
                Random random = new Random();
                
                // Spawn all Organisms.
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
                else if(random.nextInt(100) < lionChance)
                {
                    board[i][j].setInhabitant(new Lion(board[i][j]));
                }
            }
        }
    }

    // Returns true if there are no Animals left.
    public boolean allAnimalsHaveDied()
    {
        for(int i = 0; i < GRIDSIZE; i++)
            for(int j = 0; j < GRIDSIZE; j++)
                if(board[i][j].getInhabitant() instanceof Animal)
                    return false;
        return true;
    }

    // Moves the inhabitant in Cell a to Cell b.
    public void moveAnimal(Cell a, Cell b)
    {
        b.setInhabitant(a.getInhabitant()); // Moves animal's reference from a to b
        a.empty(); // Removes animal's reference from a
    }

    public void updateStatistics(String consumed)
    {
        if(consumed == "Plant")
            plantsEaten += 1;
        else if(consumed == "Rabbit")
            rabbitsMunched += 1;
        else if(consumed == "Fox")
            foxesDevoured += 1;
        else if(consumed == "Wolf")
            wolvesFeastedOn += 1;
    }
    
    // Returns the Cells adjacent to Cell a.
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

    // Spawns new Plants with equal probability at every empty Cell.
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

    // Runs the ecosystem for one day.
    public void daytime()
    {
        // Initialize ArrayList mustAct.
        ArrayList<Organism> mustAct = new ArrayList<Organism>();
        
        // Animals are gathered, then act.
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


        // Plants that were not devoured by Herbivores age.
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

        // Spawn new plants.
        spawnPlants();
        
        // Day count incremented.
        turnCount += 1;
    }

    public String summarizeStatistics()
    {
        String output = "";
        output += (plantsEaten + " plants were eaten.\n");
        output += (rabbitsMunched + " rabbits were munched.\n");
        output += (foxesDevoured + " foxes were devoured.\n");
        output += (wolvesFeastedOn + " wolves were feasted on.\n");
        return output;
    }

    // Returns a String representation of the whole ecosystem.
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

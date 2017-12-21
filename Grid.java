import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;

public class Grid
{
    private final int GRIDSIZE = 32;
    private Organism [][] board = new Organism[GRIDSIZE][GRIDSIZE];
    // private HashSet<GridObject> acted = new HashSet<>();
    private int turnCount;
    private boolean paused;
    
    public void daytime()
    {
        // Initialize ArrayList mustAct
        ArrayList<Organism> mustAct = new ArrayList<>();
        
        // Carnivores are gathered, then act
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                if(board[i][j] instanceof Carnivore)
                    mustAct.add(board[i][j]);
            }
        }
        while(!mustAct.isEmpty())
        {
            mustAct.get(0).act();
            mustAct.remove(0);
        }
        
        // Herbivores that survived the Carnivore action are gathered, then act
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                if(board[i][j] instanceof Herbivore)
                    mustAct.add(board[i][j]);
            }
        }
        while(!mustAct.isEmpty())
        {
            mustAct.get(0).act();
            mustAct.remove(0);
        }
        
        // Plants that were not devoured by Herbivores age
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                if(board[i][j] instanceof Plant)
                    mustAct.add(board[i][j]);
            }
        }
        while(!mustAct.isEmpty())
        {
            mustAct.get(0).act();
            mustAct.remove(0);
        }
        
        // Day count incremented
        turnCount += 1;
    }
        
            
    
    @Override
    public String toString()
    {
        String output = "Day " + turncount + "\n";
        for(int i = 0; i < GRIDSIZE; i++)
            output += "--";
        output += "\n";
        for(int i = 0; i < GRIDSIZE; i++)
        {
            for(int j = 0; j < GRIDSIZE; j++)
            {
                if(board[i][j] instanceof Organism)
                    output += board[i][j].symbol + " ";
                else
                    output += "  ";
            }
            output += "\n";
        }
        return output;
    }
}

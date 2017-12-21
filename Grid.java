package com.company;

import java.util.HashSet;
import java.util.Random;

public class Grid
{
    private final int GRIDSIZE = 32;
    private Organism [][] board = new Organism[GRIDSIZE][GRIDSIZE];
    private HashSet<GridObject> acted = new HashSet<>();
    private int turnCount;
    private boolean paused;
    
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

package com.company;

import java.util.HashSet;
import java.util.Random;

public class Grid
{
    private final int GRIDSIZE = 32;
    private GridObject [][] board = new GridObject[GRIDSIZE][GRIDSIZE];
    private HashSet<GridObject> acted = new HashSet<>();
    private int turnCount;
    private boolean paused;
    
}

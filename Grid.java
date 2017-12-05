package com.company;

import java.util.HashSet;
import java.util.Random;

public class Grid
{
    private GridObject [][] board = new GridObject[16][16];
    private HashSet<GridObject> acted = new HashSet<>();
    private int turnCount;
    private boolean paused;
    
}

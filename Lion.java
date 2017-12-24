import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.*;

public class Lion extends Animal
{
    public Lion(Cell myCell)
    {
        super(myCell);
        initialize();
    }
    
    public Lion(Cell myCell, Cell parent1, Cell parent2)
    {
        super(myCell, parent1, parent2);
        initialize();
    }

    private void initialize()
    {
        image = new Image(getClass().getResourceAsStream("lion.png"));
        energy = 120;
        symbol = 'l';
    }
    
    @Override
    public void filter(ArrayList<Cell> input)
    {
        filterRocks(input);
        filterMountains(input);
        filterAnimals(input);
    }

    @Override
    public Lion makeChild(Cell birthPlace, Cell parent1, Cell parent2)
    {
        return new Lion(birthPlace, parent1, parent2);
    }

    @Override
    public int getAverageLifeSpan()
    {
        return 160;
    }

    @Override
    public int getAverageEnergyToAct()
    {
        return 20;
    }

    @Override
    public int daysStoredBeforeFull()
    {
        return 12;
    }

    @Override
    public int energyFromConsuming(Cell a)
    {
        if(a.getInhabitant() instanceof Wolf)
        {
            return 120;
        }
        else if(a.getInhabitant() instanceof Fox)
        {
            return 80;
        }
        return 0;
    }

    @Override
    public boolean prioritizePrey()
    {
        if(energy <= energyToAct * 18)
            return true;
        return false;
    }
}


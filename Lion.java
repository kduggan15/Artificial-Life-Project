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
        energy = 80;
        symbol = 'l';
    }

    @Override
    public Lion makeChild(Cell birthPlace, Cell parent1, Cell parent2)
    {
        return new Lion(birthPlace, parent1, parent2);
    }

    @Override
    public int getAverageLifeSpan()
    {
        return 80;
    }

    @Override
    public int getAverageEnergyToAct()
    {
        return 20;
    }

    @Override
    public int energyFromConsuming(Cell a)
    {
        if(a.getInhabitant() instanceof Wolf)
        {
            return 40;
        }
        else if(a.getInhabitant() instanceof Fox)
        {
            return 30;
        }
        else if(a.getInhabitant() instanceof Rabbit)
        {
            return 20;
        }
        return 0;
    }
}


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.*;

public class Wolf extends Animal
{
    public Wolf(Cell myCell)
    {
        super(myCell);
        initialize();
    }
    
    public Wolf(Cell myCell, Cell parent1, Cell parent2)
    {
        super(myCell, parent1, parent2);
        initialize();
    }

    private void initialize()
    {
        image = new Image(getClass().getResourceAsStream("wolf.png"));
        energy = 60;
        symbol = 'w';
    }
    
    @Override
    public Wolf makeChild(Cell birthPlace, Cell parent1, Cell parent2)
    {
        return new Wolf(birthPlace, parent1, parent2);
    }

    @Override
    public int getAverageLifeSpan()
    {
        return 60;
    }

    @Override
    public int getAverageEnergyToAct()
    {
        return 10;
    }

    @Override
    public int energyFromConsuming(Cell a)
    {
        if(a.getInhabitant() instanceof Fox)
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

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.*;

public class Fox extends Animal
{
    public Fox(Cell myCell)
    {
        super(myCell);
        initialize();
    }
    
    public Fox(Cell myCell, Cell parent1, Cell parent2)
    {
        super(myCell, parent1, parent2);
        initialize();
    }

    private void initialize()
    {
        image = new Image(getClass().getResourceAsStream("fox.png"));
        energy = 100;
        symbol = 'f';
    }
    
    @Override
    public Fox makeChild(Cell birthPlace, Cell parent1, Cell parent2)
    {
        return new Fox(birthPlace, parent1, parent2);
    }

    @Override
    public int getAverageLifeSpan()
    {
        return 100;
    }

    @Override
    public int getAverageEnergyToAct()
    {
        return 10;
    }

    @Override
    public int daysStoredBeforeFull()
    {
        return 6;
    }

    @Override
    public int energyFromConsuming(Cell a)
    {
        if(a.getInhabitant() instanceof Rabbit)
        {
            return 40;
        }
        return 0;
    }

    @Override
    public boolean prioritizePrey()
    {
        if(energy <= energyToAct * 4)
            return true;
        return false;
    }
}

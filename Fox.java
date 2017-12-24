import javafx.scene.canvas.GraphicsContext;
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
        energy = 40;
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
        return 40;
    }

    @Override
    public int getAverageEnergyToAct()
    {
        return 10;
    }

    @Override
    public int energyFromConsuming(Cell a)
    {
        if(a.getInhabitant() instanceof Rabbit)
        {
            return 20;
        }
        return 0;
    }

    @Override
    public void drawMyself(GraphicsContext gc, int x, int y)
    {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(x, y, x+16, y+16);
    }
}

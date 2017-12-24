import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.util.*;

public class Rabbit extends Animal
{
    //protected final char symbol = '!';
    public Rabbit(Cell myCell)
    {
        super(myCell);
        initialize();
    }

    public Rabbit(Cell myCell, Cell parent1, Cell parent2)
    {
        super(myCell, parent1, parent2);
        initialize();
    }

    private void initialize()
    {
        energy = 20;
        symbol = '!';
    }

    @Override
    public int getAverageLifeSpan()
    {
        return 40;
    }

    @Override
    public int getAverageEnergyToAct()
    {
        return 4;
    }

    @Override
    public int energyFromConsuming(Cell a)
    {
        if(a.getInhabitant() instanceof Plant)
        {
            return 20;
        }
        return 0;
    }

    @Override
    public Rabbit makeChild(Cell birthPlace, Cell parent1, Cell parent2)
    {
        return new Rabbit(birthPlace, parent1, parent2);
    }

    @Override
    public void drawMyself(GraphicsContext gc, int x, int y, int cellSize)
    {
        Image image = new Image(getClass().getResourceAsStream("rabbit.jpg"));
        gc.drawImage(image, x, y, cellSize, cellSize);
    }
}

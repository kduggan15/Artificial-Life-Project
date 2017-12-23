import java.util.*;

public class Fox extends Animal
{
    public Fox(Cell myCell)
    {
        super(myCell);
        energy = 40;
        symbol = 'f';
    }
    
    public Fox(Cell myCell, Cell parent1, Cell parent2)
    {
        super(myCell, parent1, parent2);
    }
    
    @Override
    public Fox makeChild(Cell birthPlace, Cell parent1, Cell parent2)
    {
        return new Fox(birthPlace, parent1, parent2);
    }

    @Override
    public void filter(ArrayList<Cell> input)
    {
        filterRocks(input);
        filterMountains(input);
        filterOrganisms(input);
    }

    @Override
    public int getAverageLifeSpan()
    {
        return 40;
    }

    @Override
    public int getAverageEnergyToAct()
    {
        return 6;
    }

    @Override
    public int energyFromConsuming(Cell a)
    {
        if(a.getInhabitant() instanceof Rabbit)
        {
            return 30;
        }
        return 0;
    }

    @Override
    public boolean readyToMate()
    {
        return true;
    }
}

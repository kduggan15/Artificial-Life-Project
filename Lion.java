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
        energy = 80;
        symbol = 'l';
    }

    @Override
    public Lion makeChild(Cell birthPlace, Cell parent1, Cell parent2)
    {
        return new Lion(birthPlace, parent1, parent2);
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
        return 0;
    }
}


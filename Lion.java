import java.util.*;

public class Lion extends Animal
{
    public Lion(Cell myCell)
    {
        super(myCell);
        energy = 80;
        symbol = 'l';
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

    @Override
    public boolean readyToMate()
    {
        return true;
    }
}

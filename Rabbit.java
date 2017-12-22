import java.util.*;

public class Rabbit extends Animal
{
    public Rabbit(Cell myCell)
    {
        super(myCell);
        energy = 20;
        symbol = '!';
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
    public boolean readyToMate()
    {
        return true;
    }
}
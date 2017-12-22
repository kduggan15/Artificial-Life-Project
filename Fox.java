import java.util.*;

public class Fox extends Animal
{
    public Fox(Cell myCell)
    {
        super(myCell);
        energy = 40;
        symbol = 'f';
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
            System.out.println("A fox is going to eat a rabbit!");
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

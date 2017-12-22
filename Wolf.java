import java.util.*;

public class Wolf extends Animal
{
    public Wolf(Cell myCell)
    {
        super(myCell);
        energy = 60;
        symbol = 'w';
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
            System.out.println("A wolf is going to eat a fox!");
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

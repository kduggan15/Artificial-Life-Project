import java.util.*;

public class Rabbit extends Animal
{
    public Rabbit(Cell myCell)
    {
        super(myCell);
        energy = 20;
        symbol = '!';
    }

    public Rabbit(Cell myCell, Animal parent1, Animal parent2)
    {
        super(myCell,parent1,parent2);
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

    @Override
    public Rabbit makeChild(Cell theMate, Cell Loc)
    {
        return new Rabbit(Loc, this, (Rabbit)theMate.getInhabitant());//Weird BS to make it so child constructors are used
    }
}
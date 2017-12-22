import java.util.*;

public abstract class Animal extends Organism
{
    protected final DNA genetics;

    public Animal(Cell myCell)
    {
        super(myCell);

        // Randomly generated DNA
        genetics = new DNA();
        lifeSpan = (int) (getAverageLifeSpan() * genetics.getLifeSpanMultiplier());
        energyToAct = (int) (getAverageEnergyToAct() * genetics.getDailyEnergyMultiplier());
    }

    public Animal(Cell myCell, Animal parent1, Animal parent2)
    {
        super(myCell);
        genetics = new DNA(parent1.getDNA(), parent2.getDNA());
        lifeSpan = (int) (getAverageLifeSpan() * genetics.getLifeSpanMultiplier());
        energyToAct = (int) (getAverageEnergyToAct() * genetics.getDailyEnergyMultiplier());
    }

    public abstract int getAverageLifeSpan();
    public abstract int getAverageEnergyToAct();
    public abstract int energyFromConsuming(Cell a);
    public abstract boolean readyToMate();

    public DNA getDNA()
    {
        return genetics;
    }

    public void act()
    {
        energy -= energyToAct;
        ArrayList<Cell> surroundings = myCell.getMyGrid().getAdjacentCells(myCell);
        ArrayList<Cell> nearbyPrey = isolatePrey(surroundings);

        /*
        ArrayList<Cell> nearbyMates = isolateMates(surroundings);
        if(readyToMate() && nearbyMates.size() > 0)
        {
            // Reproduce
        }
        */

        filter(surroundings);
        if(nearbyPrey.size() != 0 || surroundings.size() != 0)
        {
            Random random = new Random();
            Cell chosen;
            if(nearbyPrey.size() == 0)
                chosen = surroundings.get(random.nextInt(surroundings.size()));
            else
            {
                chosen = nearbyPrey.get(random.nextInt(nearbyPrey.size()));
            }
            energy += energyFromConsuming(chosen);
            myCell.getMyGrid().moveAnimal(myCell, chosen);
            myCell = chosen;
        }
    }

    public void filter(ArrayList<Cell> input)
    {
        filterRocks(input);
        filterOrganisms(input);
    }

    public ArrayList<Cell> isolateMates(ArrayList<Cell> input)
    {
        ArrayList<Cell> nearbyMates = new ArrayList<Cell>();
        for(int i = 0; i < input.size(); i++)
        {
            if(this.getClass().getName().equals( input.get(i).getInhabitant().getClass().getName() ))
            {
                nearbyMates.add(input.get(i));
            }
        }
        return nearbyMates;
    }

    public Cell chooseMate(ArrayList<Organism> input)
    {
        int chosenIndex = 0;
        for(int i = 1; i < input.size(); i++)
        {
            if(((Animal)input.get(i)).getDNA().getDNAStrength() > ((Animal)input.get(i)).getDNA().getDNAStrength())
                chosenIndex = i;
        }
        return input.get(chosenIndex).myCell;
    }

    public ArrayList<Cell> isolatePrey(ArrayList<Cell> input)
    {
        ArrayList<Cell> inputCopy = (ArrayList<Cell>) input.clone();
        for(int i = 0; i < inputCopy.size(); i++)
        {
            if(energyFromConsuming(inputCopy.get(i)) == 0)
            {
                inputCopy.remove(i);
                i--;
            }
        }
        return inputCopy;
    }

    public void filterRocks(ArrayList<Cell> input)
    {
        for(int i = 0; i < input.size(); i++)
        {
            if(input.get(i).getTerrain() == Cell.Terrain.ROCKS)
            {
                input.remove(i);
                i--;
            }
        }
    }

    public void filterMountains(ArrayList<Cell> input)
    {
        for(int i = 0; i < input.size(); i++)
        {
            if(input.get(i).getTerrain() == Cell.Terrain.MOUNTAINS)
            {
                input.remove(i);
                i--;
            }
        }
    }

    public void filterOrganisms(ArrayList<Cell> input)
    {
        for(int i = 0; i < input.size(); i++)
        {
            if(input.get(i).getInhabitant() instanceof Organism)
            {
                input.remove(i);
                i--;
            }
        }
    }

    public void filterAnimals(ArrayList<Cell> input)
    {
        for(int i = 0; i < input.size(); i++)
        {
            if(input.get(i).getInhabitant() instanceof Animal)
            {
                input.remove(i);
                i--;
            }
        }
    }
}
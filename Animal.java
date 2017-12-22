import java.util.*;

public abstract class Animal extends Organism
{
    protected final DNA genetics;

    // Creates a new Animal with randomly generated DNA.
    public Animal(Cell myCell)
    {
        super(myCell);
        
        genetics = new DNA();
        lifeSpan = (int) (getAverageLifeSpan() * genetics.getLifeSpanMultiplier());
        energyToAct = (int) (getAverageEnergyToAct() * genetics.getDailyEnergyMultiplier());
    }

    // Creates a new Animal with DNA that is based on its parents' DNA.
    public Animal(Cell myCell, Animal parent1, Animal parent2)
    {
        super(myCell);
        genetics = new DNA(parent1.getDNA(), parent2.getDNA());
        lifeSpan = (int) (getAverageLifeSpan() * genetics.getLifeSpanMultiplier());
        energyToAct = (int) (getAverageEnergyToAct() * genetics.getDailyEnergyMultiplier());
    }

    // Abstract methods that must be implemented by all subclass Animals.
    public abstract int getAverageLifeSpan();
    public abstract int getAverageEnergyToAct();
    public abstract int energyFromConsuming(Cell a);
    public abstract boolean readyToMate();

    // Returns the DNA object of this Animal.
    public DNA getDNA()
    {
        return genetics;
    }

    // Determines what this Animal will do.
    public void act()
    {
        // Depletes its energy.
        energy -= energyToAct;
        
        // Gathers surroundings Cells.
        ArrayList<Cell> surroundings = myCell.getMyGrid().getAdjacentCells(myCell);
        
        // Gathers nearby prey.
        ArrayList<Cell> nearbyPrey = isolatePrey(surroundings);

        /*
        ArrayList<Cell> nearbyMates = isolateMates(surroundings);
        if(readyToMate() && nearbyMates.size() > 0)
        {
            // Reproduce
        }
        */

        // Filters the surrounding Cells, removing all non-empty spaces.
        filter(surroundings);
        
        // If there is any possible Cell to move to...
        if(nearbyPrey.size() != 0 || surroundings.size() != 0)
        {
            Random random = new Random();
            Cell chosen;
            
            // If there are no nearby empty Cells to move to, or this Animal is hungry, choose a nearby prey to consume.
            if(surroundings.size() == 0 || prioritizePrey())
            {
                chosen = nearbyPrey.get(random.nextInt(nearbyPrey.size()));
            }
            else
            {
                chosen = surroundings.get(random.nextInt(surroundings.size()));
            }
            
            // If the chosen Cell contained prey, add the appropriate amount of energy to this predator.
            energy += energyFromConsuming(chosen);
            chosen.empty();
            myCell.getMyGrid().moveAnimal(myCell, chosen);
            myCell = chosen;
        }
    }
    
    // Returns true if the Animal is at risk of dying within 3 days of not finding food.
    public boolean prioritizePrey()
    {
        if(energy <= energyToAct * 3)
            return true;
        return false;
    }

    // Removes all non-empty Cells.
    public void filter(ArrayList<Cell> input)
    {
        filterRocks(input);
        filterOrganisms(input);
    }

    // Isolates any nearby Animals that are of the same type.
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
    
    // Returns the Animal with the strongest DNA.
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

    // Removes nearby Cells that don't contain prey.
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

    // Removes nearby Cells that contain rocks.
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

    // Removes nearby Cells that contain mountains.
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

    // Removes nearby Cells that contain inhabitants.
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

    // Removes nearby Cells that contain inhabitants that are Animals.
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

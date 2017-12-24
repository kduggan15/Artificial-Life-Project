import javafx.scene.canvas.GraphicsContext;

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
    public Animal(Cell birthPlace, Cell parent1, Cell parent2)
    {
        super(birthPlace);
        genetics = new DNA(((Animal)(parent1.getInhabitant())).getDNA(), ((Animal)(parent2.getInhabitant())).getDNA());
        lifeSpan = (int) (getAverageLifeSpan() * genetics.getLifeSpanMultiplier());
        energyToAct = (int) (getAverageEnergyToAct() * genetics.getDailyEnergyMultiplier());
    }

    // Abstract methods that must be implemented by all subclass Animals.
    public abstract int getAverageLifeSpan();
    public abstract int getAverageEnergyToAct();
    public abstract int energyFromConsuming(Cell a);
    public abstract Animal makeChild(Cell birthPlace, Cell parent1, Cell parent2);

    public boolean isFull()
    {
        if(energy > energyToAct * 5)
            return true;
        return false;
    }

    // Returns true if this Animal is ready to mate.
    public boolean readyToMate()
    {
        if(energy > energyToAct * 3 && age > lifeSpan / 5)
        {
            return true;
        }
        return false;
    }
    
    // Returns the DNA object of this Animal.
    public DNA getDNA()
    {
        return genetics;
    }

    // Determines what this Animal will do.
    public void act()
    {
        if(!alive) return;
        // Depletes its energy.
        energy -= energyToAct;
        
        // Gathers surroundings Cells.
        ArrayList<Cell> surroundings = myCell.getMyGrid().getAdjacentCells(myCell);
        
        // Gathers nearby prey.
        ArrayList<Cell> nearbyPrey = isolatePrey(surroundings);
        
        // Reproduction
        if(readyToMate())
        {
            // Gathers nearby Animals of the same type.
            ArrayList<Cell> nearbyMates = isolateMates(surroundings);
            ArrayList<Cell> possibleBirthplaces = myCell.getMyGrid().getAdjacentCells(myCell);
            filter(possibleBirthplaces);
            if(nearbyMates.size() > 0)
            {
                filter(surroundings);
                if(surroundings.size() > 0) // Is there a place to put the baby?
                {
                    Random random = new Random();
                    Cell myMate = chooseMate(nearbyMates);
                    Cell chosenBirthplace = surroundings.get(random.nextInt(surroundings.size()));
                    chosenBirthplace.setInhabitant(makeChild(chosenBirthplace, myCell, myMate));
                    return;
                }
            }
        }

        // Filters the surrounding Cells, removing all non-empty spaces.
        filter(surroundings);
        
        // If there is any possible Cell to move to...
        if( ( (nearbyPrey.size() != 0) && !isFull() ) || surroundings.size() != 0)
        {
            Random random = new Random();
            Cell chosen;
            
            // If there are no nearby empty Cells to move to, or this Animal is hungry, choose a nearby prey to consume.
            if( (surroundings.size() == 0) || (nearbyPrey.size() != 0 && prioritizePrey()) )
            {
                chosen = chooseBestPrey(nearbyPrey);
            }
            else
            {
                chosen = surroundings.get(random.nextInt(surroundings.size()));
            }
            
            // Record statistical data.
            if(energyFromConsuming(chosen) != 0)
            {
                myCell.getMyGrid().updateStatistics(chosen.getInhabitant().getClass().getName());
                energy += energyFromConsuming(chosen);
                chosen.getInhabitant().die();
            }
            chosen.empty();
            myCell.getMyGrid().moveAnimal(myCell, chosen);
            myCell = chosen;
        }
    }

    public Cell chooseBestPrey(ArrayList<Cell> nearbyPrey)
    {
        Cell chosen = nearbyPrey.get(0);
        for(int i = 0; i < nearbyPrey.size(); i++)
        {
            if(energyFromConsuming(nearbyPrey.get(i)) > energyFromConsuming(chosen))
            {
                chosen = nearbyPrey.get(i);
            }
        }
        return chosen;
    }
    
    // Returns true if the Animal is at risk of dying within 3 days of not finding food, even if there is no nearby food.
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
        filterMountains(input);
        filterOrganisms(input);
    }

    // Isolates any nearby Animals that are of the same type.
    public ArrayList<Cell> isolateMates(ArrayList<Cell> input)
    {
        ArrayList<Cell> nearbyMates = new ArrayList<Cell>();
        for(int i = 0; i < input.size(); i++)
        {
            if (input.get(i).getInhabitant()!=null)//stop from checking null spaces
                if(this.getClass().equals(input.get(i).getInhabitant().getClass()))
                {
                    // Ensure the mate is ready.
                    if(( (Animal)(input.get(i).getInhabitant()) ).readyToMate())
                        nearbyMates.add(input.get(i));
                }
        }
        return nearbyMates;
    }
    
    // Returns the Animal with the strongest DNA.
    public Cell chooseMate(ArrayList<Cell> input)//Changed the arraylist to type Cell because that is more consistent with program
    {
        int chosenIndex = 0;
        for(int i = 1; i < input.size(); i++)
        {
            if(((Animal)input.get(i).getInhabitant()).getDNA().computeDNAStrength() > 
            ((Animal)input.get(i).getInhabitant()).getDNA().computeDNAStrength())
                chosenIndex = i;
        }
        return input.get(chosenIndex);
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

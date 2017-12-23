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
        genetics = new DNA((Animal)(parent1.getInhabitant()).getDNA(), (Animal)(parent2.getInhabitant()).getDNA());
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
        ArrayList<Cell> surroundings = myCell.getMyGrid().getAdjacentCells(myCell);//TODO:This structure is bad, but whatever
        
        // Gathers nearby prey.
        ArrayList<Cell> nearbyPrey = isolatePrey(surroundings);
        
        // Reproduction
        if(readyToMate()) // TODO:Adding a chance to breed might be good
        {
            // Gathers nearby Animals of the same type.
            ArrayList<Cell> nearbyMates = isolateMates(surroundings);
            if(nearbyMates.size() > 0)
            {
                filter(surroundings);
                if(surroundings.size() > 0) // Is there a place to put the baby?
                {
                    Random random = new Random();
                    Cell myMate = chooseMate(nearbyMates);
                    Cell chosenBirthplace = surroundings.get(random.nextInt(surroundings.size()));
                    breed(myMate); //TODO:Adding a chance to breed might be good
                    return;
                }
            }
        }

        // Filters the surrounding Cells, removing all non-empty spaces.
        filter(surroundings);
        
        // If there is any possible Cell to move to...
        if(nearbyPrey.size() != 0 || surroundings.size() != 0)
        {
            Random random = new Random();
            Cell chosen;
            
            // If there are no nearby empty Cells to move to, or this Animal is hungry, choose a nearby prey to consume.
            if(surroundings.size() == 0 || (nearbyPrey.size() != 0 && prioritizePrey()))
            {
                chosen = nearbyPrey.get(random.nextInt(nearbyPrey.size()));
            }
            else
            {
                chosen = surroundings.get(random.nextInt(surroundings.size()));
            }
            
            // Record statistical data.
            if(energyFromConsuming(chosen) != 0)
            {
                myCell.getMyGrid().updateStatistics(chosen.getInhabitant().getClass().getName());
            }

            // If the chosen Cell contained prey, add the appropriate amount of energy to this predator.
            energy += energyFromConsuming(chosen);
            chosen.empty();
            myCell.getMyGrid().moveAnimal(myCell, chosen);
            myCell = chosen;
        }
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
        filterOrganisms(input);
    }

    // Isolates any nearby Animals that are of the same type.
    public ArrayList<Cell> isolateMates(ArrayList<Cell> input)
    {
        ArrayList<Cell> nearbyMates = new ArrayList<Cell>();
        for(int i = 0; i < input.size(); i++)
        {
            //this.getClass();
            //System.out.println(""+i+"/"+input.size());
            if (input.get(i).getInhabitant()!=null)//stop from checking null spaces
                if(this.getClass().equals(input.get(i).getInhabitant().getClass()))
                //if(input.get(i) instanceof this.getClass())
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
            if(((Animal)input.get(i).getInhabitant()).getDNA().computeDNAStrength() > ((Animal)input.get(i).getInhabitant()).getDNA().computeDNAStrength())
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

    public void filterOccupied(ArrayList<Cell> input)
    {
        for(int i=0; i<input.size();i++)
        {
            if(!input.get(i).isValidMove()) input.remove(i);
        }
    }

    public void breed(Cell theMate)
    {
        ArrayList<Cell> surroundings = myCell.getMyGrid().getAdjacentCells(myCell);
        filterOccupied(surroundings);
        //Animal child = new Animal(surroundings.get(0), this, theMate.getInhabitant())
        surroundings.get(0).setInhabitant(this.makeChild(theMate,surroundings.get(0)));
    }

    //TODO: This must be overwritten in each child class to allow for inheritance
    public Animal makeChild(Cell theMate, Cell Loc)
    {
        //return new Animal(Loc, this, theMate.getInhabitant());//Weird BS to make it so child constructors are used
        return this;//This does not work, but it shouldn't be used anyway
    }
}

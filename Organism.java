public abstract class Organism {
    protected Cell myCell;
    protected int energy;
    protected int energyToAct;
    protected int age;
    protected char symbol;
    protected int lifeSpan;
    protected boolean alive;

    // Initializes an Organism that is aware of which Cell it exists in.
    public Organism(Cell myCell)
    {
        alive = true;
        age = 0;
        this.myCell = myCell;
    }

    // If this Organism hasn't died, calls the act method.
    public void live()
    {
        if(age > lifeSpan || energy < 1)
        {
            alive = false;
            die();
            return;
        }
        age += 1;
        if(myCell.getInhabitant() != null)
            act();
    }
    
    public abstract void act();
    
    // Calls the empty() method of this Organism's Cell, removing its reference from the Cell.
    public void die()
    {
        alive=false;
        myCell.empty();
    }

    public boolean isAlive()
    {
        return alive;
    }
}

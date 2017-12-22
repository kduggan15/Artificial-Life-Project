public abstract class Organism {
    protected Cell myCell;
    protected int energy;
    protected int age;
    protected char symbol;
    
    protected int lifeSpan;
    protected boolean alive;

    public Organism(Cell myCell)
    {
        alive = true;
        this.myCell = myCell;
    }

    public void live()
    {
        if(alive)
        {
            act();
        }
    }
    public abstract void act();
    public void die()
    {
        myCell.setInhabitant(null);
    }
}

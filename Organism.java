public abstract class Organism {
    protected int energy;
    protected int age;
    protected char symbol;
    protected Cell myCell;
    protected Location myLocation;
    protected int lifeSpan;
    protected boolean alive;
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

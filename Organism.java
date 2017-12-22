public abstract class Organism {
    protected Cell myCell;
    protected int energy;
    protected int energyToAct;
    protected int age;
    protected char symbol;
    protected int lifeSpan;
    protected boolean alive;

    public Organism(Cell myCell)
    {
        alive = true;
        age = 0;
        this.myCell = myCell;
    }

    public void live()
    {
        if(age > lifeSpan || energy < 1)
        {
            die();
            return;
        }
        age += 1;
        act();
    }
    public abstract void act();
    public void die()
    {
        myCell.empty();
    }
}
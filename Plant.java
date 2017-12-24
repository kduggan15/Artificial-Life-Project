import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Plant extends Organism
{
    public Plant(Cell myCell)
    {
        super(myCell);
        age = 0;
        energy = 20;
        symbol = '#';
        lifeSpan = 20;
        image = new Image(getClass().getResourceAsStream("plant.png"));
    }

    public void act()
    {

    }
}

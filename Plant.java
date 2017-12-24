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
    }

    public void act()
    {

    }

    @Override
    public void drawMyself(GraphicsContext gc, int x, int y, int cellSize)
    {
        Image image = new Image(getClass().getResourceAsStream("plant.png"));
        gc.drawImage(image, x, y, cellSize, cellSize);
    }
}

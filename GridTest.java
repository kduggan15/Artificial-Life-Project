import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
//import javafx.*;



public class GridTest extends Application {
    final private int CANVAS_SIZE = 512;
    Grid world;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        world = new Grid();

        stage.setTitle("Artificial Life Project by Vagan Grigoryan, Ricky Valdes, Kieran Duggan");

        BorderPane border = new BorderPane();

        VBox vbox = new VBox();//create and configure vbox
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);


        Canvas canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawGrid(gc);


        Button buttonNext = new Button("Next Day");//create a button
        buttonNext.setPrefSize(100, 20);
        buttonNext.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                updateWorld(gc);
            }
        });
        vbox.getChildren().addAll(buttonNext);//Add a button to vbox

        border.setRight(vbox);//put the vbox in the borderpane




        border.setCenter(canvas);//put the canvas in the stage

        Scene scene = new Scene(border);
        stage.setScene(scene);
        stage.setTitle("Layout Sample");
        stage.show();
    }

    private void updateWorld(GraphicsContext gc)
    {
        world.daytime();
        gc.clearRect(0,0,CANVAS_SIZE,CANVAS_SIZE);
        drawGrid(gc);
        System.out.println(world.toString());
    }

    private void drawGrid(GraphicsContext gc)
    {
        final int SIZE_OF_CELL = CANVAS_SIZE/world.GRIDSIZE;
        int effectiveX;
        int effectiveY;
        Organism thisOrg;
        Cell thisCell;
        for(int i=0; i<world.GRIDSIZE*world.GRIDSIZE;i++)
        {

            effectiveX=(i*(SIZE_OF_CELL))%CANVAS_SIZE;
            effectiveY=((i/world.GRIDSIZE)*SIZE_OF_CELL);
            thisCell = world.getCell(i%world.GRIDSIZE,i/world.GRIDSIZE);
            thisOrg = world.getCell(i%world.GRIDSIZE,i/world.GRIDSIZE).getInhabitant();

            thisCell.drawTerrain(gc,effectiveX,effectiveY,SIZE_OF_CELL);
            if(thisOrg!= null)
                thisOrg.drawMyself(gc,effectiveX,effectiveY,SIZE_OF_CELL);
        }
    }
}


/*
public class GridTest extends
{
    public static void main(String[] args)
    {
        Grid world = new Grid();
        while(!world.allAnimalsHaveDied())
        {
            System.out.println(world.toString());
            world.daytime();
        }
        System.out.println(world.summarizeStatistics());
    }
}
*/

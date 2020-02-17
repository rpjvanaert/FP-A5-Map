import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;

public class MapLoaderMain extends Application {

    private TiledMap tiledMap;
    private ResizableCanvas canvas;

    @Override
    public void start(Stage stage) {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
//                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("MApppppie");
        stage.show();
        draw(graphics);
    }

    public void init() {
        this.tiledMap = new TiledMap();
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setBackground(Color.black);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        tiledMap.draw(graphics);
    }

    public void update(double deltaTime) {

    }

    public static void main(String[] args) {
        launch(MapLoaderMain.class);
    }

}
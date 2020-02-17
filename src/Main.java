import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;

public class Main extends Application {
    private TiledMap tiledMap;
    private ResizableCanvas canvas;

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage stage) {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(this::draw, mainPane);
        mainPane.setCenter(canvas);

        tiledMap = new TiledMap();

        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) last = now;

                update((now - last) / 1000000000.0);
                last = now;
                draw(graphics);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("A5 FP");
        stage.show();

        draw(graphics);
    }

    public void init() {

    }

    public void draw(FXGraphics2D g) {
        g.setBackground(Color.black);
        g.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        tiledMap.draw(g);
    }

    public void update(double deltaTime) {

    }
}
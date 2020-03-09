import DistanceMapLogic.DistanceMap;
import DistanceMapLogic.TargetArea;
import DistanceMapLogic.WalkableMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Point2D;

public class Main extends Application {
    private TiledMap tiledMap;
    private ResizableCanvas canvas;

    public static void main(String[] args) {
        launch(Main.class);
//        Boolean[][] walkableMap = new Boolean[100][100];
//
//        for (int i = 0; i < 100; i++) {
//            for (int j = 0; j < 100; j++) {
//                walkableMap[i][j] = true;
//            }
//        }
//
//        DistanceMap distanceMap = new DistanceMap("Map", new TargetArea(new Point2D.Double(0, 0), new Point2D.Double(5, 5)), new WalkableMap(walkableMap));
//
//            for (int i = 0; i < 50; i++) {
//                System.out.println();
//                for (int j = 0; j < 50; j++) {
//                    System.out.print(distanceMap.getMap()[i][j] + ",");
//                }
//            }

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
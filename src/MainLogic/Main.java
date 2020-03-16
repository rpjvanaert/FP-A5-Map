package MainLogic;

import NPCLogic.DistanceMap;
import NPCLogic.Person;
import MapData.TargetArea;
import MapData.WalkableMap;
import MapData.TiledMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Main extends Application {
    private TiledMap tiledMap;
    private ResizableCanvas canvas;
    private ArrayList<Person> people;

    private int peopleAmount = 30;
    private int stageAmount = 6;
    private int toiletAmount = 20;
    private int globalSpeed = 4;
    private CameraTransform cameraTransform;
    private boolean predictedGuests = true;
    private ArrayList<Integer> Prediction = new ArrayList<>();

    private static NPCLogic.DistanceMap[] distanceMaps;
    public static void main(String[] args) {
        launch(Main.class);
        Boolean[][] walkableMap = new Boolean[100][100];

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                walkableMap[i][j] = true;
            }
        }

        NPCLogic.DistanceMap distanceMap = new DistanceMap("Map", new TargetArea(new Point2D.Double(0, 0), new Point2D.Double(5, 5)), new WalkableMap(walkableMap));

//        for (int i = 0; i < 100; i++) {
//            System.out.println();
//            for (int j = 0; j < 100; j++) {
//                System.out.print(distanceMap.getMap()[i][j] + ",");
//            }
//        }

    }

    @Override
    public void start(Stage stage) {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(this::draw, mainPane);
        mainPane.setCenter(canvas);

        tiledMap = new TiledMap();

        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        this.cameraTransform = new CameraTransform(canvas);
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
        canvas.setOnMouseClicked(e -> {
            clickAction(e);
            if (e.getButton() == MouseButton.PRIMARY){
                this.init();
            }
        });
        stage.setScene(new Scene(mainPane));
        stage.setTitle("A5 FP");
        stage.show();

        draw(graphics);
    }

    public void update(double frameTime) {
        for (Person person : people) {
            person.update(people);
        }
    }

    /**
     * Spawns a amount of people, stops spawning after 10% failed spawnAttempts of the amount
     * @param amount the amount of people to be spawned
     */
    public void spawnPeople(int amount) {
        int failedSpawnAttempts = 0;

        for (int i = 0; i < amount; i++) {

            Point2D newSpawnLocation = new Point2D.Double(Math.random() * 100 * 32, Math.random() * 100  * 32);
            if (canSpawn(newSpawnLocation)) {
                this.people.add(new Person(new Point2D.Double(newSpawnLocation.getX(),
                        newSpawnLocation.getY() ), this.Prediction, this.globalSpeed));
                failedSpawnAttempts = 0;
            } else {
                failedSpawnAttempts++;
                i--;
                if (failedSpawnAttempts > amount * 0.1) {
                    return;
                }
            }
        }
    }

    /**
     * A method that checks if a spot is not occupied by another person
     * @param spawnPosition the location to check if it's available
     * @return true if empty, false if occupied
     */
    public boolean canSpawn(Point2D spawnPosition) {
        if (this.people.size() <= 0) {
            return true;
        }

        for (Person person : people) {
            if (spawnPosition.distance(person.getPosition()) <= 64) {
                return false;
            }
//            if(!NPCLogic.PathCalculator.isWalkable(spawnPosition)){
//                return false;
//            }
        }

        return true;
    }

    public void clickAction(MouseEvent e) {
        for (Person person : this.people) {
            if (person.getPosition().distance(new Point2D.Double(e.getX(), e.getY())) < 32) {
                person.playSoundEffect();
            }
        }
    }

    public void createPredictions() {
        int Total = 6;
        int metal = 1;
        int Country = 1;
        int classic = 1;
        int Rap = 1;
        int Pop = 1;
        int electro = 1;

        this.Prediction.add(metal);
        this.Prediction.add(classic);
        this.Prediction.add(Country);
        this.Prediction.add(Rap);
        this.Prediction.add(Pop);
        this.Prediction.add(electro);
        this.Prediction.add(Total);
    }

    public void init()  {
        this.people = new ArrayList<>();
        this.distanceMaps = new DistanceMap[stageAmount + toiletAmount];

        // initialing DistanceMap

        Boolean[][] walkableMap = new Boolean[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                walkableMap[i][j] = true;
            }
        }
        for (int i = 33; i < 66; i++) {
            for (int j = 33; j < 66; j++) {
                walkableMap[i][j] = false;
            }
        }

        WalkableMap wMap = new WalkableMap(walkableMap);
        DistanceMap testMap1 = new DistanceMap("TestMap1", new TargetArea(new Point2D.Double(80,20), (new Point2D.Double(30,10)) ),wMap);
        DistanceMap testMap2 = new DistanceMap("TestMap2", new TargetArea(new Point2D.Double(20,80), new Point2D.Double(0,20)), wMap);
        DistanceMap testMap3 = new DistanceMap("TestMap3", new TargetArea(new Point2D.Double(80,80), new Point2D.Double(50,50)), wMap);
        distanceMaps = new DistanceMap[]{testMap1,testMap2,testMap3};

        createPredictions();
        spawnPeople(peopleAmount);
            }

    public void draw(FXGraphics2D g) {
        g.setTransform(this.cameraTransform.getTransform());
        AffineTransform inverse = this.cameraTransform.getInverseTransform();
        g.clearRect(
                (int)inverse.getTranslateX(),
                (int)inverse.getTranslateY(),
                (int)(inverse.getScaleX() * this.canvas.getWidth() - inverse.getTranslateX()),
                (int)(inverse.getScaleY() * this.canvas.getHeight() - inverse.getTranslateY())
        );
        g.setBackground(Color.black);
        tiledMap.draw(g);
        for (Person person : people) {
            person.draw(g);
        }
    }

    public static NPCLogic.DistanceMap[] getDistanceMaps() {
        return distanceMaps;
    }

    public void setPeopleAmount(int peopleAmount) {
        this.peopleAmount = peopleAmount;
    }

    public int getGlobalSpeed() {
        return globalSpeed;
    }

    public int getPeopleAmount() {
        return peopleAmount;
    }

    public void setGlobalSpeed(int globalSpeed) {
        this.globalSpeed = globalSpeed;
    }

    public void setPredictedGuests(boolean predictedGuests) {
        this.predictedGuests = predictedGuests;
    }

    public static NPCLogic.DistanceMap getDistanceMap(String mapName){
        for (DistanceMap dm : distanceMaps) {
            if (dm.getMapName().equals(mapName))
                return dm;
        }

        return null;
    }
}
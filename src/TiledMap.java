import org.jfree.fx.FXGraphics2D;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TiledMap implements Drawable {

    private final static String SPRITESHEETS_DIR = "Resources/spritesheets";
    private final static String MAP_LAYOUT_DIR = "Resources/festmap.json";

    private final static int MAP_SIZE = 32;
    private final static int TILE_SIZE = 32;

    private ArrayList<TiledLayer> tiledLayers;

    public TiledMap() {
        this.tiledLayers = new ArrayList<>();
        TiledMapImage tiledMapImage = new TiledMapImage();

        try {
            JsonReader jsonReader = Json.createReader(new FileInputStream(new File(MAP_LAYOUT_DIR)));
            JsonArray layersJsonArray = jsonReader.readObject().getJsonArray("layers"); // layers{}

            for (JsonObject layerJsonObject : layersJsonArray.getValuesAs(JsonObject.class)) {
                tiledLayers.add(new TiledLayer(tiledMapImage, layerJsonObject));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        for (TiledLayer tiledLayer : tiledLayers) {
            tiledLayer.draw(graphics);
        }
    }

    public static String getSpritesheetsDir() {
        return SPRITESHEETS_DIR;
    }

    public static String getMapLayoutDir() {
        return MAP_LAYOUT_DIR;
    }

    public static int getMapSize() {
        return MAP_SIZE;
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }
}
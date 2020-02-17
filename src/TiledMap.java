import org.jfree.fx.FXGraphics2D;

import javax.json.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TiledMap implements Drawable {

    private final static String MAP_IMAGE_DIR = "Resources/terrain_atlas.png";
    private final static String MAP_LAYOUT_DIR = "Resources/map.json";

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

    }

    public static String getMapImageDir() {
        return MAP_IMAGE_DIR;
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
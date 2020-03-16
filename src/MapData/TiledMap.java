package MapData;

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

    private final static String SPRITESHEETS_DIR = "Resources/spritesheets/";
    private final static String MAP_LAYOUT_DIR = "Resources/festmap.json";

    private static int MAP_WIDTH = 100;//todo kunnen we deze niet beter op 0 initialiseren zodat je nooit out of bounds exceptions krijgt
    private static int MAP_HEIGHT = 100;
    private static int TILE_SIZE = 32;

    private ArrayList<TiledLayer> tiledLayers;

    public TiledMap() {
        this.tiledLayers = new ArrayList<>();
        TiledMapImage tiledMapImage = new TiledMapImage();

        try {
            JsonReader jsonReader = Json.createReader(new FileInputStream(new File(MAP_LAYOUT_DIR)));
            JsonObject baseJsonObject = jsonReader.readObject();
            jsonReader.close();

            MAP_WIDTH = baseJsonObject.getInt("width");
            MAP_HEIGHT = baseJsonObject.getInt("height");
            TILE_SIZE = baseJsonObject.getInt("tilewidth");

            JsonArray layersJsonArray = baseJsonObject.getJsonArray("layers");
            JsonArray tilesetsJsonArray = baseJsonObject.getJsonArray("tilesets");

            for (JsonObject tileset : tilesetsJsonArray.getValuesAs(JsonObject.class)) {
                tiledMapImage.initialise(tileset.getString("image"), tileset.getInt("firstgid"),
                        tileset.getInt("imagewidth") / TILE_SIZE, tileset.getInt("imageheight") / TILE_SIZE);
            }

            for (JsonObject layerJsonObject : layersJsonArray.getValuesAs(JsonObject.class)) {
                if (layerJsonObject.getBoolean("visible") && !layerJsonObject.getJsonString("type").toString().equals("objectgroup"))
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

    public static int getMapWidth() {
        return MAP_WIDTH;
    }

    public static int getMapHeight() {
        return MAP_HEIGHT;
    }

    public static String getSpritesheetsDir() {
        return SPRITESHEETS_DIR;
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }
}
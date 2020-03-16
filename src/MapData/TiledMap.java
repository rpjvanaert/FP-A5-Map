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

/**
 * this object is the mother object of the map
 * all the tiled layers are stored here
 */
public class TiledMap implements Drawable {
    //static finals for where you can find the sprites and the layout json file
    private final static String SPRITESHEETS_DIR = "Resources/spritesheets/";
    private final static String MAP_LAYOUT_DIR = "Resources/festmap.json";

    //values for the dimensions of the map
    private static int MAP_WIDTH;
    private static int MAP_HEIGHT;
    private static int TILE_SIZE;

    //arraylist where the layers are stored
    private ArrayList<TiledLayer> tiledLayers;

    /**
     * constructor
     * first the TiledMapImage is created
     * then the json file is read out and for each layer a new object is created
     */
    public TiledMap() {
        this.tiledLayers = new ArrayList<>();

        //create the tiledMapImage object so we can add to it the sprites later
        TiledMapImage tiledMapImage = new TiledMapImage();

        try {
            //read out the map layout file to an json object
            JsonReader jsonReader = Json.createReader(new FileInputStream(new File(MAP_LAYOUT_DIR)));
            JsonObject baseJsonObject = jsonReader.readObject();
            jsonReader.close();

            //get from the json object the dimensions and set the attributes
            MAP_WIDTH = baseJsonObject.getInt("width");
            MAP_HEIGHT = baseJsonObject.getInt("height");
            TILE_SIZE = baseJsonObject.getInt("tilewidth");

            //read the jsonObject out so we get two array lists containing the data for the layers and tilesets
            JsonArray layersJsonArray = baseJsonObject.getJsonArray("layers");
            JsonArray tilesetsJsonArray = baseJsonObject.getJsonArray("tilesets");

            //for each tileset in the tileset array we add the sprites to the tiledMapImage object so we get access to every sprite by their corresponding gid
            for (JsonObject tileset : tilesetsJsonArray.getValuesAs(JsonObject.class)) {
                tiledMapImage.addSpriteSheet(tileset.getString("image"), tileset.getInt("firstgid"),
                        tileset.getInt("imagewidth") / TILE_SIZE, tileset.getInt("imageheight") / TILE_SIZE);
            }

            //for each layer we create a tiledLayer object
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
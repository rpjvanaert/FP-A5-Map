import org.jfree.fx.FXGraphics2D;

import javax.json.JsonObject;
import java.util.ArrayList;

public class TiledMap implements Drawable {

    private final String MAP_IMAGE_DIR = "/Resources/SpriteSheet.jpg";
    private final String MAP_LAYOUT_DIR = "/Resources/MapPreset1.json";

    private final int MAP_SIZE = 50;
    private final int TILE_SIZE = 32;
    private ArrayList<TiledLayer> tiledLayers;

    public TiledMap() {
        this.tiledLayers = new ArrayList<>();
        TiledMapImage tiledMapImage = new TiledMapImage(this.MAP_SIZE, this.TILE_SIZE, this.MAP_IMAGE_DIR);

        //todo json shit
        for (JsonObject layerJsonObject : layersJsonArray) {
            tiledLayers.add(new TiledLayer(tiledMapImage, layerJsonObject));
        }
    }

    @Override
    public void draw(FXGraphics2D graphics) {

    }
}
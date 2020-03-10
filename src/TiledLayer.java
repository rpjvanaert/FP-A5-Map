import org.jfree.fx.FXGraphics2D;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class TiledLayer implements Drawable {

    private ArrayList<TiledTile> tiles;

    /**
     * @param mapImage
     * @param jsonObject
     */
    public TiledLayer(TiledMapImage mapImage, JsonObject jsonObject) {
        this.tiles = new ArrayList<>();

        JsonArray gidArray = jsonObject.getJsonArray("data");

        for (int i = 0; i < gidArray.size(); i++) {
            System.out.println(gidArray.get(i));

            if (gidArray.get(i).getValueType().equals(JsonValue.ValueType.NUMBER)) {
                int mapSize = TiledMap.getMapSize();
                int tileSize = TiledMap.getTileSize();


                Point2D pos = new Point2D.Double((i % mapSize) * tileSize, (i / mapSize) * tileSize);
                tiles.add(new TiledTile(mapImage.getTile(gidArray.getInt(i)), pos));
            }
        }
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        for (TiledTile tile : tiles) {
            tile.draw(graphics);
        }
    }
}
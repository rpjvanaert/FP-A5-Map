import org.jfree.fx.FXGraphics2D;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class TiledLayer implements Drawable {

    private ArrayList<TiledTile> tiles;

	/**
	 *
	 * @param mapImage
	 * @param jsonObject
	 */
	public TiledLayer(TiledMapImage mapImage, JsonObject jsonObject) {
		this.tiles = new ArrayList<>();

        JsonArray dataArray = jsonObject.getJsonArray("data");

        for (int i = 0; i < dataArray.size(); i++) {
            if (dataArray.get(i).getValueType().equals(JsonValue.ValueType.NUMBER)) {
                int mapSize = TiledMap.getMapSize();
                Point2D pos = new Point2D.Double(i % mapSize, i / mapSize);
                tiles.add(new TiledTile(mapImage.getTile(dataArray.getInt(i)), pos));
            }
        }
    }

    @Override
    public void draw(FXGraphics2D graphics) {

    }
}
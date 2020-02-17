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

		//todo json shit
		JsonArray jsonArray = jsonObject.getJsonArray("data");

		for (int i = 0; i < jsonArray.size(); i++) {
			if(jsonArray.get(i).getValueType().equals(JsonValue.ValueType.NUMBER)){
				Point2D pos = new Point2D.Double(i%jsonArray.size(), i);
				tiles.add(new TiledTile(mapImage.getTile(jsonArray.getInt(i)),pos));
			}
		}
	}

	@Override
	public void draw(FXGraphics2D graphics) {
	}
}
package MapData;

import org.jfree.fx.FXGraphics2D;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.*;
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

            if (gidArray.getInt(i) != 0) {
                int mapWidth = TiledMap.getMapWidth();
                int mapHeight = TiledMap.getMapHeight();
                int tileSize = TiledMap.getTileSize();

                Point2D pos = new Point2D.Double((i % mapWidth) * tileSize, (i / mapHeight) * tileSize);
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

    public void drawG(Graphics graphics){
        for (TiledTile tile : tiles){
            tile.drawG(graphics);
        }
    }

}
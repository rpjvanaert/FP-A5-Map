package MapData;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class TiledTile implements Drawable {

    private BufferedImage tileImage;
    private Point2D position;

    /**
     * @param tileImage
     * @param position
     */
    public TiledTile(BufferedImage tileImage, Point2D position) {
        this.tileImage = tileImage;
        this.position = position;
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        graphics.drawImage(tileImage, (int) position.getX(), (int) position.getY(), TiledMap.getTileSize(), TiledMap.getTileSize(), null);
    }

    public void drawG(Graphics graphics){
        graphics.drawImage(tileImage, (int) position.getX(), (int) position.getY(), TiledMap.getTileSize(), TiledMap.getTileSize(), null);
    }
}
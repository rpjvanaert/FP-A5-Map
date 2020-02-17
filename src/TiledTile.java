import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class TiledTile implements Drawable {

    private BufferedImage tileImage;
    private Point2D position;

    /**
     * @param image
     * @param position
     */
    public TiledTile(BufferedImage image, Point2D position) {
        this.tileImage = image;
        this.position = position;
    }

    @Override
    public void draw(FXGraphics2D graphics) {

    }
}
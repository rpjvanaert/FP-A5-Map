import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TiledMapImage {

    private List<BufferedImage> tileImages;

    public TiledMapImage() {
        this.tileImages = new ArrayList<>();
        tileImages.add(null);

        try {
            File directory = new File(TiledMap.getSpritesheetsDir());
            File[] files = directory.listFiles();

            int mapSize = TiledMap.getMapSize();
            int tileSize = TiledMap.getTileSize();

            for (File file : files) {
                BufferedImage image = ImageIO.read(file);

                for (int y = 0; y < mapSize; y++) {
                    for (int x = 0; x < mapSize; x++) {
                        this.tileImages.add(image.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param gid
     */
    public BufferedImage getTile(int gid) {
        return (BufferedImage) tileImages.toArray()[gid];
    }
}
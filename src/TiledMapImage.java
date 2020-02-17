import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TiledMapImage {

    private Set<BufferedImage> tileImages;

    public TiledMapImage() {
        this.tileImages = new HashSet<>();
        try {
            BufferedImage image = ImageIO.read(new File(TiledMap.getMapImageDir()));

            int mapSize = TiledMap.getMapSize();
            int tileSize = TiledMap.getTileSize();

            for (int y = 0; y < mapSize; y++) {
                for (int x = 0; x < mapSize; x++) {
                    this.tileImages.add(image.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize));
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
        System.out.println(gid);
        System.out.println(tileImages.toArray()[gid].toString());
        return (BufferedImage) tileImages.toArray()[gid];
    }
}
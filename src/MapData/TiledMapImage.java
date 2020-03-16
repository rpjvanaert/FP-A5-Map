package MapData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class TiledMapImage {
    //hash map to save the bufferedImages by their gid
    private HashMap<Integer, BufferedImage> tileImages;

    public TiledMapImage() {
        this.tileImages = new HashMap<>();
    }

    /**
     * @param gid
     */
    public BufferedImage getTile(int gid) {
        return tileImages.get(gid);
    }

    public void initialise(String spritesheetName, int startingGID, int mapWidth, int mapHeight) {
        try {
            int tileSize = TiledMap.getTileSize();
            int counter = startingGID;
            BufferedImage image = ImageIO.read(new File(TiledMap.getSpritesheetsDir() + spritesheetName));

            for (int y = 0; y < mapHeight; y++) {
                for (int x = 0; x < mapWidth; x++) {
                    this.tileImages.put(counter, image.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize));
                    counter++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
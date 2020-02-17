import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TiledMapImage {

	private Set<BufferedImage> tileImages;

	/**
	 *
	 * @param mapSize
	 * @param tileSize
	 * @param mapImageDir
	 */
	public TiledMapImage(int mapSize, int tileSize, String mapImageDir) {
		this.tileImages = new HashSet<>();
		try {
			BufferedImage image = ImageIO.read(getClass().getResource(mapImageDir));
			for (int y = 0; y < mapSize; y++) {
				for (int x = 0; x < mapSize; x++) {
					this.tileImages.add(image.getSubimage(x*tileSize,y*tileSize,tileSize,tileSize));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param gid
	 */
	public BufferedImage getTile(int gid) {
		return (BufferedImage) tileImages.toArray()[gid];
	}

}
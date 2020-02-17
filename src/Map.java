import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


public class Map {
    private int width;
    private int height;

    private int tileHeight;
    private int tileWidth;

    private ArrayList<BufferedImage> tiles = new ArrayList<>();

    private int[][] map;

    public Map(String fileName) {
        JsonReader reader = null;
        reader = Json.createReader(getClass().getResourceAsStream(fileName));
        JsonObject root = reader.readObject();

        this.width = root.getJsonArray("layers").getJsonObject(0).getInt("width");
        this.height = root.getJsonArray("layers").getJsonObject(0).getInt("height");


//        JsonReader reader = null;
//        reader = Json.createReader(getClass().getResourceAsStream(fileName));
//        JsonObject root = reader.readObject();
//
//        this.width = root.getInt("width");
//        this.height = root.getInt("height");
//
//        //load the tilemap
//        try {
//            BufferedImage tilemap = ImageIO.read(getClass().getResourceAsStream(root.getJsonObject("tilemap").getString("file")));
//
//            tileHeight = root.getJsonObject("tilemap").getJsonObject("tile").getInt("height");
//            tileWidth = root.getJsonObject("tilemap").getJsonObject("tile").getInt("width");
//
//            for(int y = 0; y < tilemap.getHeight(); y += tileHeight)
//            {
//                for(int x = 0; x < tilemap.getWidth(); x += tileWidth)
//                {
//                    tiles.add(tilemap.getSubimage(x, y, tileWidth, tileHeight));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        map = new int[height][width];
//        for(int y = 0; y < height; y++)
//        {
//            for(int x = 0; x < width; x++)
//            {
//                map[y][x] = root.getJsonArray("map").getJsonArray(y).getInt(x);
//            }
//        }

        this.map = new int[this.height][this.width];
        for (int y = 0; y < this.height; y++) {

            for (int x = 0; x < this.width; x++) {

                int i = x + y * 32;
                int adding = root.getJsonArray("layers").getJsonObject(0).getJsonArray("data").getInt(i);
                System.out.println(adding);
                this.map[y][x] = adding;
            }
        }

        try {
            BufferedImage tilemap = ImageIO.read(getClass().getResourceAsStream(root.getJsonArray("tilesets").getJsonObject(0).getString("image")));

            this.tileHeight = root.getJsonArray("tilesets").getJsonObject(0).getInt("tileheight");
            this.tileWidth = root.getJsonArray("tilesets").getJsonObject(0).getInt("tilewidth");

            for (int y = 0; y < tilemap.getHeight(); y += this.tileHeight) {

                for (int x = 0; x < tilemap.getWidth(); x += this.tileWidth) {
                    this.tiles.add(tilemap.getSubimage(x, y, this.tileWidth, this.tileHeight));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void draw(Graphics2D g2d) {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] < 0)
                    continue;

                g2d.drawImage(
                        tiles.get(map[y][x]),
                        AffineTransform.getTranslateInstance(x * tileWidth, y * tileHeight),
                        null);
            }
        }


    }

}
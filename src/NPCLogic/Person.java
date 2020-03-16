package NPCLogic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * A class to represent a visitor
 */
public class Person {

    private BufferedImage sprite;
    private String favoriteGenre;
    private Media soundEffect;
    private MediaPlayer mediaPlayer;
    private PersonLogic personLogic;

    /**
     * A constructor of NPCLogic.Person
     * @param position the starting position of the NPCLogic.Person
     * @param genreChanceList a list for the probability of liking a Genre
     * @param speed the movement speed of the NPCLogic.Person
     */
    public Person(Point2D position, ArrayList<Integer> genreChanceList, int speed) {

        imageDecider(genreChanceList);
        this.personLogic = new PersonLogic(position,speed, this);

    }

    /**
     * Assings the corresponding image and sound according to the liked superGenre
     * @param genreChance
     */
    public void imageDecider(ArrayList<Integer> genreChance) {
        int number = (int) (Math.random() * ((genreChance.get(6) - 1) + 1)) + 1;

        if (genreChance.get(0) >= number && number > 0) {
            try {
                this.sprite = ImageIO.read(this.getClass().getResourceAsStream("/images/metal.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.favoriteGenre = "metal";
            this.soundEffect = new Media(new File("resources/soundEffects/MetalScream.mp3").toURI().toString());
        } else if ((genreChance.get(0) + genreChance.get(1)) >= number && number > genreChance.get(0)) {
            try {
                this.sprite = ImageIO.read(this.getClass().getResourceAsStream("/images/classic.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.favoriteGenre = "classic";
            this.soundEffect = new Media(new File("resources/soundEffects/ClassicLaugh.mp3").toURI().toString());
        } else if ((genreChance.get(0) + genreChance.get(1) + genreChance.get(2)) >= number && number > (genreChance.get(0) + genreChance.get(1))) {
            try {
                this.sprite = ImageIO.read(this.getClass().getResourceAsStream("/images/country.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.favoriteGenre = "Country";
            this.soundEffect = new Media(new File("resources/soundEffects/CountryAlabama.mp3").toURI().toString());
        } else if ((genreChance.get(6) - genreChance.get(5) - genreChance.get(4)) >= number && number > (genreChance.get(0) + genreChance.get(1) + genreChance.get(2))) {
            try {
                this.sprite = ImageIO.read(this.getClass().getResourceAsStream("/images/rap.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.favoriteGenre = "rap";
            this.soundEffect = new Media(new File("resources/soundEffects/ClassicLaugh.mp3").toURI().toString());
        } else if ((genreChance.get(6) - genreChance.get(5)) >= number && number > (genreChance.get(6) - genreChance.get(5) - genreChance.get(4))) {

            try {
                this.sprite = ImageIO.read(this.getClass().getResourceAsStream("/images/Pop.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.favoriteGenre = "Pop";
            this.soundEffect = new

                    Media(new File("resources/soundEffects/ClassicLaugh.mp3").toURI().toString());
        } else if (genreChance.get(6) >= number && number > (genreChance.get(6) - genreChance.get(5))) {
            try {
                this.sprite = ImageIO.read(this.getClass().getResourceAsStream("/images/electro.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.favoriteGenre = "Electro";
            this.soundEffect = new Media(new File("resources/soundEffects/ClassicLaugh.mp3").toURI().toString());
        } else {
            try {
                this.sprite = ImageIO.read(this.getClass().getResourceAsStream("/images/npc.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.favoriteGenre = "npc";
            this.soundEffect = new Media(new File("resources/soundEffects/ClassicLaugh.mp3").toURI().toString());

        }
        this.mediaPlayer = new MediaPlayer(this.soundEffect);
    }

    public PersonLogic getPersonLogic() {
        return personLogic;
    }

    /**
     * decides the behavior of the NPCLogic.Person
     */

    public void update(ArrayList<Person> people) {

this.personLogic.update();
        //colliding handler
        boolean collided = false;

        for (Person other : people) {
            if (other != this && this.personLogic.getNewPosition().distance(other.personLogic.getPosition()) < 32) {
                collided = true;
            }
        }

        if (!collided) {
            this.personLogic.setPosition(this.personLogic.getNewPosition());
        } else {
            this.personLogic.setTarget(PathCalculator.findRandomClosestWalkable(this.personLogic.getPosition(),this.personLogic.getTargetMapName()));
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(sprite, this.personLogic.getTransform(), null);
    }


    /**
     * Plays an soundEffect according to the genre
     */
    public void playSoundEffect() {
        if (this.favoriteGenre.equals("metal")) {
            this.mediaPlayer.setVolume(0.05);
        }
        this.mediaPlayer.setStartTime(Duration.millis(0));
        this.mediaPlayer.play();
        this.mediaPlayer.stop();
        this.mediaPlayer.setStartTime(Duration.millis(0));

    }

    public BufferedImage getSprite() {
        return sprite;
    }
}
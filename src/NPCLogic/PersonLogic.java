package NPCLogic;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Random;

public class PersonLogic {
    private Point2D position;
    private double angle;
    private double speed;
    private Point2D target;
    private double rotationSpeed;
    private String targetMapName;
    private String activity;
    private Person person;
    private Point2D newPosition;

    private int negativeFeedback = 5;


    public PersonLogic(Point2D position, double speed, Person person) {
        this.position = position;
        this.person = person;
        this.angle = 0;
        this.speed = speed;
        this.rotationSpeed = 100;
        this.targetMapName = selectRandomMap();
    }

    public void choiceMaker() {
        int number = (int) (Math.random() * ((10 - 1) + 1)) + 1;
        if (number > 5/*this.favoriteGenre==genre.getSuperGenre()*/) {
            //change back once integrated with the main application
            if (number < 2) {
                System.out.println("didn't go, so idle");
                this.negativeFeedback--;
            } else {
                System.out.println("did go to the show");
                this.negativeFeedback = 5;
            }
        } else {
            if (number <= this.negativeFeedback) {
                System.out.println("didn't go, so idle");
                this.negativeFeedback--;
            } else {
                System.out.println("did go to the show");
                this.negativeFeedback = 5;
            }
        }
    }

    public Point2D getPosition() {
        return position;
    }

    public AffineTransform getTransform() {
        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - this.person.getSprite().getWidth() / 2, position.getY() - this.person.getSprite().getHeight() / 2);
        tx.rotate(this.angle, this.person.getSprite().getWidth() / 2, this.person.getSprite().getHeight() / 2);
        return tx;
    }

    public void setTarget(Point2D target) {
        this.target = target;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }

    public Point2D getTarget() {
        return target;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public String getTargetMapName() {
        return targetMapName;
    }

    public Person getPerson() {
        return person;
    }

    public int getNegativeFeedback() {
        return negativeFeedback;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setTargetMapName(String mapName){
        this.targetMapName = mapName;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setNegativeFeedback(int negativeFeedback) {
        this.negativeFeedback = negativeFeedback;
    }

    /**
     * checks if the NPCLogic.Person has arrived at the target
     * @return
     */
    public boolean hasArrivedAtTarget(){
        double distanceAmount = 17;
        return position.distance(target.getX(), target.getY()) < distanceAmount;
    }

    /**
     *checks if the NPCLogic.Person has arrived at it's end destination
     */
    private boolean hasArrivedAtDestination() {
        double distanceAmount = 16;
        if(this.target.distance(new Point2D.Double(-1,-1)) < distanceAmount){
            return true;
        }
        return false;
    }

    public void setNextTarget(){
        this.target = PathCalculator.nextTarget(this.position, targetMapName);
    }

    /**
     * For testing purposes!
     * Selects a random distanceMap
     * @return the name of the map
     */
    public String selectRandomMap(){
        Random random = new Random();
        String mapName = null;
        int randomNumber = random.nextInt(3);
        switch (randomNumber){
            case 0: mapName = "TestMap1";
                break;
            case 1: mapName = "TestMap2";
                break;
            case 2: mapName = "TestMap3";
                break;

        }
        return mapName;
    }

    public Point2D getNewPosition() {
        return newPosition;
    }

    public void update(){
        if (hasArrivedAtDestination()){
            targetMapName = selectRandomMap();
            setNextTarget();
        } else if (hasArrivedAtTarget()){
            setNextTarget();
        }


        double targetAngle = Math.atan2(this.target.getY() - this.position.getY(),
                this.target.getX() - this.position.getX());

        double angleDifference = this.angle - targetAngle;
        while (angleDifference < -Math.PI)
            angleDifference += 2 * Math.PI;
        while (angleDifference > Math.PI)
            angleDifference -= 2 * Math.PI;


        if (Math.abs(angleDifference) < this.rotationSpeed)
            this.angle = targetAngle;
        else if (angleDifference < 0)
            this.angle += this.rotationSpeed;
        else
            this.angle -= this.rotationSpeed;

        this.newPosition = new Point2D.Double(this.position.getX() + this.speed * Math.cos(this.angle),
                this.position.getY() + this.speed * Math.sin(this.angle));

    }

}
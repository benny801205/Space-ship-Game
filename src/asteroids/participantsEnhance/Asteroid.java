package asteroids.participantsEnhance;

import static asteroids.gameEnhance.Constants.*;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.gameEnhance.Controller;
import asteroids.gameEnhance.Participant;

/**
 * Represents asteroids
 */
public class Asteroid extends Participant implements ShipDestroyer
{
    /** The size of the asteroid (0 = small, 1 = medium, 2 = large) */
    private int size;

    /** The outline of the asteroid */
    private Shape outline;

    /** The game controller */
    private Controller controller;

    private int[] speedlist= {MAXIMUM_LARGE_ASTEROID_SPEED,MAXIMUM_MEDIUM_ASTEROID_SPEED,MAXIMUM_SMALL_ASTEROID_SPEED};
    /**
     * Throws an IllegalArgumentException if size or variety is out of range.
     * 
     * Creates an asteroid of the specified variety (0 through 3) and size (0 = small, 1 = medium, 2 = large) and
     * positions it at the provided coordinates with a random rotation. Its velocity has the given speed but is in a
     * random direction.
     */
    public Asteroid (int variety, int size, double x, double y, Controller controller)
    {
        // Make sure size and variety are valid
        if (size < 0 || size > 2)
        {
            throw new IllegalArgumentException("Invalid asteroid size: " + size);
        }
        else if (variety < 0 || variety > 3)
        {
            throw new IllegalArgumentException();
        }

        this.size=size;
        // Create the asteroid
        this.controller = controller;
        this.size = size;
        setPosition(x, y);
        setVelocity(checkspeed(), RANDOM.nextDouble() * 2 * Math.PI);
        setRotation(2 * Math.PI * RANDOM.nextDouble());
        setColor(Asteroid_C);// Asteriud is white
        createAsteroidOutline(variety, size);
    }

    private int checkspeed()
    {
        if(size==2) {return speedlist[0];}
        
        else if(size ==1) {return speedlist[RANDOM.nextInt(2)];}
        
        else {
        return speedlist[RANDOM.nextInt(3)];  
        }
        
    }
    
    
    
    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    /**
     * Creates the outline of the asteroid based on its variety and size.
     */
    private void createAsteroidOutline (int variety, int size)
    {
        // This will contain the outline
        Path2D.Double poly = new Path2D.Double();

        // Fill out according to variety
        if (variety == 0)
        {
           
            poly.moveTo(0, -30);
            poly.lineTo(28, -15);
            poly.lineTo(20, 20);
            poly.lineTo(4, 8);
            poly.lineTo(-1, 30);
            poly.lineTo(-12, 15);
            poly.lineTo(-5, 2);
            poly.lineTo(-25, 7);
            poly.lineTo(-10, -25);
            poly.closePath();
        }
        else if (variety == 1)
        {
            poly.moveTo(10, -28);
            poly.lineTo(7, -16);
            poly.lineTo(30, -9);
            poly.lineTo(30, 9);
            poly.lineTo(10, 13);
            poly.lineTo(5, 30);
            poly.lineTo(-8, 28);
            poly.lineTo(-6, 6);
            poly.lineTo(-27, 12);
            poly.lineTo(-30, -11);
            poly.lineTo(-6, -15);
            poly.lineTo(-6, -28);
            poly.closePath();
        }
        else if (variety == 2)
        {
            poly.moveTo(10, -30);
            poly.lineTo(30, 0);
            poly.lineTo(15, 30);
            poly.lineTo(0, 15);
            poly.lineTo(-15, 30);
            poly.lineTo(-30, 0);
            poly.lineTo(-10, -30);
            poly.closePath();
        }
        else
        {
            poly.moveTo(30, -18);
            poly.lineTo(5, 5);
            poly.lineTo(30, 15);
            poly.lineTo(15, 30);
            poly.lineTo(0, 25);
            poly.lineTo(-15, 30);
            poly.lineTo(-25, 8);
            poly.lineTo(-10, -25);
            poly.lineTo(0, -30);
            poly.lineTo(10, -30);
            poly.closePath();
        }

        // Scale to the desired size
        double scale = ASTEROID_SCALE[size];
        poly.transform(AffineTransform.getScaleInstance(scale, scale));

        // Save the outline
        outline = poly;
    }

    /**
     * Returns the size of the asteroid
     */
    public int getSize ()
    {
        return size;
    }

    /**
     * When an Asteroid collides with an AsteroidDestroyer, it expires.
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof AsteroidDestroyer)
        {
            if(size==2) {
                controller.placeSubAsteroids(this.getX(), this.getY(), size-1);
                controller.placeSubAsteroids(this.getX(), this.getY(), size-1);
                if(p instanceof Bullet) {
                controller.addScore(((Bullet) p).getOwner(),20);}
            }
            else if(size==1) {
                controller.placeSubAsteroids(this.getX(), this.getY(), size-1);
                controller.placeSubAsteroids(this.getX(), this.getY(), size-1);
                if(p instanceof Bullet) {
                controller.addScore(((Bullet) p).getOwner(),50);}
            }
            else if(size==0) {
                if(p instanceof Bullet) {
                controller.addScore(((Bullet) p).getOwner(),100);}
                }
            // Expire the asteroid and put effect
            controller.placeDust(this.getX(), this.getY(),this.getColor());
            Participant.expire(this);

            // Inform the controller
            
            controller.asteroidDestroyed();
            
            
            
        }
    }

    @Override
    public BufferedImage getPic ()
    {
        // TODO Auto-generated method stub
        return null;
    }
}

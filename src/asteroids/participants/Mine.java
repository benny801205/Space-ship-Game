package asteroids.participants;

import static asteroids.game.Constants.ASTEROID_SCALE;
import static asteroids.game.Constants.RANDOM;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.FlameSestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.game.Controller;
import asteroids.game.Participant;

public class Mine extends Participant implements FlameSestroyer
{
    /** The outline of the ship */
    private Shape outline;
    private int size;
    /** Game controller */
    private Controller controller;
    public Mine (int variety, int size, double x, double y, int speed, Controller controller)
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

        // Create the asteroid
        this.controller = controller;
        this.size = size;
        setPosition(x, y);
        setVelocity(0, RANDOM.nextDouble() * 2 * Math.PI);
        setRotation(2 * Math.PI * RANDOM.nextDouble());
        setColor(Color.red);
        createAsteroidOutline(variety, size);
    }
    
    private void createAsteroidOutline (int variety, int size)
    {
        
 /*       double leftX = 100;
        double topY = 100;
        double width = 200;
        double height = 150;

        Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
        Ellipse2D ellipse = new Ellipse2D.Double();
        ellipse.setFrame(rect);
        // This will contain the outline
        Path2D.Double poly = new Path2D.Double();
        
        // Fill out according to variety
        
        // Scale to the desired size
        double scale = ASTEROID_SCALE[size];
        poly.transform(AffineTransform.getScaleInstance(scale, scale));

        // Save the outline
        outline = new Area(new Rectangle2D.Double(20, 20, 100, 100));*/
        Path2D.Double poly = new Path2D.Double();
       /* poly.lineTo(0, 0);
        poly.lineTo(-8,5);
        poly.lineTo(-7,4);  
        poly.lineTo(-12,3);
        poly.lineTo(-10,1);
        poly.lineTo(-13,0);
        poly.lineTo(-10,-1);
        poly.lineTo(-12,-3);
        poly.lineTo(-7,-4); 
        poly.lineTo(-8,-5);
        poly.closePath();*/
        outline=new Area(new Rectangle2D.Double(20, 20, 100, 100));
        
        
        
    }
    @Override
    protected Shape getOutline ()
    {
        // TODO Auto-generated method stub
        return outline;
    }

    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof ShipDestroyer)
        {
            // Expire the asteroid
           // Participant.expire(this);

            // Inform the controller
           // controller.asteroidDestroyed();
        }
        
    }

    @Override
    protected Shape getflameShape ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Color getflameC ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void flamemove ()
    {
        // TODO Auto-generated method stub
        
    }

    
    
    
    
    
    
    
    
    
    
    
}

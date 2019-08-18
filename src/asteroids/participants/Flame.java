package asteroids.participants;

import static asteroids.game.Constants.RANDOM;
import static asteroids.game.Constants.SHIP_FRICTION;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Path2D;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.FlameSestroyer;
import asteroids.game.Controller;
import asteroids.game.Participant;





public class Flame extends Participant implements FlameSestroyer
{
    /** The outline of the asteroid */
    private Shape outline;

    /** The game controller */
    private Controller controller;
    
    
    public Flame(double x ,double y,double direction, Controller controller,double speed)
    {
        setColor(Color.red);
        this.controller = controller;
        setPosition(x, y);
        setRotation(direction);
        setVelocity(speed, direction);
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(0, 0);
        poly.lineTo(-8,5);
        poly.lineTo(-7,4);  
        poly.lineTo(-12,3);
        poly.lineTo(-10,1);
        poly.lineTo(-13,0);
        poly.lineTo(-10,-1);
        poly.lineTo(-12,-3);
        poly.lineTo(-7,-4); 
        poly.lineTo(-8,-5);
        poly.lineTo(0, 0);
        poly.closePath();
        outline=poly;

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    @Override
    public void collidedWith (Participant p)
    {
        // TODO Auto-generated method stub
        
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

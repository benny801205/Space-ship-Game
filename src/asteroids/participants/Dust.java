package asteroids.participants;

import static asteroids.game.Constants.RANDOM;
import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import javax.swing.Timer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.BulletDestroyer;
import asteroids.destroyers.FlameSestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.game.Controller;
import asteroids.game.Participant;

public class Dust extends Participant implements  ActionListener
{
    /** The outline of the asteroid */
    private Shape outline;

    /** The game controller */
    private Controller controller;
    private Timer timer;
    
    public Dust(double x ,double y,int Duration, Controller controller,Color color)
    {

        setColor(color);
        setVelocity(2, RANDOM.nextDouble() * 2 * Math.PI);
        setRotation(2 * Math.PI );
        this.controller = controller;
        setPosition(x, y);
        creatshape();
        
        timer=new Timer(Duration, this);
        timer.start();
        
    }

    
    
    
    public void creatshape ()
    {
        int randomRadius= RANDOM.nextInt(5)+1;
        outline=new Ellipse2D.Double(0, 0, randomRadius, randomRadius);

        
        
    }
    
    @Override
    protected Shape getOutline ()
    {

        return outline;
    }

    @Override
    public void collidedWith (Participant p)
    {
 
        
    }




    @Override
    public void actionPerformed (ActionEvent e)
    {
        timer.stop();
        expire(this);
        
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
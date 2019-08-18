package asteroids.participants;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import javax.swing.Timer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.BulletDestroyer;
import asteroids.destroyers.FlameSestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.game.Controller;
import asteroids.game.Participant;

public class Bullet extends Participant implements AsteroidDestroyer, ActionListener, ShipDestroyer , BulletDestroyer
{
    /** The outline of the asteroid */
    private Shape outline;

    /** The game controller */
    private Controller controller;
    private Timer timer;
    
    public Bullet(double x ,double y,double direction,int BulSpeed,int Duration, Controller controller,Color color)
    {
        setColor(color);
        
        this.controller = controller;
        setPosition(x, y);
        setRotation(direction);
        setVelocity(BulSpeed, direction);
        creatshape();
        
        timer=new Timer(Duration, this);
        timer.start();
        
    }

    
    
    
    public void creatshape ()
    {
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(-5, 1);
        poly.lineTo(-5, -1);
        poly.lineTo(-10, -1);
        poly.lineTo(-10, 1);
        poly.closePath();
        Area tempA =new Area(poly);
        AffineTransform at =AffineTransform.getTranslateInstance(17,0);
        
        
        outline=tempA.createTransformedArea(at);
        //outline=poly;
        
        //outline = new Area(new Rectangle2D.Double(0, 0, 6, 2));
        
        
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
        if ((p instanceof ShipDestroyer ||p instanceof BulletDestroyer)&& p.getColor()!= this.getColor())
        {
            // Expire the ship from the game
            Participant.expire(this);
            timer.stop();

        }
        
        
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

package asteroids.participantsEnhance;

import static asteroids.gameEnhance.Constants.RANDOM;
import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.gameEnhance.Controller;
import asteroids.gameEnhance.Participant;

public class Wreckage extends Participant implements ActionListener
{

    /** The outline of the asteroid */
    private Shape outline;

    /** The game controller */
    private Controller controller;
    private Timer timer;
    private Color color;
    Ship p;
    public Wreckage(double x ,double y,int Duration, Controller controller,Color color,Ship p)
    {
        this.color=color;
        this.p = p;
        setColor(color);
        setVelocity(0, RANDOM.nextDouble() * 2 * Math.PI);
        setRotation(2 * Math.PI );
        this.controller = controller;
        setPosition(x, y);
        creatshape();
        
        timer=new Timer(Duration, this);
        timer.start();
        
    }

    
    
    
    public void creatshape ()
    {
        Path2D.Double poly = new Path2D.Double();
        
        poly.moveTo(10, 5);
        poly.lineTo(12, 17);
        poly.lineTo(23, 13);
        poly.closePath();
        poly.moveTo(10, -4);
        poly.lineTo(12, -22);
        poly.lineTo(23,-16);
        poly.lineTo(20, -8);
        poly.closePath();
        poly.moveTo(-10, 10);
        poly.lineTo(-3,8);
        poly.lineTo(-11, -7);
        poly.lineTo(-13, -2);
        poly.closePath();
        
        outline=poly;
        
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void actionPerformed (ActionEvent e)
    {
        // TODO Auto-generated method stub
        timer.stop();
        expire(this);
        controller.reviveShip(p);
    }




    @Override
    public BufferedImage getPic ()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    
    
    
    
}

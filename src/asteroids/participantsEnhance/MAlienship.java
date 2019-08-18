package asteroids.participantsEnhance;

import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.gameEnhance.Controller;
import asteroids.gameEnhance.Participant;
import static asteroids.gameEnhance.Constants.*;
public class MAlienship extends Participant implements ActionListener,AsteroidDestroyer,ShipDestroyer
{
    /** The outline of the asteroid */
    private Shape outline;

    /** The game controller */
    private Controller controller;
    private Timer movetimer;
    private int speed=3;
    private Timer firetimer;
    private double direction;
    private int[] curvelist= {0,1,-1};
    
    public MAlienship(Controller controller) {
        
    direction=Math.PI*RANDOM.nextInt(2);
    setColor(Alien_C);
    setVelocity(speed, direction );
  
    

    this.controller = controller;
    setPosition(SIZE*RANDOM.nextInt(1), SIZE*Math.random());
    creatshape();
    
    movetimer=new Timer(1000, this);
    movetimer.start();
    firetimer=new Timer(2500, this);
    firetimer.start();
    }
    public void creatshape ()
    {
        
        outline=new Ellipse2D.Double(0, 0, 60, 60);

        
        
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
        if (p instanceof ShipDestroyer && p.getColor()!= this.getColor())
        {
            if(p instanceof Bullet) {
                controller.addScore(((Bullet) p).getOwner(),200);}
            
            controller.placeDust(this.getX(), this.getY(),this.getColor());
            movetimer.stop();
            firetimer.stop();
            Participant.expire(this);
            controller.MAdestroy();
        }
        
    }
    @Override
    public void actionPerformed (ActionEvent e)
    {
        if(this.isExpired()) {
            movetimer.stop();
            firetimer.stop();  
            
        }
        if(e.getSource() == movetimer) {
        setVelocity(speed,  direction+ curvelist[RANDOM.nextInt(3)]);}
        else if(e.getSource() == firetimer)
        {
            controller.placeMAlienBullet(this);
        }
        
        
        
    }
    @Override
    public BufferedImage getPic ()
    {
        // TODO Auto-generated method stub
        return null;
    } 


    
    
    
    
    
    
    
    
    
    
    
}

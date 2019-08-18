package asteroids.participants;

import static asteroids.game.Constants.*;
import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import asteroids.destroyers.*;
import asteroids.game.Controller;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;


/**
 * Represents ships
 */
public class Ship extends Participant implements AsteroidDestroyer,ShipDestroyer,ActionListener
{
    /** The outline of the ship */
    private Shape outline;
    private Color flameColor;
    private Shape flameshape=null;
    /** Game controller */
    private Controller controller;
    private Timer flametimer;
  
    private Clip beat1;
    private Clip beat2;
    private Timer Soundtimer;
    private Timer SecondSoundtimer;
    private Clip destorySound;
    /**
     * Constructs a ship at the specified coordinates that is pointed in the given direction.
     */
    public Ship (int x, int y, double direction, Controller controller ,Color color)
    {
        this.controller = controller;
        setPosition(x, y);
        setRotation(direction);
        flametimer= new Timer(50, this);
        createFlameshape();
        flameColor=Color.black;

        withoutFlame();

        setColor(color);
        // Schedule an acceleration in two seconds
        //new ParticipantCountdownTimer(this, "move", 2000);
        beat1=createClip("/sounds/beat1.wav");
        beat2=createClip("/sounds/beat2.wav");
        destorySound=createClip("/sounds/bangShip.wav");   
        Soundtimer= new Timer(1400, this);
        Soundtimer.start();
        SecondSoundtimer= new Timer(700, this);
    }

    public void withoutFlame()
    {
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(22, 0);
        poly.lineTo(-20, 12);
        //poly.lineTo(-20, 0);
        poly.lineTo(-20, -12);
        poly.closePath();
        outline=poly;
        
    }
    public void createFlameshape()
    {
        
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
        Area tempA = new Area(poly);
        
        AffineTransform at =AffineTransform.getTranslateInstance(-21,0);
        
        
        flameshape=tempA.createTransformedArea(at);
    }
    
    public void creatwithflame()
    {
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(15, 0);
        poly.lineTo(-20, 15);
        poly.lineTo(-10, 0);
        poly.lineTo(-18,5);
        poly.lineTo(-17,4);  
        poly.lineTo(-22,3);
        poly.lineTo(-20,1);
        poly.lineTo(-23,0);
        poly.lineTo(-20,-1);
        poly.lineTo(-22,-3);
        poly.lineTo(-17,-4); 
        poly.lineTo(-18,-5);
        poly.lineTo(-10, 0);
        poly.lineTo(-20, -15);
        poly.closePath();
        outline=poly;
        
    }
    
    
    
    
    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose is located.
     */
 /*   public double getXNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getX();
    }

    *//**
     * Returns the x-coordinate of the point on the screen where the ship's nose is located.
     *//*
    public double getYNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getY();
    }*/
    @Override
    protected Shape getflameShape()
    {
        return flameshape;
    }
    @Override
    protected Color getflameC()
    {
        return flameColor;
    }
    
    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    /**
     * Customizes the base move method by imposing friction
     */
    @Override
    public void move ()
    {
        
        applyFriction(SHIP_FRICTION);
        super.move();
    }

    /**
     * Turns right by Pi/16 radians
     */
    public void turnRight ()
    {
        rotate(Math.PI / 16);
    }

    /**
     * Turns left by Pi/16 radians
     */
    public void turnLeft ()
    {
        rotate(-Math.PI / 16);
    }

    /**
     * Accelerates by SHIP_ACCELERATION
     */
    public void accelerate (double a)
    {

        flameColor=Color.red;
        flametimer.start();
        super.accelerate(a);
        
    }

    public void brake ()
    {
        brake(SHIP_ACCELERATION);
    }
    
    /**
     * When a Ship collides with a ShipDestroyer, it expires
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof ShipDestroyer && p.getColor()!= this.getColor())
        {
            // Expire the ship from the game
            Participant.expire(this);

            // Tell the controller the ship was destroyed
            controller.shipDestroyed(this);
            destorySound.setFramePosition(0);
            destorySound.start();
        }
    }

    /**
     * This method is invoked when a ParticipantCountdownTimer completes its countdown.
     */
    @Override
    public void countdownComplete (Object payload)
    {
        // Give a burst of acceleration, then schedule another
        // burst for 200 msecs from now.
        if (payload.equals("move"))
        {
         
            new ParticipantCountdownTimer(this, "move", 200);
        }
    }

    @Override
    public void actionPerformed (ActionEvent e)
    {
        if(e.getSource()==flametimer) {
        flametimer.stop();
        flameColor=Color.black;
        }
        else if(e.getSource()==Soundtimer) {
            if(!this.isExpired()) {
                createSound();
                
            }
            
        }
        else if(e.getSource()==SecondSoundtimer)
        {
            if(!this.isExpired()) {
                SecondSoundtimer.stop();
                beat2.setFramePosition(0);
                beat2.start();
            }
            
            else {SecondSoundtimer.stop();}
            
            
        }
        
        
        
        
    }
    
    
    public void createSound ()
    {
        beat1.setFramePosition(0);
        beat1.start();
        SecondSoundtimer.start();
      //  beat2.setFramePosition(500);
       // beat2.start();
    }
    
    
    
    @Override
    public void flamemove ()
    {
        Shape original = getflameShape();

        // Translate and rotate the original to reflect the accumulated motion
        AffineTransform trans = AffineTransform.getTranslateInstance(getX(), getY());
        trans.concatenate(AffineTransform.getRotateInstance(getRotation()));
        this.flameshape = trans.createTransformedShape(original);

    }
    
    public Clip createClip (String soundFile)
    {
        // Opening the sound file this way will work no matter how the
        // project is exported. The only restriction is that the
        // sound files must be stored in a package.
        try (BufferedInputStream sound = new BufferedInputStream(getClass().getResourceAsStream(soundFile)))
        {
            // Create and return a Clip that will play a sound file. There are
            // various reasons that the creation attempt could fail. If it
            // fails, return null.
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            return clip;
        }
        catch (LineUnavailableException e)
        {
            return null;
        }
        catch (IOException e)
        {
            return null;
        }
        catch (UnsupportedAudioFileException e)
        {
            return null;
        }
    }
    
    
    
    
}

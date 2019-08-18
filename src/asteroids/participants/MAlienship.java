package asteroids.participants;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.game.Controller;
import asteroids.game.Participant;
import static asteroids.game.Constants.*;
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
    private Clip AnnoyingSound; 
    private Clip destorySound; 
    private Timer AnnoyingTimer;
    
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
    
    AnnoyingSound= createClip("/sounds/saucerBig.wav");
    destorySound= createClip("/sounds/bangAlienShip.wav");
    AnnoyingTimer=new Timer(60000, this);
    AnnoyingTimer.start();
    AnnoyingSound.loop(1000000);
    }
   
    public void creatshape ()
    {
        Area tempA;
        CustomShape c= new CustomShape("src\\pic\\ufo2.png");
        //outline=new Ellipse2D.Double(0, 0, 60, 60);
        tempA=c.getArea_FastHack();

        
        AffineTransform at =AffineTransform.getTranslateInstance(-tempA.getBounds().getCenterX(), -tempA.getBounds().getCenterY());
        outline=tempA.createTransformedArea(at);
        
        
        
        
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
                controller.addScore(200);}
            
            controller.placeDust(this.getX(), this.getY());
            movetimer.stop();
            firetimer.stop();
            Participant.expire(this);
            controller.MAdestroy();
            destorySound.setFramePosition(0);
            destorySound.start();
            AnnoyingSound.stop();
            AnnoyingTimer.stop();
        }
        
    }
    @Override
    public void actionPerformed (ActionEvent e)
    {
        if(this.isExpired()) {
            movetimer.stop();
            firetimer.stop();  
            AnnoyingSound.stop();
            AnnoyingTimer.stop();
        }
        if(e.getSource() == movetimer) {
        setVelocity(speed,  direction+ curvelist[RANDOM.nextInt(3)]);}
        else if(e.getSource() == firetimer)
        {
            controller.placeMAlienBullet(this);
        }
        
        else if (e.getSource()== AnnoyingTimer)
        {
            AnnoyingSound.close();
            AnnoyingSound.loop(100000);
        }
        
        
        
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


    class CustomShape {

        private BufferedImage image=null;

        /**
         * Creates an Area with PixelPerfect precision
         * @param color The color that is draws the Custom Shape
         * @param tolerance The color tolerance
         * @return Area
         */
        public Area getArea(Color color, int tolerance) {
            if(image==null) return null;
            Area area = new Area();
            for (int x=0; x<image.getWidth(); x++) {
                for (int y=0; y<image.getHeight(); y++) {
                    Color pixel = new Color(image.getRGB(x,y));
                    if (isIncluded(color, pixel, tolerance)) {
                        Rectangle r = new Rectangle(x,y,1,1);
                        area.add(new Area(r));
                    }
                }
            }

            return area;
        }

        public Area getArea_FastHack() {
            //Assumes Black as Shape Color
            if(image==null) return null;

            Area area = new Area();
            Rectangle r;
            int y1,y2;

            for (int x=0; x<image.getWidth(); x++) {
                y1=99;
                y2=-1;
                for (int y=0; y<image.getHeight(); y++) {
                    Color pixel = new Color(image.getRGB(x,y));
                    //-16777216 entspricht RGB(0,0,0)
                    if (pixel.getBlue()!=0&& pixel.getGreen()!=0&& pixel.getRed()!=0) {
                        if(y1==99) {y1=y;y2=y;}
                        if(y>(y2+1)) {
                            r = new Rectangle(x,y1,1,y2-y1);
                            area.add(new Area(r)); 
                            y1=y;y2=y;
                        }
                        y2=y;
                    }               
                }
                if((y2-y1)>=0) {
                    r = new Rectangle(x,y1,1,y2-y1);
                    area.add(new Area(r)); 
                }
            }

            return area;
        }

        public boolean isIncluded(Color target, Color pixel, int tolerance) {
            int rT = target.getRed();
            int gT = target.getGreen();
            int bT = target.getBlue();
            int rP = pixel.getRed();
            int gP = pixel.getGreen();
            int bP = pixel.getBlue();
            return(
                (rP-tolerance<=rT) && (rT<=rP+tolerance) &&
                (gP-tolerance<=gT) && (gT<=gP+tolerance) &&
                (bP-tolerance<=bT) && (bT<=bP+tolerance) );
        }

        public CustomShape(String path) {
            try {
                BufferedImage image = ImageIO.read(new File(path));
                this.image = image;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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

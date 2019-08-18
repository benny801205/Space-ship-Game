package asteroids.participantsEnhance;

import static asteroids.gameEnhance.Constants.*;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import asteroids.destroyers.*;
import asteroids.gameEnhance.Controller;
import asteroids.gameEnhance.Participant;
import asteroids.gameEnhance.ParticipantCountdownTimer;


/**
 * Represents ships
 */
public class Ship extends Participant implements AsteroidDestroyer,ShipDestroyer
{
    /** The outline of the ship */
    private Shape outline;

    /** Game controller */
    private Controller controller;
    private int lives;
    private int Scores;
    private int usedscore;
    private boolean isDestructible=true;
    private BufferedImage image;
    /**
     * Constructs a ship at the specified coordinates that is pointed in the given direction.
     */
    public Ship (int x, int y, double direction, Controller controller ,Color color)
    {
        this.controller = controller;
        setPosition(x, y);
        setRotation(0* Math.PI);

        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(10, 0);
        poly.lineTo(-10, 10);
        poly.lineTo(0, 0);
        poly.lineTo(-10, -10);
        poly.closePath();
        
        
        withoutFlame();
        //creatshildship();
        setColor(color);
        // Schedule an acceleration in two seconds
        //new ParticipantCountdownTimer(this, "move", 2000);
        image=LoadImage("src\\pic\\flight-1.png");
        
    }

    public void withoutFlame()
    {
      /*  Path2D.Double poly = new Path2D.Double();
        poly.moveTo(15, 0);
        poly.lineTo(-20, 15);
        poly.lineTo(-10, 0);
        poly.lineTo(-20, -15);
        poly.closePath();
        outline=poly;//new Rectangle.Double(0, 0, 30, 30);
        */
        
        //outline=new Ellipse2D.Double(0, 0, 30, 30);
        Area tempA;
        CustomShape c= new CustomShape("src\\pic\\flight.png");
        //outline=new Ellipse2D.Double(0, 0, 60, 60);
        tempA=c.getArea_FastHack();

        System.out.println(tempA.getBounds().getWidth());
        System.out.println(tempA.getBounds().getHeight()); 
        AffineTransform at =AffineTransform.getTranslateInstance(-tempA.getBounds().getCenterX(), -tempA.getBounds().getCenterY());
        //at.scale(0.5, 0.5);
        outline=tempA.createTransformedArea(at);
        //outline=at.createTransformedShape(tempA);
        
    }
    
    
    public void creatwithflame()
    {
    /*    Path2D.Double poly = new Path2D.Double();
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
        outline=poly;*/
        
    }
    
    public void Initial(int live,int score) {
        this.lives=live;
        this.Scores=score;
    }
    
    
    public int getLives()
    {
        return this.lives;
    }
    
    public int getScore()
    {
        return this.Scores;
    }    
    
    public void addscore(int s)
    {
        this.Scores+=s;
        moreLives();
    }
    
    public void die()
    {
        this.lives--;
    }
    
    
    
    
    
    private void moreLives()
    {
       if(this.Scores-this.usedscore> 1000) {
        this.lives++;
        this.usedscore+=1000;
       }
    }
    
    
    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose is located.
     */
    public double getXNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getX();
    }

    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose is located.
     */
    public double getYNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getY();
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

    @Override
    public BufferedImage getPic()
    {
        System.out.println("ship");
        return image;
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
    public void accelerate ()
    {
        accelerate(SHIP_ACCELERATION);
    }

    public void brake ()
    {
        brake(SHIP_ACCELERATION);
    }
    
    
    public void openshiled()
    {
        
        creatshildship();//change shape
        isDestructible=false;//change to be indestructible 
        
    }
    
    public void closeshild()
    {
        isDestructible=true;
        withoutFlame();
    }
    
    
    private void creatshildship() {
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(15, 0);
        poly.lineTo(-20, 15);
        poly.lineTo(-10, 0);
        poly.lineTo(-20, -15);
        poly.closePath();
        Area aShape=new Area(poly);
        Area bShape=new Area(new Ellipse2D.Double(-30,-25,60,60));
        bShape.exclusiveOr(aShape);
        outline=bShape;//new Rectangle.Double(0, 0, 30, 30);
        
    }
    
    
    
    
    
    /**
     * When a Ship collides with a ShipDestroyer, it expires
     */
    @Override
    public void collidedWith (Participant p)
    {
        if(isDestructible) {
        if (p instanceof ShipDestroyer && p.getColor()!= this.getColor() && !(p instanceof Ship) )
        {
            if(p instanceof Bullet) {
                
                controller.addScore(((Bullet) p).getOwner(),2000);
            }
            // Expire the ship from the game
            Participant.expire(this);
            setVelocity(0, 0);
            // Tell the controller the ship was destroyed
            controller.shipDestroyed(this);
        }}
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
            accelerate();
            new ParticipantCountdownTimer(this, "move", 200);
        }
    }
    
    
    public BufferedImage LoadImage (String FileName)
    {
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File(FileName));
        }
        catch (IOException e)
        {
        }
        return img;
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
    
}

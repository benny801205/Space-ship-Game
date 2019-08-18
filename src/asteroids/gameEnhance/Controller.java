package asteroids.gameEnhance;

import static asteroids.gameEnhance.Constants.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;
import asteroids.participantsEnhance.AlianBullet;
import asteroids.participantsEnhance.Asteroid;
import asteroids.participantsEnhance.Bullet;
import asteroids.participantsEnhance.Dust;
import asteroids.participantsEnhance.MAlienship;
import asteroids.participantsEnhance.SAlienship;
import asteroids.participantsEnhance.Ship;
import asteroids.participantsEnhance.Wreckage;

/**
 * Controls a game of Asteroids.
 */
public class Controller implements KeyListener, ActionListener
{
    /** The state of all the Participants */
    private ParticipantState pstate;
    private CheatFunction cheatF;
    /** The ship (if one is active) or null (otherwise) */
    private Ship ship_1;
    private Ship ship_2;
    //private Flame flame;
    /** When this timer goes off, it is time to refresh the animation */
    private Timer refreshTimer;
    private Timer levelupTimer;
    private Random random;
    private Timer MAtimer;
    private Timer SAtimer;
    /**
     * The time at which a transition to a new stage of the game should be made. A transition is scheduled a few seconds
     * in the future to give the user time to see what has happened before doing something like going to a new level or
     * resetting the current level.
     */
    private long transitionTime;



    /** The game display */
    private Display display;

    //private int Score;

    //private int lives;
    private int Level;
    
    private int usedscore;
    
    /**
     * Constructs a controller to coordinate the game and screen
     */
    public Controller ()
    {
        random=new Random();
        // Initialize the ParticipantState
        pstate = new ParticipantState();
        
        // Set up the refresh timer.
        refreshTimer = new Timer(FRAME_INTERVAL, this);

        
        // Clear the transitionTime
        transitionTime = Long.MAX_VALUE;

        // Record the display object
        display = new Display(this);
        cheatF= new CheatFunction(this, display,pstate);
        // Bring up the splash screen and start the refresh timer
        splashScreen();
        display.setVisible(true);
        refreshTimer.start();
    }

    /**
     * Returns the ship, or null if there isn't one
     */
    public Ship getShip ()
    {
        return ship_1;
    }

    public void InitialScoreLv()
    {
        ship_1.Initial(3, 0);
        ship_2.Initial(3, 0);
        Level=1;
        usedscore=0;
 
    }
    
    public int getScore_1()
    {
        return ship_1.getScore();
    }
    
    public int getScore_2()
    {
        return ship_2.getScore();
    }
    
    public int getLives_1()
    {
        return ship_1.getLives();
    }
    
    public int getLives_2()
    {
        return ship_2.getLives();
    }
    
    
    public int getLevel()
    {
        return Level;
    }
    
    public void addScore(Ship p,int getScore)
    {
        p.addscore(getScore);
    }
    
 
    
    
    /**
     * Configures the game screen to display the splash screen
     */
    private void splashScreen ()
    {
        // Clear the screen, reset the level, and display the legend
        clear();
        display.setLegend("Enhance II");

        // Place four asteroids near the corners of the screen.
        placeAsteroids();

    }

    /**
     * The game is over. Displays a message to that effect.
     */
    private void finalScreen ()
    {
        display.setLegend(GAME_OVER);
        display.removeKeyListener(this);
    }

    /**
     * Place a new ship in the center of the screen. Remove any existing ship first.
     */
    private void placeShip_1 (Color color)
    {
      
        // Place a new ship
        Participant.expire(ship_1);
        ship_1 = new Ship((int)(SIZE*0.30), SIZE / 2, (-Math.PI)*0.5, this, color);
        addParticipant(ship_1);
        //Place second ship
 
    }

    
    private void placeShip_2(Color color)
    {
       Participant.expire(ship_2);
        ship_2 = new Ship((int)(SIZE*0.70), SIZE / 2, (-Math.PI)*0.5, this, color);
        addParticipant(ship_2);
        
    }
    
    
    
    public void reviveShip(Ship p)
    {
        p.revive();;
    }
    
    
    private void placeMAlien()
    {
        addParticipant(new MAlienship(this));
    }
    
    private void placeSAlien()
    {
        addParticipant(new SAlienship(this));
    }
    
    public void placeMAlienBullet(MAlienship p)
    {
        if(ship_1!=null) {
        addParticipant(new AlianBullet(p.getX(), p.getY(), 2*Math.PI*Math.random(), this, p.getColor()));
    }
        }
    
    
    public void placeSAlienBullet(SAlienship p)
    {
        
        if(!(ship_1.isExpired())||!(ship_2.isExpired())) {
        addParticipant(new AlianBullet(p.getX(), p.getY(),getCorrectDirection(ship_1.getX(),ship_1.getY(),p.getX(), p.getY()) , this, p.getColor()));
        }
    }
    
    
    private double getCorrectDirection (double x1,double y1,double x2,double y2)
    {
        double tan=(y1-y2)/(x1-x2);
        double dir1=Math.atan(tan);
        
        if(x2>x1) {
            dir1+=Math.PI;
        }
        
        
        return dir1;
    }
    
    
    /**
     * Places an asteroid near one corner of the screen. Gives it a random velocity and rotation.
     */
    private void placeAsteroids ()
    {
        if(ship_1==null&&Level>=1) {placeShip_1(shipP1_C);}
        
        System.out.println(Level);
        int extra = Level-1;
        addParticipant(new Asteroid(random.nextInt(3), 2, quarterLength, quarterLength, this));
        addParticipant(new Asteroid(random.nextInt(3), 2, quarterLength, SIZE-quarterLength, this));
        addParticipant(new Asteroid(random.nextInt(3), 2, SIZE-quarterLength, quarterLength, this));
        addParticipant(new Asteroid(random.nextInt(3), 2, SIZE-quarterLength, SIZE-quarterLength, this));
        for(int i=0;i<extra;i++)
        {
            
            switch(RANDOM.nextInt(4)){
                case 0:
                    addParticipant(new Asteroid(random.nextInt(3), 2, quarterLength, quarterLength, this));
                    break;
                case 1:
                    addParticipant(new Asteroid(random.nextInt(3), 2, quarterLength, SIZE-quarterLength, this));
                    break;
                case 2:
                    addParticipant(new Asteroid(random.nextInt(3), 2, SIZE-quarterLength, quarterLength, this));
                    break;
                case 3:
                    addParticipant(new Asteroid(random.nextInt(3), 2, SIZE-quarterLength, SIZE-quarterLength, this));
                    break;
               default:
                   throw new IllegalArgumentException();
                
            }
            
        }
        
        if(Level==2) {placeMAlien();}
        
        else if(Level>2) {
            placeSAlien();
        }
        
        
    
    }
    
    /**
     * Places an asteroid near one corner of the screen. Gives it a random velocity and rotation.
     */
    private void placeWreckage (Ship p)
    {
        addParticipant(new Wreckage(p.getX(),p.getY(),END_DELAY,this,p.getColor(),p));
    }
    

    
    
    /**
     * Add dusts.
     */
    public void placeDust (double d,double e,Color c)
    {

        for(int i =0;i<10;i++) {
        addParticipant(new Dust(d,e,Dust_Duration,this,c));
        }
        
    }
    
    
    
    
    /**
     * Places an Subasteroid near one corner of the screen. Gives it a random velocity and rotation.
     */
    public void placeSubAsteroids (double d ,double e, int size)
    {
        addParticipant(new Asteroid(random.nextInt(3), size,d, e, this));
    }
    
    

    /**
     * Places an asteroid near one corner of the screen. Gives it a random velocity and rotation.
     */
    private void placeBullet (Ship p)
    {
        addParticipant(new Bullet(p.getX(), p.getY(), p.getRotation(), BULLET_SPEED,BULLET_DURATION, this, shipP1_C,p));
    }
    
    /**
     * Clears the screen so that nothing is displayed
     */
    private void clear ()
    {
        try {
        MAtimer.stop();
        
        
        }
        catch (Exception e) {
            System.out.println("no timer");
        }
        try {
        
        SAtimer.stop();
        
        }
        catch (Exception e) {
         
        }
        
        
        
        
        
        pstate.clear();
        display.setLegend("");
        
    }

    
    public void clearforlevelup()
    {
        try {
        MAtimer.stop();
        SAtimer.stop();
        
        }
        catch (Exception e) {
            System.out.println("no timer");
        }

        pstate.clearNonShip();
        
        display.setLegend("");        
    }
    
    
    
    
    
    /**
     * Sets things up and begins a new game.
     */
    private void initialScreen ()
    {
        // Clear the screen
        clear();
        display.setLegend("");
        // Plac asteroids



        // Place the ship
        placeShip_1(shipP1_C);
        placeShip_2(shipP2_C);
        //placeMine();
        // Reset statistics
                InitialScoreLv();
        placeAsteroids();

        // Start listening to events (but don't listen twice)
        display.removeKeyListener(this);
        display.addKeyListener(this);

        // Give focus to the game screen
        display.requestFocusInWindow();

    }

    /**
     * Adds a new Participant
     */
    public void addParticipant (Participant p)
    {
        pstate.addParticipant(p);
    }

    /**
     * The ship has been destroyed
     */
    public void shipDestroyed (Ship p)
    {
        // Null out the ship
        //this.ship_1 = null;
        p.expire(p);
        if(p.getLives()>=1) {
            p.die();;
            placeWreckage(p);
            }
        else if((ship_1.getLives()<=0) && (ship_2.getLives()<=0)){
            pstate.finishclear();
            display.setLegend("Game Finished");
            display.TerminateTimer();
            display.removeKeyListener(this);


            
        }
        else {
            p.expire(p);
            placeDust(p.getX(), p.getY(),p.getColor());
        }
        
        
        
        // Display a legend
        //display.setLegend("Ouch!");

        // Decrement lives
        

        // Since the ship was destroyed, schedule a transition
       
        
      //  scheduleTransition(END_DELAY);
    }

    public void MAdestroy()
    {
        MAtimer=new Timer((RANDOM.nextInt(6000)+5000), this);
        MAtimer.start();
        
    }
    
    public void SAdestroy()
    {
        SAtimer=new Timer((RANDOM.nextInt(6000)+5000), this);
        SAtimer.start();
        
    }
    
    /**
     * An asteroid has been destroyed
     */
    public void asteroidDestroyed ()
    {
        // If all the asteroids are gone, schedule a transition
        if (pstate.countAsteroids() == 0)
        {
            clearforlevelup();

            //set level up
            Level++;
            display.setLegend("Level up!");
            
            levelupTimer=new Timer(END_DELAY, this);
            levelupTimer.start();
            scheduleTransition(END_DELAY);
        }
    }

    /**
     * Schedules a transition m msecs in the future
     */
    private void scheduleTransition (int m)
    {
        
        transitionTime = System.currentTimeMillis() + m;
    }

    /**
     * This method will be invoked because of button presses and timer events.
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {
        // The start button has been pressed. Stop whatever we're doing
        // and bring up the initial screen
        //System.out.println(e.getActionCommand());
        if ( e.getActionCommand()=="RESTART")
        {
            display.addStateLabel();
            display.addtimer();
            //display.stopSGfuntion();
            initialScreen();
        }

        // Time to refresh the screen and deal with keyboard input
        else if (e.getSource() == refreshTimer)
        {

            // It may be time to make a game transition
            performTransition();
            
            // Move the participants to their new locations
            pstate.moveParticipants();

            // Refresh screen
            display.refresh();
        }
        
        else if (e.getSource() == levelupTimer) {
            levelupTimer.stop();
            display.setLegend("");
            placeAsteroids();
            
        }
        else if (e.getSource() == MAtimer) {
            MAtimer.stop();
            placeMAlien();
            
        }
        
        else if (e.getSource() == SAtimer) {
            SAtimer.stop();
            placeSAlien();
            
        }
        
        
        
    }

    /**
     * Returns an iterator over the active participants
     */
    public Iterator<Participant> getParticipants ()
    {
        return pstate.getParticipants();
    }

    
    public LinkedList<Participant> getParticipantsList ()
    {
        return pstate.getParticipantsLIST();
    }
    
    
    /**
     * If the transition time has been reached, transition to a new state
     */
    private void performTransition ()
    {
        // Do something only if the time has been reached
        if (transitionTime <= System.currentTimeMillis())
        {
            // Clear the transition time
            transitionTime = Long.MAX_VALUE;

            // If there are no lives left, the game is over. Show the final
            // screen.
/*            if (lives <= 0)
            {
                finalScreen();
            }*/
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    //////////
    
    
    /**
     * If a key of interest is pressed, record that it is down.
     */
    @Override
    public void keyPressed (KeyEvent e)
    {
        if (e.getKeyCode() == 68 && !ship_1.isExpired())//A
        {
            ship_1.turnRight();
        }
        else if (e.getKeyCode() == 65 && !ship_1.isExpired())//D
        {
            ship_1.turnLeft();
        }
    
        else if (e.getKeyCode() == 87 && !ship_1.isExpired())//W
        {
            ship_1.accelerate();
            //flame.setColor(Color.red);
            ship_1.creatwithflame();
/*            flame.setPosition(ship.getX(), ship.getY());
            flame.setRotation(ship.getRotation());
            flame.setSpeed(ship.getSpeed());*/
        //    flame.revive();
           // display.refresh();
            
        }
        
        else if (e.getKeyCode() == 83 && !ship_1.isExpired())//S
        {
            ship_1.brake();
            
        }
        else if (e.getKeyCode() == 69 && !ship_1.isExpired())//E
        { 
            if(pstate.countBullets()<BULLET_LIMIT) {
           placeBullet(ship_1);}
            
        }
        else if (e.getKeyCode() == 79 && !ship_2.isExpired())//O
        { 
            if(pstate.countBullets()<BULLET_LIMIT) {
           placeBullet(ship_2);}
            
        }
        
        else if (e.getKeyCode() == 73 && !ship_2.isExpired())//I
        {
            ship_2.accelerate();
            ship_2.creatwithflame();
        }
        else if (e.getKeyCode() == 75 && !ship_2.isExpired())//k
        {
            ship_2.brake();
        }
        else if (e.getKeyCode() == 74 && !ship_2.isExpired())//J
        {
            ship_2.turnLeft();;
        }
        else if (e.getKeyCode() == 76 && !ship_2.isExpired())//L
        {
            ship_2.turnRight();
        }
        
        else if (e.getKeyCode() == 192 )//` clean 
        {
           // System.err.println("cheat");
            cheatF.cleanAsandLVup();
        }

        else if (e.getKeyCode() == 81 && !ship_1.isExpired())//q
        {
           // System.err.println("cheat");
            ship_1.openshiled();
        }
        else if (e.getKeyCode() == 85 && !ship_2.isExpired())//u
        {
           // System.err.println("cheat");
            ship_2.openshiled();
        }
        
        else {
            //System.out.println(e.getKeyChar());
        }
        
        
        
        
        
    }

    /**
     * These events are ignored.
     */
    @Override
    public void keyTyped (KeyEvent e)
    {

    
    
    }

    /**
     * These events are ignored.
     */
    @Override
    public void keyReleased (KeyEvent e)
    {
        
        if (e.getKeyCode() == 87 && !ship_2.isExpired())
        {
           // flame.setColor(Color.black);
            ship_1.withoutFlame();
            
        }
        else if (e.getKeyCode() == 73 && !ship_2.isExpired())
        {
           // flame.setColor(Color.black);
            ship_2.withoutFlame();
            
        }
        
        else if (e.getKeyCode() == 81 && !ship_1.isExpired())//q clean 
        {
           // System.err.println("cheat");
            ship_1.closeshild();
        }
        else if (e.getKeyCode() == 85 && !ship_2.isExpired())//u clean 
        {
           // System.err.println("cheat");
            ship_2.closeshild();
        }
        
        
        
        
    }
}

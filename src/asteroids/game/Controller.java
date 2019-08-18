package asteroids.game;

import static asteroids.game.Constants.*;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import asteroids.participants.AlianBullet;
import asteroids.participants.Asteroid;
import asteroids.participants.Bullet;
import asteroids.participants.Dust;
import asteroids.participants.Flame;
import asteroids.participants.MAlienship;
import asteroids.participants.Mine;
import asteroids.participants.SAlienship;
import asteroids.participants.Ship;
import asteroids.participants.Wreckage;
import asteroids.participants.*;
/**
 * Controls a game of Asteroids.
 */
public class Controller implements  ActionListener
{
    /** The state of all the Participants */
    private ParticipantState pstate;

    /** The ship (if one is active) or null (otherwise) */
    private Ship ship;
   // private Score scoreShape;
    private Flame flame;
    /** When this timer goes off, it is time to refresh the animation */
    private Timer refreshTimer;
    private Timer levelupTimer;
    private Random random;
    private Timer MAtimer;
    private Timer SAtimer;
    private Map<String, String> pressedKeys = new HashMap<String, String>();
    /**
     * The time at which a transition to a new stage of the game should be made. A transition is scheduled a few seconds
     * in the future to give the user time to see what has happened before doing something like going to a new level or
     * resetting the current level.
     */
    private long transitionTime;

    /** Number of lives left */
    private int lives;

    /** The game display */
    private Display display;

    private int Score;
    
    private int Level;
    
    private int usedscore;
    
    private Clip bulletSound;
    
    
    /**
     * Constructs a controller to coordinate the game and screen
     */
    public Controller ()
    {
        random=new Random();
        // Initialize the ParticipantState
        pstate = new ParticipantState();
        //create voice
        bulletSound=createClip("/sounds/fire.wav");
        
        
        
        
        
        //////////////
        // Set up the refresh timer.
        refreshTimer = new Timer(FRAME_INTERVAL, this);

        
        // Clear the transitionTime
        transitionTime = Long.MAX_VALUE;

        // Record the display object
        display = new Display(this);

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
        return ship;
    }

    public void InitialScoreLv()
    {
        Score=0;
        Level=1;
        usedscore=0;
        lives = 3;
        display.setLv(Level);
        display.setS(Score);
        display.setLives(lives);
    
    }
    
    public int getScore()
    {
        display.setS(Score);
        return Score;
    }
    
    public int getLives()
    {
        return lives;
    }
    
    public int getLevel()
    {
        return Level;
    }
    
    
    public void addScore(int score)
    {
        Score+=score;
      //  scoreShape.renew(Score);
        //moreLives();
    }
    
    private void moreLives()
    {
       if(Score-usedscore> 10000) {
        lives++;
        display.setLives(lives);
        usedscore+=10000;
       }
    }
    
    
    /**
     * Configures the game screen to display the splash screen
     */
    private void splashScreen ()
    {
        // Clear the screen, reset the level, and display the legend
        clear();
        display.setLegend("Asteroids");

        // Place four asteroids near the corners of the screen.
        placeAsteroids();

    }

    /**
     * The game is over. Displays a message to that effect.
     */
    private void finalScreen ()
    {
        display.setLegend(GAME_OVER);
       // display.removeKeyListener(this);
    }

    /**
     * Place a new ship in the center of the screen. Remove any existing ship first.
     */
    private void placeShip (Color color)
    {
       
        // Place a new ship
        Participant.expire(ship);
        ship = new Ship(SIZE / 2, SIZE / 2, (-Math.PI), this, color);
        addParticipant(ship);
        display.setLegend("");
    }


    
    
    public void reviveShip(Color color)
    {
        placeShip (color);
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
        if(ship!=null) {
        addParticipant(new AlianBullet(p.getX(), p.getY(), 2*Math.PI*Math.random(), this, p.getColor()));
    }
        }
    
    
    public void placeSAlienBullet(SAlienship p)
    {
        
        if(ship!=null) {
        addParticipant(new AlianBullet(p.getX(), p.getY(),getCorrectDirection(ship.getX(),ship.getY(),p.getX(), p.getY()) , this, p.getColor()));
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
        if(ship==null&&Level>=1) {placeShip(shipP1_C);}
        
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
        addParticipant(new Wreckage(p.getX(),p.getY(),END_DELAY,this,p.getColor()));
    }
/*    private void placeScore ()
    {
        scoreShape=new Score(35,80,this);
        
        addParticipant(scoreShape);
    }
*/
    
    
    /**
     * Add dusts.
     */
    public void placeDust (double d,double e)
    {

        for(int i =0;i<10;i++) {
        addParticipant(new Dust(d,e,Dust_Duration,this,Asteroid_C));
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
    private void placeBullet ()
    {
        if(pstate.countBullets()<BULLET_LIMIT) {
        addParticipant(new Bullet(ship.getX(), ship.getY(), ship.getRotation(), BULLET_SPEED,BULLET_DURATION, this, shipP1_C));
        bulletSound.setFramePosition(0);
        bulletSound.start();
        
        }
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
                  }
        try {
        SAtimer.stop();
        }
        catch (Exception e) {
                  }
        
        
        
        
        
        pstate.clear();
        display.setLegend("");
        ship = null;
    }

    /**
     * Sets things up and begins a new game.
     */
    private void initialScreen ()
    {
        // Clear the screen
        clear();

        // Plac asteroids
        placeAsteroids();

        //placeSAlien();
        // Place the ship
        placeShip(shipP1_C);
        //placeMine();
        // Reset statistics
     // placeScore();
        display.getStart();
        // Start listening to events (but don't listen twice)
      //  display.removeKeyListener(this);
       // display.addKeyListener(this);

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
        this.ship = null;
        lives--;
        display.setLives(lives);
        if(lives>=1) {
            
            placeWreckage(p);
            }
        else {
            
            display.setLegend("Game Over");
            display.TerminateTimer();
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
            clear();

            //set level up
            Level++;
            display.setLegend("Level up!");
            display.setLv(Level);
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
            InitialScoreLv();
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
            
            moveship();
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
            if (lives <= 0)
            {
                finalScreen();
            }
        }
    }

    private void moveship()
    {
        for (String s : pressedKeys.values())
        {
            if(ship!=null) {
            if(s.equals("up")) {ship.accelerate(SHIP_ACCELERATION);}
            else if(s.equals("left")) {ship.rotate(-Math.PI / 16);}
            else if(s.equals("right")) {ship.rotate(Math.PI / 16);}
            else if(s.equals("down")) {placeBullet();}
            else if(s.equals("space")) {placeBullet();}
        
            else {System.out.println("useless");}
    
            
            
        }
            }
        
    }
    
    
    private void handleKeyEvent(String key, String value)
    {
        //  Keep track of which keys are pressed

        if (value == null)
            pressedKeys.remove( key );
        else
            pressedKeys.put(key, value);

        //  Start the Timer when the first key is pressed

/*        if (pressedKeys.size() == 1)
        {
            timer.start();
        }

        //  Stop the Timer when all keys have been released

        if (pressedKeys.size() == 0)
        {
            timer.stop();
        }*/
    }
    
    
    public class actionkeysave extends AbstractAction implements ActionListener{
        private String key;
        private String command;
        public actionkeysave (String key,String com)
        {
            this.key=key;
            this.command=com;
            
        }
        @Override
        public void actionPerformed (ActionEvent e)
        {
            handleKeyEvent(key, command);
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
        
        
        
}

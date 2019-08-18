package asteroids.game;

import javax.swing.*;
import asteroids.participants.Flame;
import asteroids.participants.Ship;
import static asteroids.game.Constants.*;
import java.awt.*;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Defines the top-level appearance of an Asteroids game.
 */
@SuppressWarnings("serial")
public class Display extends JFrame
{
    /** The area where the action takes place */
    private Screen screen;
    private JButton startGame;
    private JPanel controls;
    private JLabel TimeLable;
    private int timeeee=0;
    private Timer timer;
    private JLabel Levellabel;
    private JPanel State;
    private JLabel scorelabel;
    private JLabel Livelabel;
    private JPanel mainPanel;
    private Controller controller;
    
    /**
     * Lays out the game and creates the controller
     */
    public Display (Controller controller)
    {
        // Title at the top
        setTitle(TITLE);
        this.controller=controller;
        // Default behavior on closing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main playing area and the controller
        screen = new Screen(controller);

        // This panel contains the screen to prevent the screen from being
        // resized
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new GridBagLayout());
        screenPanel.add(screen);

        // This panel contains buttons and labels
        controls = new JPanel();
        controls.setLayout(new GridLayout(1,3));
        // The button that starts the game
        startGame = new JButton(START_LABEL);
        TimeLable = new JLabel("Time: 0s");
        Levellabel=new JLabel("Level: 1");
        controls.add(startGame);
        
        State=new JPanel();
        State.setLayout(new GridLayout(1,4));
        scorelabel = new JLabel("Score: 0");
        Livelabel = new JLabel("Lives: 3");
        State.add(scorelabel);
        State.add(Livelabel);
        
        //controls.add(TimeLable);
        // Organize everything
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(screenPanel, "Center");
        mainPanel.add(controls, "North");
        setContentPane(mainPanel);
        pack();
        timer = null;
        // Connect the controller to the start button
        startGame.addActionListener(controller);
        startGame.setActionCommand("RESTART");
    }

    public void getStart()
    {
        
        screen.isstart=true;
    }
    
    
    /**
     * Called when it is time to update the screen display. This is what drives the animation.
     */
    public void refresh ()
    {
        TimeLable.setText("Time: "+ timeeee +"s");
        
        Levellabel.setText("Level: "+controller.getLevel());
        Livelabel.setText("Lives: "+ controller.getLives());
        scorelabel.setText("Score: "+ controller.getScore());
        
        screen.repaint();
    }

    /**
     * Sets the large legend
     */
    public void setLegend (String s)
    {
        screen.setLegend(s);
    }
    
    public void setLv(int lv)
    {
     screen.setLV(lv);
     
    }
    public void setLives(int l)
    {
     screen.setLives(l);
     
    }
    public void setS(int S)
    {
     screen.setS(S);
     
    }
    public void stopSGfuntion()
    {
        startGame.setEnabled(false);

    }
    
    public void TerminateTimer()
    {
        timer.cancel();
    }
    
    
    public void addtimer()
    {
        
        if(timer!=null) {
            TerminateTimer();
        }
        timeeee=0;
        timer=new Timer();
        timer.schedule(new MyTask(), 1000, 1000);
        controls.add(Levellabel);
        controls.add(TimeLable);
        controls.validate();
        refresh();
    }
    
    public void addStateLabel()
    {
        mainPanel.add(State,"South");
        mainPanel.validate();
        refresh();
    }
    
    
    
    
    class MyTask extends TimerTask {

        @Override
        public void run() {
           // System.out.println("Hello world from Timer task!");
            timeeee++;
            refresh();
        }
    }
    
 
    }
    
    
    
    


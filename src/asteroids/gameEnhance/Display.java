package asteroids.gameEnhance;

import javax.swing.*;
import static asteroids.gameEnhance.Constants.*;
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
    private JLabel scorelabel_1;
    private JLabel Livelabel_1;
    private JPanel mainPanel;
    private Controller controller;
    
    private JLabel scorelabel_2;
    private JLabel Livelabel_2;
    
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
        scorelabel_1 = new JLabel("Score: 0");
        Livelabel_1 = new JLabel("Lives: 3");
        scorelabel_2 = new JLabel("Score: 0");
        Livelabel_2 = new JLabel("Lives: 3");
        State.add(scorelabel_1);
        State.add(Livelabel_1);
        State.add(scorelabel_2);
        State.add(Livelabel_2);
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

    /**
     * Called when it is time to update the screen display. This is what drives the animation.
     */
    public void refresh ()
    {
        TimeLable.setText("Time: "+ timeeee +"s");
        
        Levellabel.setText("Level: "+controller.getLevel());
        
        try {
        Livelabel_1.setText("P1_Lives: "+ controller.getLives_1());
        scorelabel_1.setText("P1_Score: "+ controller.getScore_1());
        Livelabel_2.setText("P2_Lives: "+ controller.getLives_2());
        scorelabel_2.setText("P2_Score: "+ controller.getScore_2());
        
        
        }catch (Exception e) {
            // TODO: handle exception
        }
        
        screen.repaint();
    }

    /**
     * Sets the large legend
     */
    public void setLegend (String s)
    {
        screen.setLegend(s);
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
    
    
    
    


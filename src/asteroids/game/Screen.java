package asteroids.game;

import static asteroids.game.Constants.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Iterator;
import javax.swing.*;


/**
 * The area of the display in which the game takes place.
 */
@SuppressWarnings("serial")
public class Screen extends JPanel
{
    /** Legend that is displayed across the screen */
    private String legend;
    public boolean isstart=false;
    /** Game controller */
    private Controller controller;
    private int lives;
    private int Level;
    private int Scores;
    private Shape Ship;
    private Graphics2D g;
    /**
     * Creates an empty screen
     */
    public Screen (Controller controller)
    {
        this.controller = controller;
        createShip();
        setKeyBinding();
        legend = "";
        setPreferredSize(new Dimension(SIZE, SIZE));
        setMinimumSize(new Dimension(SIZE, SIZE));
        setBackground(Color.black);
        setForeground(Color.white);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 120));
        setFocusable(true);
    }

    public void createShip()
    {
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(22, 0);
        poly.lineTo(-20, 12);
        //poly.lineTo(-20, 0);
        poly.lineTo(-20, -12);
        poly.closePath();
        Ship=poly;
        
        
    }
    
    
    
    /**
     * Set the legend
     */
    public void setLegend (String legend)
    {
        this.legend = legend;
    }
    public void setLV (int S)
    {
        this.Level=S;
    }
    
    public void setS (int s)
    {
        this.Scores=s;
    }
    public void setLives (int l)
    {
        this.lives=l;
    }
    
    
    public void setKeyBinding()
    {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(40, 0, false), "down-pressed");
        im.put(KeyStroke.getKeyStroke(40, 0, true), "down-released");
        im.put(KeyStroke.getKeyStroke(38, 0, false), "up-pressed");
        im.put(KeyStroke.getKeyStroke(38, 0, true), "up-released");
        im.put(KeyStroke.getKeyStroke(37, 0, false), "left-pressed");
        im.put(KeyStroke.getKeyStroke(37, 0, true), "left-released");
        im.put(KeyStroke.getKeyStroke(39, 0, false), "right-pressed");
        im.put(KeyStroke.getKeyStroke(39, 0, true), "right-released");
        im.put(KeyStroke.getKeyStroke(32, 0, false), "space-pressed");
        im.put(KeyStroke.getKeyStroke(32, 0, true), "space-released");
        
        
        am.put("up-pressed", controller.new actionkeysave("up","up"));
        am.put("up-released", controller.new actionkeysave("up",null));


        am.put("left-pressed", controller.new actionkeysave("left","left"));
        am.put("left-released", controller.new actionkeysave("left",null));
        am.put("right-pressed", controller.new actionkeysave("right","right"));
        am.put("right-released", controller.new actionkeysave("right",null));
        am.put("down-pressed", controller.new actionkeysave("down","down"));
        am.put("down-released", controller.new actionkeysave("down",null));
        am.put("space-pressed", controller.new actionkeysave("space","space"));
        am.put("space-released", controller.new actionkeysave("space",null));
 
        

        
    }
    

    
    
    
    
    
    /**
     * Paint the participants onto this panel
     */
    @Override
    public void paintComponent (Graphics graphics)
    {
        // Use better resolution
        g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
       
        // Do the default painting
        super.paintComponent(g);

        // Draw each participant in its proper place
        Iterator<Participant> iter = controller.getParticipants();
        while (iter.hasNext())
        {
            iter.next().draw(g);
        }

        // Draw the legend across the middle of the panel
        int size = g.getFontMetrics().stringWidth(legend);
        g.setPaint(Color.white);
        g.drawString(legend, (SIZE - size) / 2, SIZE / 2);
        
        if(isstart) 
        {
            drawLV(Level);
            drawS(Scores);
            
            drawLives(lives);
            
        }
        
        

        
        
        
    }
    public void drawLV(int lv)
    {
        
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 0.5F);
        g.setFont(newFont);
        g.setPaint(Color.white);
        g.drawString(lv+"", 680, 70);
    }
    public void drawS(int s)
    {
        
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1F);
        g.setFont(newFont);
        g.setPaint(Color.white);
        g.drawString(s+"", 30, 70);
    }
    
    public void drawLives(int lives)
    {
        g.setPaint(Color.white);
        
        for(int i =0;i<lives;i++) {
        Shape original=Ship;
        AffineTransform trans = AffineTransform.getTranslateInstance(30+i*33, 105);
        trans.concatenate(AffineTransform.getRotateInstance(-Math.PI*0.5));
        g.draw(trans.createTransformedShape(original));
        }
        
    }
}

package asteroids.participants;


import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import asteroids.game.Controller;
import asteroids.game.Participant;

import java.awt.Dimension;

import javax.swing.JPanel;
public class Score extends Participant 
{

    /** The outline of the asteroid */
    private Shape outline;
    private int Scores;
    /** The game controller */
    private Controller controller;
    private CreateShape createShape;
 
    public Score(double x ,double y, Controller controller)
    {
        Scores=0;
        setColor(Color.white);
        setVelocity(0, 2 * Math.PI);
        setRotation(2 * Math.PI );
        this.controller = controller;
        setPosition(x, y);
        createShape =new CreateShape();
        createShape.getshape(Scores+"");
        
    }

    
  
    public void renew(int s)
    {
        
        //this.Scores=s;
        createShape.getshape(s+"");
        //System.out.println(this.Scores);
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

    public class CreateShape extends JPanel{
        private Shape s;
        public CreateShape() {

            
        }
        public void getshape(String str)
        {
            Font f = getFont().deriveFont(Font.BOLD, 70);
            GlyphVector v = f.createGlyphVector(getFontMetrics(f).getFontRenderContext(), str);
            outline = v.getOutline();
            //setPreferredSize(new Dimension(300,300));
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

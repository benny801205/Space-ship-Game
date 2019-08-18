package asteroids.gameEnhance;

import asteroids.participantsEnhance.Ship;

public class CheatFunction
{
    private Controller controller;
    private Display display;
    private ParticipantState pstate;
    public CheatFunction(Controller c, Display d,ParticipantState s)
    {
        this.controller=c;
        this.display=d;
        this.pstate=s;
    }
    
    

    ////special function
    
    public void cleanAsandLVup()
    {
        
        pstate.clearNonShip();
        controller.asteroidDestroyed();
        
        
    }
    public void shield(Ship p)
    {
        
        
        
    }
    
    
    
    
    
    
    
}

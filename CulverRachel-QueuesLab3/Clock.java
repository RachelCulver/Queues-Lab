
/**
 * holds methods that act on clocks that work within the system.
 * 
 * @author Culver, Rachel 
 * @version 10/22/2017
 */
public class Clock
{
    private int ticks;

    /**
     * Constructor for objects of class Clock
     */
    public Clock()
    {
        ticks = 0;
    }

    /**
     * Inrements the int variable by one
     *
     * @param i number of clock ticks 
     * @return  int returns incremented ticks of clock
     */
    public int increment(int i)
    {
        ticks = i;
        ++ticks;
        return ticks;
    }
    
    /**
     * Decrements the int variable by one
     *
     * @param d number of clock ticks 
     * @return  int returns decremented ticks of clock
     */
    public int decrement(int d) {
        ticks =  d;
        --ticks;
        return ticks;
    }
    
    
}

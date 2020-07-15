import java.util.Arrays;
/**
 * Creates Job objects from a file's input. 
 * 
 * @author Culver, Rachel
 * @version 10/22/2017
 */
public class Job
{
    private String line;
    private int pid;
    private int arrivalTime;
    private int cpuTimeRequired;
    private int currentQueue;
    
    /**
     * Constructor for objects of class Job
     */
    public Job()
    {
    pid = 0;
    arrivalTime = 0;
    cpuTimeRequired = 0;
    currentQueue = 0;
    }
    
    /**
     * Parses through a string dividing the string in to individual words then assigns each word to a variable.
     *
     * @param a  a line of text from a file containing job information
     */
    public void parseString(String a) {
        line = a;
        String spaces = "[ ]+";
        String[] words = line.split(spaces);
        arrivalTime = Integer.parseInt(words[0]);
        pid = Integer.parseInt(words[1]);
        cpuTimeRequired = Integer.parseInt(words[2]);
    }
   
    /**
     * Method getPID returns the process ID of the job
     *
     * @return process ID of the job
     */
    public int getPID() {
         return pid;
    }
    
    /**
     * Returns time job arrives in system
     *
     * @return  arrival time of job to system
     */
    public int getArrivalTime() {
        return arrivalTime;
    }
    
    /**
     * Returns the time required by the job on the cpu
     *
     * @return  time required on cpu
     */
    public int getCpuTimeRequired() {
        return cpuTimeRequired;
    }
    
    /**
     * Sets the current queue the job is in
     *
     * @param q  current queue of job
     */
    public void setCurrentQ(int q) {
        currentQueue = q;
    }
    
    /**
     * Returns the current queue the job is in.
     *
     * @return   current queue of job
     */
    public int getCurrentQ() {
        return currentQueue;
    }
    
    /**
     * Sets the cpu time required by the job
     *
     * @param i   cpu time required by the job
     */
    public void setCpuTimeRequired(int i) {
        cpuTimeRequired = i;
    }
}

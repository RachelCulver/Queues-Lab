import java.io.*;
/**
 * Driver implements class MFQ to run multi-level feedback queue simulation.
 * 
 * @author Stegman, Richard
 * 
 * @version 10/22/2017
 */
public class Driver
{
    public static void main(String[] args) throws IOException{
        PrintWriter pw = new PrintWriter(new FileWriter("csis.txt"));
        MFQ mfq = new MFQ(pw);
        mfq.getJobs();
        mfq.outputHeader();
        mfq.runSimulation();
        mfq.outStats();
        pw.close();
    }
}

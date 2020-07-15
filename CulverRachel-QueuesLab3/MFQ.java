import java.util.Scanner;
import java.io.*;
/**
 * MFQ class moves Job objects through a multi-level feedback queue. 
 * Recording Jobs arrivals and departures into and out of the system to the terminal window and to a file.
 * 
 * @author Culver, Rachel 
 * ID# 009164179
 * @version 10/22/17
 */
public class MFQ
{
    private Job job = new Job();
    private ObjectQueue Q1= new ObjectQueue();
    private ObjectQueue Q2 = new ObjectQueue();
    private ObjectQueue Q3 = new ObjectQueue();
    private ObjectQueue Q4 = new ObjectQueue();
    private ObjectQueue inputQ = new ObjectQueue();
    private Clock clock = new Clock();
    private CPU cpu = new CPU();
    private PrintWriter pw;
    private Scanner fileScan = new Scanner(new File("mfq.txt"));
    private int totalNumOfJobs;
    private int totalTimeInSyst;
    private double aveResponseTime;
    private double aveTurnaroundTime;
    private double aveWaitTime;
    private int waitTime;
    private int jobsThroughSyst;
    private double aveThroughput;
    private int ticks;
    private int identify;
    
     /**
     * Constructor for objects of class MFQ
     * 
     * * @param   pw     PrintWriter object passed in as parameter
     */
     public MFQ (PrintWriter pw) throws IOException {
     this.pw = pw;
     ticks = 0;  
     identify = 0;
     totalNumOfJobs = 0;
     totalTimeInSyst = 0;
     aveResponseTime = 0.0;
     aveTurnaroundTime = 0.0;
     waitTime = 0;
     jobsThroughSyst = 0;
     aveThroughput = 0.0;
    }
    
    /**
     * Method outStats outputs the statistics to the terminal window and to a file.
     *
     */
    public void outStats() {
        aveTurnaroundTime = totalTimeInSyst / totalNumOfJobs;
        aveWaitTime = waitTime / totalNumOfJobs;
        aveThroughput = totalNumOfJobs /  jobsThroughSyst;
        
        System.out.println(" ");
        System.out.println("Statistics");
        System.out.println("Total Number of Jobs: " + totalNumOfJobs);
        System.out.println("Total Time of All Jobs in system " + totalTimeInSyst);
        System.out.println("Average Response Time: " + aveResponseTime);
        System.out.println("Average Turnaround Time " + aveTurnaroundTime);
        System.out.println("Average Wait Time: " + aveWaitTime);
        System.out.println("Average Throughput: " + aveThroughput);
        pw.println(" ");
        pw.println("Statistics");
        pw.println("Total Number of Jobs: " + totalNumOfJobs);
        pw.println("Total Time of All Jobs in System " + totalTimeInSyst);
        pw.println("Average Response Time: " + aveResponseTime);
        pw.println("Average Turnaround Time " + aveTurnaroundTime);
        pw.println("Average Wait Time: " + aveWaitTime);
        pw.println("Average Throughput: " + aveThroughput);
    }
    
    /**
     * Method getJobs scans jobs in from a file, inputs them into a queue, and sends file input to a file.
     *
     */
    public void getJobs() {
        pw.println("Data from Input File");
        while (fileScan.hasNext()){
            Job job = new Job();    
            String buf = fileScan.nextLine();
            job.parseString(buf);
            inputQ.insert(job);
            pw.println(job.getArrivalTime() + " " + job.getPID() + " " + job.getCpuTimeRequired());
            ++totalNumOfJobs;
        }
        pw.println(" ");
    }
   
     /**
      * Method outputHeader prints header to the terminal window and sends it to a file.
      *
      */
     public void outputHeader(){
        System.out.println("\t\t\tSystem\t\t\t\tCPU Time\tTotal Time\tLowest Level");
        System.out.println("Event\t\t\tTime\t\tPID\t\tNeeded\t\tin System\tQueue");
        System.out.println(" ");
        pw.println("\t\t\tSystem\t\t\t\tCPU Time\tTotal Time\tLowest Level");
        pw.println("Event\t\t\tTime\t\tPID\t\tNeeded\t\tin System\tQueue");
        pw.println(" ");
         }
    
  
    /**
     * Method runSimulation runs jobs through a simulation of a multi-level feedback queue.
     *
     */
    public void runSimulation(){
        while(!inputQ.isEmpty() || !Q1.isEmpty() || !Q2.isEmpty() || !Q3.isEmpty() || !Q4.isEmpty() || cpu.busyFlag() == true) {
           ticks = clock.increment(ticks);
           submitNewJob(); 
           checkCPUbusy();
           checkJobFinished();
           }
   }
   
   
   /**
    * Method submitNewJob sumbits a new job from input queue to queue 1, outputs arrival to the terminal window and to a file. Also tracks number of jobs input into the system.
    *
    */

   private void submitNewJob() {
     if(!inputQ.isEmpty()) {
        Job front = (Job) inputQ.query();
        if(front.getArrivalTime() == ticks) {   
            front = (Job) inputQ.remove(); 
            Q1.insert(front);
            System.out.println("Arrival \t\t" + front.getArrivalTime() + "\t\t" + front.getPID() + "\t\t" + front.getCpuTimeRequired());
            pw.println("Arrival \t\t" + front.getArrivalTime() + "\t\t" + front.getPID() + "\t\t" + front.getCpuTimeRequired());
            aveResponseTime++;
        }
     }
    }
  
   /**
    * Method checkCPUbusy checks whether CPU is busy. If busy decremnts job clock and quantum clock. If not busy sends job to cpu.
    *
    */
   private void checkCPUbusy() {
       if(cpu.busyFlag() == false) {
           submitJobtoCpu();
       }
       else if (cpu.busyFlag() == true){
           int cpuTime = cpu.getQclock(); 
           cpuTime = clock.decrement(cpuTime);
           cpu.setQclock(cpuTime);
           int jobClock = (Integer) job.getCpuTimeRequired();
           jobClock = clock.decrement(jobClock);
           job.setCpuTimeRequired(jobClock);      
        }
    }
   
   /**
    * Method checkJobFinished checks if job clock has reached zero. If zero the job is taken off the cpu and a message of its departure is printed to the terminal window and sent to a file. checks if new job enters system job on cpu is preemted and incoming job is sent to cpu. Checks if quantum clock is zero job is preemted job from queue 1, 2, 3, or 4 is sent to cpu.
    *
    */
    private void checkJobFinished() {
        if(job.getCpuTimeRequired() == 0 && cpu.busyFlag() == true) {
            cpu.setBusyFlag(false);
            int arrival = (Integer) job.getArrivalTime();
            int total = ticks - arrival;
            totalTimeInSyst = total + totalTimeInSyst;
            System.out.println("Departure\t\t" + ticks + " \t\t"+ job.getPID() + "\t\t\t\t" + total + "\t\t" + job.getCurrentQ());
            pw.println("Departure\t\t" + ticks + " \t\t"+ job.getPID() + "\t\t\t\t" + total + "\t\t" + job.getCurrentQ());
            waitTime = total - job.getCpuTimeRequired();
            ++jobsThroughSyst;
            submitJobtoCpu();
        }
        else if(!Q1.isEmpty()) {
            Job check = (Job) Q1.query();
            if(check.getArrivalTime() == ticks){
                cpu.setBusyFlag(false);
                sendToQueue();
                submitJobtoCpu();
            }
        }
        else if(cpu.getQclock() == 0 && cpu.busyFlag() == true){
            cpu.setBusyFlag(false);
            sendToQueue();
            submitJobtoCpu();
        }
    }

   /**
    * Method submitJobtoCpu sumbits job to cpu from queue 1, 2, 3, or 4. 
    *
    */
   private void submitJobtoCpu() {
     if(!Q1.isEmpty() && cpu.busyFlag() == false){
           job = (Job) Q1.remove();
           cpu.setBusyFlag(true);
           cpu.setQclock(2);
           identify = 1;
           job.setCurrentQ(identify);
           cpu.jobOnCpu(job);
           --aveResponseTime;
      }
      else if(!Q2.isEmpty() && cpu.busyFlag() == false){
           job = (Job) Q2.remove();
           cpu.setBusyFlag(true);
           cpu.setQclock(4);
           identify = 2;
           job.setCurrentQ(identify);
           cpu.jobOnCpu(job);
       }
       else if(!Q3.isEmpty() && cpu.busyFlag() == false){
           job = (Job) Q3.remove();
           cpu.setBusyFlag(true);
           cpu.setQclock(8);
           identify = 3;
           job.setCurrentQ(identify);
           cpu.jobOnCpu(job);
       }
       else if(!Q4.isEmpty() && cpu.busyFlag() == false) {
           job =  (Job) Q4.remove();
           cpu.setBusyFlag(true);
           cpu.setQclock(16);
           identify = 4;
           job.setCurrentQ(identify);
           cpu.jobOnCpu(job);
       }
    }
 
   /**
    * Method sendToQueue sends the current job in the cpu to the next lower level queue.
    *
    */
    private void sendToQueue() {
         if(identify == 0) {
             Q1.insert(job);
             identify = 1;
             cpu.setBusyFlag(false);
             aveResponseTime++;
            }
         else if(identify == 1) {
            Q2.insert(job);
            cpu.setBusyFlag(false);
            }
         else if(identify == 2) {
            Q3.insert(job);
            cpu.setBusyFlag(false);
            }
         else if(identify == 3){ 
            Q4.insert(job);
            cpu.setBusyFlag(false);
            }
         else if(identify == 4) {
           Q4.insert(job);
           cpu.setBusyFlag(false);
        }  
    }
}
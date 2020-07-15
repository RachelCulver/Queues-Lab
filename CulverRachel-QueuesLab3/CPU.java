/**
 * the class acts as the processor that holds the current job being worked on by the system.
 * 
 * @author Culver, Rachel
 * @version 10/22/2017
 */
public class CPU
{
 private Job job = new Job();
 private int cpuQuantumClock;
 private boolean busyFlag;

 /**
  * Constructor for objects of class CPU
  */
  public CPU() {
      cpuQuantumClock = 0;
      busyFlag = false; 
        
  }
    
 public void jobOnCpu(Job j) {
     job = j;      
  }
    
 public void clearJob() {
     busyFlag = false;
  }

 public int getQclock() {
     return cpuQuantumClock;
  }

 public void setQclock(int i) {
     cpuQuantumClock = i;
  }
    
 private void currentJob() {
     --cpuQuantumClock;   
 }
  
 public boolean busyFlag() {
     return busyFlag;
  }
   
 public void setBusyFlag(boolean b) {
    busyFlag = b;
  }
}

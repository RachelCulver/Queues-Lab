
/**
 * Contains the signatures of public methods from the ObjectQueue class.
 * 
 * @author Culver, Rachel 
 * @version 10/21/2017
 */
public interface ObjectQueueInterface
{
 public boolean isEmpty();
 public boolean isFull();
 public void clear();
 public void insert(Object o);
 public Object remove();
 public Object query();
}

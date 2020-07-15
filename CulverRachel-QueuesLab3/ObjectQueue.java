
/**
 * Creates and maintains a queue.
 * 
 *@auther Stegman, Richard
 */

public class ObjectQueue implements ObjectQueueInterface{
	private Object[] item;
	private int front;
	private int rear;
	private int count;

	/**
     * Constructor initializes an empty stack.
     */
    public ObjectQueue() {
        item = new Object[1];
        front = 0;
        rear  = -1;
        count = 0;
    }

    /**
     * Checks if queue is empty.
     */
    public boolean isEmpty() {
        return count == 0;
    }
    
    /**
     * Checks if queue is full.
     */
    public boolean isFull() {
        return count == item.length;
    }
    
    /**
     * Clears queue.
     */
    public void clear() {
        item = new Object[1];
        front = 0;
        rear  = -1;
        count = 0;
    }
     
    /**
     * inserts an element into the queue.
     * 
     * @param   o    object to be pushed onto the stack.
     */
    public void insert(Object o) {
        if (isFull())
            resize(2 * item.length);
        rear = (rear+1) % item.length;
        item[rear] = o;
        ++count;
    }
    
    /**
     * Removes first element from the queue.
     * 
     * @return  temp    removed element from the stack
     */
    public Object remove() {
        if (isEmpty()) {
            System.out.println("Queue Underflow: remove error");
            System.exit(1);
        }
        Object temp = item[front];
        item[front] = null;
        front = (front+1) % item.length;
        --count;
        if (count == item.length/4 && item.length != 1)
            resize(item.length/2);
        return temp;
    }
    
    /**
     * Gives the value of the first element of the queue without removing it from queue.
     * 
     * @return item[top]    value of the top element of the stack
     */
    public Object query() {
        if (isEmpty()) {
            System.out.println("Queue Underflow: query error");
            System.exit(1);
        }
        return item[front];
    }

    /**
     * Resizes the queue.
     * 
     * @param   size    int value representing the size of the array
     */
    private void resize(int size) {
        Object[] temp = new Object[size];
        for (int i = 0; i < count; ++i) {
            temp[i] = item[front];
            front = (front+1) % item.length;
        }
        front = 0;
        rear = count-1;
        item = temp;
    }
}


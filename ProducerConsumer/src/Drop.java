import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Drop {
    private final double[] messages = new double[1000];
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    int insert_index, pop_index, count;
    public double take()
    {
        lock.lock();
        try
        {
            while ( count == 0 )
            {
                notEmpty.await();
            }
            double result = messages[pop_index];
            if ( ++pop_index == messages.length )
            {
                pop_index = 0;
            }
            --count;
            notFull.signal();

            return result;
        }
        catch ( InterruptedException ex )
        {
            throw new RuntimeException(ex);
        }
        finally
        {
            lock.unlock();
        }
    }

    public void put(double message)
    {
        lock.lock();
        try
        {
            while ( count == messages.length )
            {
                notFull.await();
            }
            messages[insert_index] = message;
            if ( ++insert_index == messages.length )
            {
                insert_index = 0;
            }
            ++count;
            notEmpty.signal();
        }
        catch ( InterruptedException ex )
        {
            throw new RuntimeException(ex);
        }
        finally
        {
            lock.unlock();
        }
    }
}
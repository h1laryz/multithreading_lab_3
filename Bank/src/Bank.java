import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Bank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;
    final private Object transfer_locked_object = new Object();
    final private ReentrantLock lock = new ReentrantLock();
    final private Condition lock_condition = lock.newCondition();
    public Bank(int n, int initialBalance){
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
    }
    public synchronized void transfer(int from, int to, int amount)
    {
        while ( accounts[from] < amount )
        {
            try
            {
                wait();
            }
            catch( InterruptedException ex )
            {
                throw new RuntimeException(ex);
            }
        }
        accounts[from] -= amount;
        accounts[to] += amount;
        ++ntransacts;
        if (ntransacts % NTEST == 0)
            test();

        notifyAll();
    }

    public void transferSyncBlock(int from, int to, int amount)
    {
        synchronized (transfer_locked_object)
        {
            while ( accounts[from] < amount )
            {
                try
                {
                    transfer_locked_object.wait();
                }
                catch( InterruptedException ex )
                {
                    throw new RuntimeException(ex);
                }
            }

            accounts[from] -= amount;
            accounts[to] += amount;
            ++ntransacts;
            if (ntransacts % NTEST == 0)
                test();

            transfer_locked_object.notifyAll();
        }
    }

    public void transferSyncLock(int from, int to, int amount)
    {
        lock.lock();
        try
        {
            while (accounts[from] < amount)
            {
                lock_condition.await();
            }

            accounts[from] -= amount;
            accounts[to] += amount;
            ++ntransacts;
            if (ntransacts % NTEST == 0)
                test();
        }
        catch (InterruptedException ex)
        {
            throw new RuntimeException(ex);
        }
        finally
        {
            lock_condition.signalAll();
            lock.unlock();
        }
    }

    public void test(){
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i] ;
        System.out.println("Transactions:" + ntransacts
                + " Sum: " + sum);
    }
    public int size(){
        return accounts.length;
    }
}
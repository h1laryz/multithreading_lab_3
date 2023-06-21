import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;
    private int amountToProduce;

    public Producer(Drop drop, int amountToProduce) {
        this.drop = drop;
        this.amountToProduce = amountToProduce;
    }

    public void run()
    {
        for ( int i = 0; i < amountToProduce; ++i )
        {
            Random random = new Random();
            double valueToPut = random.nextDouble(0, 10000);
            drop.put(valueToPut);
            System.out.format("MESSAGE PUT: %s%n", valueToPut);
            try
            {
                Thread.sleep(random.nextInt(5000));
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
        drop.put(-1);
    }
}
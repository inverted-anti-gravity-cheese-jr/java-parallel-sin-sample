import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentSin implements Runnable {
    
    private AtomicInteger currentIteration = new AtomicInteger();
    private AtomicLong value = new AtomicLong();
    
    private final double x;
    
    private final int iterations;
    
    public ConcurrentSin(double x) {
        this(x, 10000);
    }
    
    public ConcurrentSin(double x, int iterations) {
        this.x = x;
        this.iterations = iterations;
    }
    
    @Override
    public void run() {
        //System.out.println(MessageFormat.format("Thread {0} starts", Thread.currentThread().getId()));
        int it = 0;
        while ((it = currentIteration.getAndIncrement()) < iterations) {
            var itValue = (1 - 2 * (it % 2)) * Math.pow(x, it + 1) / factorial(it + 1);
            
            synchronized (value) {
                var valueDouble = Double.longBitsToDouble(value.get()) + itValue;
                value.set(Double.doubleToLongBits(valueDouble));
            }
        }
        System.out.println(MessageFormat.format("Thread {0} exits (after {1} iterations)", Thread.currentThread().getId(), it));
    }
    
    public double getValue() {
        return Double.longBitsToDouble(value.get());
    }
    
    public void clear() {
        currentIteration.set(0);
        value.set(0);
    }
    
    private double factorial(int number) {
        double factorial = 1;
        for (int i = 1; i <= number; ++i) {
            factorial *= i;
        }
        return factorial;
    }

}

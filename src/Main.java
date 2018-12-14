import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    
    public static void main(String[] args) throws InterruptedException {
        
        ConcurrentSin cs = new ConcurrentSin(3 * Math.PI / 2, 400);
        
        int threads = 12;
        System.out.println(MessageFormat.format("Starting multi threaded version ({0} threads)", threads));
        
        long start = System.currentTimeMillis();
        ExecutorService multi = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            multi.execute(cs);
        }
        multi.shutdown();
        while (!multi.awaitTermination(1, TimeUnit.HOURS)) {}
        long end = System.currentTimeMillis();
        
        System.out.println(MessageFormat.format("Took {0} miliseconds", (end - start)));
       
        System.out.println(String.format("Value: %.20f", cs.getValue()));
        
        cs.clear();
        
        System.out.println("Starting single threaded version");
        
        start = System.currentTimeMillis();
        ExecutorService single = Executors.newFixedThreadPool(1);
        single.execute(cs);
        single.shutdown();
        while (!single.awaitTermination(1, TimeUnit.HOURS)) {}
        end = System.currentTimeMillis();
        
        System.out.println(MessageFormat.format("Took {0} miliseconds", (end - start)));
        
        System.out.println(String.format("Value: %.20f", cs.getValue()));
        
    }

}

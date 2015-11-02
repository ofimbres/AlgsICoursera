import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
              
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int n = 0, r;
        
        while(!StdIn.isEmpty()) {
            String input = StdIn.readString();
            n++;
            r = StdRandom.uniform(0, n);
            
            if (r < k) {
                if (n > k)
                    queue.dequeue();
                
                queue.enqueue(input);
            }
        }
        
        for (int i = 0; i < k; i++) 
            StdOut.println(queue.dequeue());
    }
}

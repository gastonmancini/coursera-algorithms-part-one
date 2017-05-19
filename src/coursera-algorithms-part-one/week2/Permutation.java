import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Client program that takes a command-line integer k; reads in a sequence of strings from standard input 
 * and prints exactly k of them, uniformly at random.
 */
public class Permutation {
    
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            randomizedQueue.enqueue(string);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }    
}

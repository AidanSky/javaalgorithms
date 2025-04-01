import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        if (!StdIn.isEmpty()) {
            int counter = 1;
            String chosen = StdIn.readString();
            while (!StdIn.isEmpty()) {
                String champ = StdIn.readString();
                counter++;

                if (StdRandom.bernoulli(1.0 / counter)) {
                    chosen = champ;
                }
            }
            System.out.println(chosen);
        }
    }
}

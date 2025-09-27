
/**
 * A class for fibonacci numbers and their index
 */
public class Fibonacci {
    private int index;
    private long value;

    // Creates a fibonacci number with index and value
    public Fibonacci(int index, long value) {
        this.index = index;
        this.value = value;
    }

    // Returns the index
    public int getIndex() {
        return index;
    }

    // Returns the value
    public long getValue() {
        return value;
    }

    // Checks if value is one more than a perfect cube (n > 1)
    public boolean isCubePlusOne() {
        double root = Math.cbrt(value - 1);
        return Math.round(root) == root;
    }

    // Returns index and value as a formatted string
    public String toString() {
        return index + ") value: " + value;
    }

    // Generates n fibonacci numbers
    public static Fibonacci[] generate(int n) {
        Fibonacci[] arr = new Fibonacci[n];
        int a = 1;
        int b = 1;
        for (int i = 0; i < n; i++) {
            arr[i] = new Fibonacci(i + 1, a);
            int next = a + b;
            a = b;
            b = next;
        }
        return arr;
    }

    /**
     * Gets the value of n from command line arguments or user input
     * @param args command line arguments
     * @return validated n value
     */
    private static int getNValue(String[] args) {
        int n = 10; // default value
        java.util.Scanner scanner = null;

        // Try to get n from command line arguments first
        if (args.length > 0) {
            try {
                n = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, using default n = 10");
            }
        } else {
            // Get n from user input if no command line arguments
            scanner = new java.util.Scanner(System.in);
            System.out.print("Enter n: ");
            try {
                n = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input, using default n = 10");
                n = 10;
            }
            scanner.close();
        }

        return validateN(n);
    }

    /**
     * Validates and adjusts n value to be within acceptable bounds
     * @param n the input value
     * @return validated n value
     */
    private static int validateN(int n) {
        // Check lower bound
        if (n <= 0) {
            System.out.println("n must be > 0, using default n = 10");
            n = 10;
        }
        // Check upper bound to avoid overflow
        if (n >= 40) {
            System.out.println("n must be < 40 to avoid overflow, using n = 39");
            n = 39;
        }
        return n;
    }

    /**
     * Prints all fibonacci numbers in the array
     * @param arr array of fibonacci numbers
     * @param n the count of numbers generated
     */
    private static void printAllFibonacci(Fibonacci[] arr, int n) {
        System.out.println("Input n: " + n);
        System.out.println("All fibonacci numbers:");
        for (Fibonacci f : arr) {
            System.out.println(f.toString());
        }
    }

    /**
     * Prints fibonacci numbers that are one more than a perfect cube
     * @param arr array of fibonacci numbers to check
     */
    private static void printCubePlusOneFibonacci(Fibonacci[] arr) {
        System.out.println("\nFibonacci numbers that are w^3+1:");
        boolean found = false;

        for (Fibonacci f : arr) {
            if (f.isCubePlusOne()) {
                System.out.println(f.toString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("None found");
        }
    }

    /**
     * Main method: orchestrates the fibonacci generation and analysis
     */
    public static void main(String[] args) {
        // Get and validate the number of fibonacci numbers to generate
        int n = getNValue(args);

        // Generate the fibonacci sequence
        Fibonacci[] fibArray = Fibonacci.generate(n);

        // Display all fibonacci numbers
        printAllFibonacci(fibArray, n);

        // Find and display fibonacci numbers that are cube + 1
        printCubePlusOneFibonacci(fibArray);
    }
}
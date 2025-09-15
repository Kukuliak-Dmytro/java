
/**
 * just a class for fibonacci numbers and their index
 */
public class Fibonacci {
    private int index;
    private long value;


    // calculates a fibonacci number with index and value
    public Fibonacci(int index, long value) {
        this.index = index;
        this.value = value;
    }
    
    // gives the index
    public int getIndex() {
        return index;
    }

    // gives the value
    public long getValue() {
        return value;
    }

    // checks if value is like 1 more than a cube (n > 1)
    public long cubePlusOne(long w) {
        long cubeRoot = w ^ 3 + 1;
        return cubeRoot;
    }

    // prints index and value as a string
    public String toString() {
        return index+")"+"value: " + value;
    }

    // makes n fibonacci numbers
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

    // main thing: gets n, prints fibs
    public static void main(String[] args) {
                    int n = 10;
                    int w = 1;
                    java.util.Scanner scanner = null;
                    if (args.length > 0) {
                        try {
                            n = Integer.parseInt(args[0]);
                        } catch (NumberFormatException e) {
                            System.out.println("invalid input, using default n = 10");
                        }
                    } else {
                        scanner = new java.util.Scanner(System.in);
                        System.out.print("enter n: ");
                        try {
                            n = scanner.nextInt();
                        } catch (Exception e) {
                            System.out.println("invalid input, using default n = 10");
                        }
                    }
                    // bounds check for n
                    if (n <= 0) {
                        System.out.println("n must be > 0, using default n = 10");
                        n = 10;
                    }
                    if (n >= 40) {
                        System.out.println("n must be < 40 to avoid overflow, using n = 39");
                        n = 39;
                    }
                    if (scanner == null) {
                        scanner = new java.util.Scanner(System.in);
                    }
                    System.out.print("enter w (minimum cube root): ");
                    try {
                        w = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("invalid input, using default w = 1");
                    }
                    scanner.close();
                    Fibonacci[] arr = Fibonacci.generate(n);
                    System.out.println("input n: " + n);
                    System.out.println("input w: " + w);
                    System.out.println("all fibonacci numbers:");
                    for (Fibonacci f : arr) {
                        System.out.println(f.toString());
                    }
                        long target = (long) Math.pow(w, 3) + 1;
                        System.out.println("\nfibonacci numbers that are 1 more than " + w + "^3 + 1:");
                        boolean found = false;
                        for (Fibonacci f : arr) {
                            if (f.getValue() == target) {
                                System.out.println(f.toString());
                                found = true;
                            }
                        }
                        if (!found) {
                            System.out.println("none found");
                        }
    }
}

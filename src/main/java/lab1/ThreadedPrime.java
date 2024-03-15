package lab1;

import java.util.*;

public class ThreadedPrime {
    public static void main(String[] args) throws InterruptedException {
        // User input
        Scanner input = new Scanner(System.in);
        System.out.println("Enter max number: ");
        int maxNumber = input.nextInt();
        System.out.println("Enter number of threads: ");
        int numberOfThreads = input.nextInt();
        input.close();

        ThreadedPrime threadedPrime = new ThreadedPrime();
        List<Integer> primes = threadedPrime.getListOfPrimes(numberOfThreads,maxNumber);
        threadedPrime.printListOfPrimes(primes);
    }

    private List<Integer> getListOfPrimes(int numberOfThreads, Integer maxNumber) throws InterruptedException
    {
        List<Integer> primeNums = Collections.synchronizedList(new ArrayList<>()); // storing primes in thread-safe list
        int numsPerThread = maxNumber / numberOfThreads;
        List<Thread> threads = new ArrayList<>(); // storing threads

        for (int i = 0; i < numberOfThreads; i++)
        {
            int start = i * numsPerThread; // starting index

            int end; // ending index
            if (i == numberOfThreads - 1) {
                end = maxNumber; // making sure last thread only goes up to the max number
            } else {
                end = (i + 1) * numsPerThread;
            }

            Thread thread = new Thread(() -> {
                for (int num = start; num < end; num++) {
                    if (isPrime(num)) { // checking if number is prime
                        primeNums.add(num);
                    }
                }
            });

            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join(); // check if all threads are finished
        }

        Collections.sort(primeNums); // sorting in ascending order
        return primeNums;
    }

    private void printListOfPrimes(List<Integer> primes)
    {
        for (Integer prime: primes) {
            System.out.print(prime);
            System.out.print(", ");
        }
    }

    // Added method for determining if an integer is prime or not
    private boolean isPrime(int number)
    {
        if (number <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(number); ++i) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
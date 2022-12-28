package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number < 2) {
            throw new IllegalArgumentException();
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int digitsSum(int number) {
        if (number < 0) {
            number = -number;
        }
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}

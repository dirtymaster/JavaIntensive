package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {
    @ParameterizedTest
    @ValueSource(ints = {-100, -1, 0, 1})
    public void numbersLessThan2ShouldThrowException(int number) {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new NumberWorker().isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 1291, 1321, 1171})
    public void numbersShouldBePrime(int number) {
        Assertions.assertTrue(new NumberWorker().isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 100, 169, 1309})
    public void numberShouldNotBePrime(int number) {
        Assertions.assertFalse(new NumberWorker().isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "../../../data.csv")
    public void sumOfNumbersShouldMatch(int number, int sum) {
        Assertions.assertEquals(new NumberWorker().digitsSum(number), sum);
    }
}

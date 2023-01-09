package ex00;

public class Printer implements Runnable {
    private final int numberOfRepetitions;
    private final String word;

    public Printer(int numberOfRepetitions, String word) {
        this.numberOfRepetitions = numberOfRepetitions;
        this.word = word;
    }

    public void run() {
        for (int i = 0; i < numberOfRepetitions; ++i) {
            System.out.println(word);
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
    }
}

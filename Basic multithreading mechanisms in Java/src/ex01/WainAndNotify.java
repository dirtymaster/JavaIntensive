package ex01;

public class WainAndNotify {
    int numberOfRepetitions;

    public WainAndNotify(int numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
    }

    public synchronized void printWord(String word) {
        for (int i = 0; i < numberOfRepetitions; ++i) {
            System.out.println(word);
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
            notify();
            if (i != numberOfRepetitions - 1) {
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}

package ex01;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1 || args[0].length() < 9
                || !args[0].startsWith("--count=")) {
            System.err.println("Invalid arguments");
            System.exit(1);
        }
        int numberOfRepetitions = 0;
        try {
            numberOfRepetitions = Integer.parseInt(args[0].substring(8));
            if (numberOfRepetitions < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.err.println("Invalid arguments");
            System.exit(1);
        }

        WainAndNotify wainAndNotify = new WainAndNotify(numberOfRepetitions);
        Thread thread1 = new Thread(() -> wainAndNotify.printWord("Egg"));
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            wainAndNotify.printWord("Hen");
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException ignored) {
        }
    }
}

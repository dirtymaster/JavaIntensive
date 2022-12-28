package ex02;

import java.util.List;

public class MyThread extends Thread {
    private final int id;
    private final int startIndex;
    private final int endIndex;

    private int sum = 0;
    List<Integer> array;

    public MyThread(int id, int startIndex, int endIndex, List<Integer> array) {
        this.id = id;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.array = array;
    }

    public void run() {
        for (int i = startIndex; i < endIndex; ++i) {
            sum += array.get(i);
        }
    }

    public int getTreadId() {
        return id;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public int getSum() {
        return sum;
    }
}

package ex03;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;

public class Downloader implements Runnable {
    Queue<MyFile> queue;
    int threadId;

    public Downloader(Queue<MyFile> queue, int threadId) {
        this.queue = queue;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            MyFile myFile;
            synchronized (this) {
                myFile = queue.poll();
            }
            System.out.println("Thread-" + threadId + " start download file " +
                    "number " + myFile.getFileId());
            try (InputStream in
                         = URI.create(myFile.getUrl()).toURL().openStream()) {
                Files.copy(in, Paths.get(myFile.getFileId()
                        + myFile.getUrl()
                        .substring(myFile.getUrl().lastIndexOf("."))));
            } catch (IOException e) {
                System.err.println("Downloading file number "
                        + myFile.getFileId() + " failed");
            }
            System.out.println("Thread-" + threadId + " finish download file "
                    + "number " + myFile.getFileId());
        }
    }
}

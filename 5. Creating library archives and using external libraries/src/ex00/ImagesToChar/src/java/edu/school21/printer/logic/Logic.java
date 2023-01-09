package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Logic implements Runnable {
    private final String[] args;
    private File image;
    private byte[] pixels;

    public Logic(String[] args) {
        this.args = args;
    }

    @Override
    public void run() {
        checkArgs();
        getPixels();
        output();
    }

    private void checkArgs() {
        if (args.length != 3 || args[0].length() != 1
                || args[1].length() != 1) {
            System.err.println("Invalid arguments");
            System.exit(1);
        }
        image = new File(args[2]);
        if (!image.exists() || image.isDirectory() || image.length() == 0 ||
                !image.getName().endsWith(".bmp")) {
            System.err.println("Incorrect file");
            System.exit(1);
        }
    }

    private void getPixels() {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(image);
            if (bufferedImage.getHeight() != 16
                    || bufferedImage.getWidth() != 16) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.err.println("Incorrect file");
            System.exit(1);
        }
        pixels = (byte[]) bufferedImage.getRaster().getDataElements(
                0, 0, 16, 16, null);
    }

    private void output() {
        for (int i = 0; i < pixels.length; ++i) {
            if (i % 16 == 0) {
                System.out.println();
            }
            System.out.print(pixels[i] == 0 ? args[0] : args[1]);
        }
    }
}

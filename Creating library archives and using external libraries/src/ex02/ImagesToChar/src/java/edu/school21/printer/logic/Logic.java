package edu.school21.printer.logic;

import com.beust.jcommander.JCommander;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Logic implements Runnable {
    private final String[] args;
    private byte[] pixels;

    public Logic(String[] args) {
        this.args = args;
    }

    @Override
    public void run() {
        getPixels();
        output();
    }

    private void getPixels() {
        File image = new File("resources/image.bmp");
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
        Args arguments = new Args();
        try {
            JCommander.newBuilder().addObject(arguments).build().parse(args);
        } catch (Exception e) {
            System.err.println("Invalid arguments");
            System.exit(1);
        }
        ColoredPrinter coloredPrinter1 = null;
        ColoredPrinter coloredPrinter2 = null;
        try {
            coloredPrinter1 = new ColoredPrinter.Builder(1, false)
                    .background(Ansi.BColor.valueOf(arguments.getBlack()))
                    .build();
            coloredPrinter2
                    = new ColoredPrinter.Builder(1, false)
                    .background(Ansi.BColor.valueOf(arguments.getWhite()))
                    .build();
        } catch (Exception e) {
            System.err.println("Invalid colors");
            System.exit(1);
        }

        for (int i = 0; i < pixels.length; ++i) {
            if (i % 16 == 0) {
                System.out.println();
            }
            if (pixels[i] == 0) {
                coloredPrinter1.print("  ");
            } else {
                coloredPrinter2.print("  ");
            }
        }
    }
}

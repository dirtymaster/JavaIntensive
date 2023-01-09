package edu.school21.spring.printer;

import edu.school21.spring.renderer.Renderer;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;

    PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String text) {
        renderer.render(text);
    }
}

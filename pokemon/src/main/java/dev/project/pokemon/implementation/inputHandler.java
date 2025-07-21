package dev.project.pokemon.implementation;

import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;

public class inputHandler {
    private static Terminal terminal;
    private static Attributes originalAttributes;
    private static NonBlockingReader reader;

    public inputHandler() {
        try {
            terminal = TerminalBuilder.builder().build();
            originalAttributes = terminal.getAttributes();
            terminal.enterRawMode();
            reader = terminal.reader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void startMenu() {

    }

    public static void progressInput(progressBar progress) {
        progress.printProgressBar();

        try {
            boolean waitLoad = true;
            int DelayTime = 500;
            long startTime = System.currentTimeMillis();

            while (progress.getCurrentValue() < progress.getProgressMax()) {
                int c = reader.read(100);

                if (c == ' ') {
                    progress.increaseProgress(1.5);
                    progress.printProgressBar();

                    waitLoad = true;
                    DelayTime = 500;
                    startTime = System.currentTimeMillis();
                }

                if (System.currentTimeMillis() - startTime >= DelayTime) {
                    if (waitLoad) {
                        waitLoad = false;
                        DelayTime = 50;
                    }

                    progress.decreaseProgress(1.5);
                    progress.printProgressBar();

                    terminal.writer().flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        close();
    }

    public static void close() {
        try {
            terminal.setAttributes(originalAttributes);
            terminal.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

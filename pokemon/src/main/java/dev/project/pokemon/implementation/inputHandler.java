package dev.project.pokemon.implementation;

import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;

public class inputHandler {

    public static void progressInput(progressBar progress) {
        try (Terminal terminal = TerminalBuilder.builder().build()) {
            progress.printProgressBar();

            Attributes originalAttributes = terminal.getAttributes();
            terminal.enterRawMode();
            NonBlockingReader reader = terminal.reader();

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

            terminal.setAttributes(originalAttributes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

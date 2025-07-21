package dev.project.pokemon.implementation;

import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;

public class inputHandler {
    private Terminal terminal;
    private NonBlockingReader reader;
    private Attributes originalAttributes;

    public inputHandler() throws IOException {
        terminal = TerminalBuilder.builder().build();
        originalAttributes = terminal.getAttributes();
        terminal.enterRawMode();
        reader = terminal.reader();
    }

    public void startMenuInput() throws IOException {
        int pressedKeystroke = reader.read();

        switch (pressedKeystroke) {
            case 'p':
                System.out.println("TEST PLAY");
                break;
            case 'q':
                System.out.println("TEST QUIT");
                break;
        }
    }

    public void progressBarInput(progressBar progress) throws IOException {
        progress.printProgressBar();
        
        boolean waitLoad = true;
        int DelayTime = 500;
        long startTime = System.currentTimeMillis();

        while (progress.getCurrentValue() < progress.getProgressMax()) {
            int pressedKeystroke = reader.read(100);

            if (pressedKeystroke == ' ') {
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
    }

    public void close() throws IOException {
        terminal.setAttributes(originalAttributes);
        terminal.close();
    }
}

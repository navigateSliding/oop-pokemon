package dev.project.pokemon;

import dev.project.pokemon.implementation.progressBar;

public class AppTest {
    public static void main(String[] args) {
        progressBar progress = new progressBar(0, 100);

        for (int i = 0; i <= 20; i++) {
            try {
                progress.increaseProgress(5);
                progress.printProgressBar();
                Thread.sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

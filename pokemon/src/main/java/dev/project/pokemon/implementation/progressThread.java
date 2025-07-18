package dev.project.pokemon.implementation;

public class progressThread extends Thread {
    private final progressBar progress;

    public progressThread(progressBar progress) {
        this.progress = progress;
    }

    public void run() {
        progress.decreaseProgress(2.5);
    }
}

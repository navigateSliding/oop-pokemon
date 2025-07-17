package dev.project.pokemon.implementation;

// inspo: https://masterex.github.io/archive/2011/10/23/java-cli-progress-bar.html

public class progressBar {
    private double currentValue;
    private double progressMax;
    private double progressMin;

    public progressBar(double progressMin, double progressMax) {
        this.progressMin = progressMin;
        this.progressMax = progressMax;
    }

    public void increaseProgress(double increaseValue) {
        this.currentValue = Math.min(this.progressMax, this.currentValue + increaseValue);
    }
    public void decreaseProgress(double decreaseValue) {
        this.currentValue = Math.max(this.progressMin, this.currentValue + decreaseValue);
    }
    public void printProgressBar() {
        String progressBar = "[" +
                "=".repeat(Math.max(0, (int) currentValue / 5)) +
                " ".repeat(Math.max(0, 20 - ((int) currentValue / 5))) +
                "]";

        System.out.printf("%s %%%.2f\r", progressBar, currentValue);
    }
    public void resetProgress() {

    }
}
package dev.project.pokemon.implementation;

// inspo: https://masterex.github.io/archive/2011/10/23/java-cli-progress-bar.html

public class progressBar {
    private double currentValue;
    private final double progressMax;
    private final double progressMin;
    private final int progressBarLength;

    public progressBar(double progressMin, double progressMax, int progressBarLength) {
        this.progressMin = progressMin;
        this.progressMax = progressMax;
        this.progressBarLength = progressBarLength;
    }

    public double getCurrentValue() {
        return currentValue;
    }
    public double getProgressMax() {
        return progressMax;
    }

    public void increaseProgress(double increaseValue) {
        this.currentValue = Math.min(this.progressMax, this.currentValue + increaseValue);
    }
    public void decreaseProgress(double decreaseValue) {
        this.currentValue = Math.max(this.progressMin, this.currentValue - decreaseValue);
    }
    public void printProgressBar() {
        int progressDivision = (int) progressMax / progressBarLength;

        String progressBar = "[" +
                "=".repeat(Math.max(0, (int) currentValue / progressDivision )) +
                ".".repeat(Math.max(0, progressBarLength - ((int) currentValue / progressDivision))) +
                "]";

        if (currentValue >= 100) {
            System.out.printf("%s %.2f%%\n", progressBar, currentValue);
            return;
        }

        System.out.printf("%s %.2f%%\r", progressBar, currentValue);
    }
    public void resetProgress() {

    }
}
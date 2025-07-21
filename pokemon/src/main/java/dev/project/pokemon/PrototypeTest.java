package dev.project.pokemon;

import dev.project.pokemon.implementation.inputHandler;
import dev.project.pokemon.implementation.progressBar;

public class PrototypeTest {
    public static void main(String[] args) {

        progressBar progress = new progressBar(0, 100, 20);
        inputHandler.progressInput(progress);

//      Creating Progress Bar Code and Load
//        while (progress.getCurrentValue() < progress.getProgressMax()) {
//            try {
//                progress.increaseProgress(3);
//                progress.printProgressBar();
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        System.out.println(progress.getCurrentValue());
    }
}

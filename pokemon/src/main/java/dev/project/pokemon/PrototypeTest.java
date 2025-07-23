package dev.project.pokemon;

import dev.project.pokemon.implementation.AsciiArt;
import dev.project.pokemon.implementation.inputHandler;
import dev.project.pokemon.implementation.progressBar;

import java.io.IOException;

public class PrototypeTest {
    public static void main(String[] args) {

        progressBar progress = new progressBar(0, 100, 20);

        try {
            System.out.println(AsciiArt.getStartMenu());

            inputHandler inputHandler = new inputHandler();
            inputHandler.startMenuInput();
            inputHandler.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        inputHandler progressBar
//        try {
//            inputHandler inputHandler = new inputHandler();
//            inputHandler.progressBarInput(progress);
//            inputHandler.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

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

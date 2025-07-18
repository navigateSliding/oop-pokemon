package dev.project.pokemon;

import dev.project.pokemon.implementation.progressBar;
import dev.project.pokemon.implementation.progressThread;


public class PrototypeTest {
    public static void main(String[] args) {

        progressBar progress = new progressBar(0, 100, 20);
        progressThread thread1 = new progressThread(progress);

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

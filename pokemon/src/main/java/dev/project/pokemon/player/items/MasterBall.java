package dev.project.pokemon.player.items;

import dev.project.pokemon.pokemon.Pokemon;

public class MasterBall extends PokeBall {
    public MasterBall() {
        super("Master Ball", 999.0); // Essentially 100% catch rate
    }
    
    @Override
    public boolean attemptCatch(Pokemon target) {
        System.out.println("Throwing " + name + " at " + target.getName() + "...");
        System.out.println("Critical hit! " + target.getName() + " was caught!");
        return true; // Master Ball never fails (in normal circumstances)
    }
}
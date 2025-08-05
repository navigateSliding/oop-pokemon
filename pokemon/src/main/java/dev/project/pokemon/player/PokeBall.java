package dev.project.pokemon.player;

import dev.project.pokemon.pokemon.Pokemon;

import java.util.Random;

/**
 * Abstract PokeBall class - base for all pokeball types
 */
abstract public class PokeBall {
    // Protected attributes (accessible by subclasses)
    protected String name;
    protected double catchRateModifier;
    protected Random random;
    
    // Constructor
    public PokeBall(String name, double catchRateModifier) {
        this.name = name;
        this.catchRateModifier = catchRateModifier;
        this.random = new Random();
    }
    
    /**
     * Attempt to catch a target Pokemon
     * @param target The Pokemon to catch
     * @return true if catch was successful, false otherwise
     */
    public boolean attemptCatch(Pokemon target) {
        System.out.println("Throwing " + name + " at " + target.getName() + "...");
        
        // Base catch rate calculation
        // Lower HP = easier to catch, higher grade = harder to catch
        double baseRate = 0.3; // 30% base catch rate
        
        // HP factor (lower HP = higher catch rate)
        double hpFactor = 1.0 - ((double) target.getHp() / target.getMaxHp());
        
        // Grade factor (higher grade = lower catch rate)
        double gradeFactor = 1.0 / (1.0 + (target.getGrade() * 0.1));
        
        // Calculate final catch rate
        double finalCatchRate = (baseRate + hpFactor * 0.4) * gradeFactor * catchRateModifier;
        
        // Cap at 95% max catch rate
        finalCatchRate = Math.min(0.95, finalCatchRate);
        
        // Generate random number and check success
        double roll = random.nextDouble();
        boolean success = roll < finalCatchRate;
        
        // Display result
        if (success) {
            System.out.println("Success! " + target.getName() + " was caught!");
            target.heal(); // Heal Pokemon when caught
        } else {
            System.out.println("Oh no! " + target.getName() + " broke free!");
        }
        
        return success;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public double getCatchRateModifier() {
        return catchRateModifier;
    }
}
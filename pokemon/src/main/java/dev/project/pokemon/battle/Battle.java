package dev.project.pokemon.battle;

import dev.project.pokemon.display.Display;
import dev.project.pokemon.player.Player;
import dev.project.pokemon.pokemon.Move;
import dev.project.pokemon.pokemon.Pokemon;
import dev.project.pokemon.pokemon.PokemonType;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Battle {
    // Attributes
    private final Player player;
    private final ArrayList<Pokemon> wildPokemon;
    private final Display display;
    private final Scanner scanner;
    private boolean battleEnded;
    private String battleResult; // "PLAYER_WIN", "AI_WIN", "DRAW" TODO: CHANGE TO ENUMERATION
    private ProgressBar progress;
    
    // Constructor
    public Battle(Player player, ArrayList<Pokemon> wildPokemon) {
        this.player = player;
        this.wildPokemon = wildPokemon;
        this.display = new Display();
        this.scanner = new Scanner(System.in);
        this.battleEnded = false;
        this.progress = new ProgressBar(0, 100, 20);
    }
    
    /**
     * Start the battle sequence
     */
    public void start() {
        if (wildPokemon != null && !wildPokemon.isEmpty()) {
            startWildBattle();
        } else {
            System.out.println("No valid battle opponent found!");
        }
    }
    
    /**
     * Start battle against wild Pokemon
     */
    private void startWildBattle() {
        display.clearScreen();
        System.out.println("=== WILD POKEMON ENCOUNTER ===");

        Pokemon wild = wildPokemon.get(0); // Battle first wild Pokemon
        
        if (getPlayerActivePokemon() == null) {
            System.out.println("You have no Pokemon to battle with!");
            return;
        }
        
        System.out.println("A wild " + wild.getName() + " appeared!");
        System.out.println(player.getName() + " sends out " + getPlayerActivePokemon().getName() + "!");
        
        // Battle loop
        while (!battleEnded) {
            // Check battle result
            if (getPlayerActivePokemon().isDefeated()) {
                System.out.printf("%s is defeated\n", getPlayerActivePokemon().getName());

                if (!player.switchPokemon()) {
                    battleResult = "WILD_WIN";
                    display.displayDefeat(getPlayerActivePokemon().getName());
                    break;
                }

                continue;
            } else if (wild.isDefeated()) {
                battleResult = "PLAYER_WIN";
                System.out.println("Wild " + wild.getName() + " was defeated!");
                break;
            }

            display.displayWildPokemonBattleOptions(wild, getPlayerActivePokemon());
            
            String choice = scanner.nextLine().toUpperCase();
            
            switch (choice) {
                case "C" -> {
                    // Attempt to catch (end battle)
                    System.out.println("Attempting to catch " + wild.getName() + "...");
                    battleEnded = true;
                    battleResult = "CATCH_ATTEMPT";
                }
                case "F" -> {
                    // Fight - use detailed battle system
                    wildBattleTurn(getPlayerActivePokemon(), wild);
                }
                case "Q" -> {
                    // Leave
                    System.out.println("You ran away from the wild " + wild.getName() + "!");
                    battleEnded = true;
                    battleResult = "RAN_AWAY";
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    
    /**
     * Handle a single turn in wild Pokemon battle
     */
    private void wildBattleTurn(Pokemon playerPokemon, Pokemon wild) {
        // Determine turn order based on speed
        if (playerPokemon.getSpeed() >= wild.getSpeed()) {
            // Player goes first
            wildPlayerTurn(playerPokemon, wild);
            if (!wild.isDefeated() && !battleEnded) {
                enemyTurn(wild, playerPokemon);
            }
        } else {
            // Wild Pokemon goes first
            enemyTurn(wild, playerPokemon);
            if (!playerPokemon.isDefeated() && !battleEnded) {
                wildPlayerTurn(playerPokemon, wild);
            }
        }
    }

    /**
     * Handle player's turn in wild battle with move selection
     */
    private void wildPlayerTurn(Pokemon playerPokemon, Pokemon opponent) {
        display.displayPlayerTurn(player, opponent, playerPokemon);
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> {
                // Attack - Show available moves
                showMoveSelection(playerPokemon, opponent);
            }
            case "2" -> {
                // Switch Pokemon
                player.switchPokemon();
            }
            case "3" -> {
                // Go back to catch/fight/run menu - just return
            }
            default -> System.out.println("Invalid choice! Turn skipped.");
        }
    }
    
    /**
     * Handle player's turn with improved move selection
     */
    private void playerTurn(Pokemon playerPokemon, Pokemon opponent) {
        display.displayPlayerTurn(player, opponent, playerPokemon);
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> {
                // Attack - Show available moves
                showMoveSelection(playerPokemon, opponent);
            }
            case "2" -> {
                // Switch Pokemon
                player.switchPokemon();
            }
            case "3" -> {
                // Run (only in wild battles)
                if (wildPokemon != null) {
                    System.out.println("You ran away!");
                    battleEnded = true;
                    battleResult = "RAN_AWAY";
                } else {
                    System.out.println("You can't run from a trainer battle!");
                }
            }
            default -> System.out.println("Invalid choice! Turn skipped.");
        }
    }
    
    /**
     * Show move selection interface
     */
    private void showMoveSelection(Pokemon playerPokemon, Pokemon opponent) {
        Random random = new Random();
        double randomChance = random.nextDouble();

        System.out.println("\n--- SELECT MOVE ---");

        // Check if Pokemon has any moves
        if (playerPokemon.getMove() == null) {
            System.out.println(playerPokemon.getName() + " has no moves available!");
            System.out.println(playerPokemon.getName() + " struggles but does no damage!");
            return;
        }

        // Display available move
        Move availableMove = playerPokemon.getMove();
        System.out.println("Available moves:");
        System.out.printf("1. %s (Type: %s, Power: %s)\n", availableMove.getName(), availableMove.getMoveType(), availableMove.getPower());
        System.out.println("2. Back to action menu");

        System.out.print("Choose move (1-2): ");
        String moveChoice = scanner.nextLine();
        
        switch (moveChoice) {
            case "1" -> {
                // Use the move
                int damage = calculateDamage(playerPokemon, opponent);

                System.out.println(playerPokemon.getName() + " used " + availableMove.getName() + "!");

                if (randomChance < 0.4) {
                    display.displayCritChanceTip();
                    scanner.nextLine();

                    InputHandler.progressInput(progress);
                    if (progress.getCurrentValue() >= 100) {
                        opponent.takeDamage(damage*2);
                    }

                    progress.resetProgress();

                    break;
                }

                opponent.takeDamage(damage);
            }
            case "2" -> {
                // Go back to main action menu
                playerTurn(playerPokemon, opponent);
            }
            default -> {
                System.out.println("Invalid choice! Using " + availableMove.getName() + " by default.");
                int damage = calculateDamage(playerPokemon, opponent);
                System.out.println(playerPokemon.getName() + " used " + availableMove.getName() + "!");
                opponent.takeDamage(damage);
            }
        }
    }
    
    /**
     * Handle enemy's turn (AI or wild Pokemon)
     */
    private void enemyTurn(Pokemon enemyPokemon, Pokemon playerPokemon) {
        Move enemyMove = enemyPokemon.getMove();
        String trainerName = "Wild Pokemon";

        System.out.println("\n=== Opponent's Turn ===");
        
        if (enemyMove != null) {
            int damage = calculateDamage(enemyPokemon, playerPokemon);
            System.out.println(trainerName + "'s " + enemyPokemon.getName() + " used " + enemyMove.getName() + "!");
            playerPokemon.takeDamage(damage);
        } else {
            System.out.println(enemyPokemon.getName() + " has no moves!");
        }
    }
    
    /**
     * Calculate damage between attacker and defender
     */
    private int calculateDamage(Pokemon attacker, Pokemon defender) {
        if (attacker.getMove() == null) {
            return 0;
        }
        
        Move attackMove = attacker.getMove();
        
        // Base damage calculation
        double baseDamage = (double) attacker.getAttack() * attackMove.getPower() / defender.getDefense();
        
        // Type effectiveness
        double effectiveness = getEffectiveness(attackMove.getMoveType(), defender.getType());
        
        // Apply effectiveness multiplier
        int finalDamage = (int) (baseDamage * effectiveness);
        
        // Minimum damage of 1
        finalDamage = Math.max(1, finalDamage);
        
        // Show effectiveness message (why is this here? !ask)
        if (effectiveness > 1.0) {
            System.out.println("It's super effective!");
        } else if (effectiveness < 1.0) {
            System.out.println("It's not very effective...");
        }
        
        return finalDamage;
    }
    
    /**
     * Get type effectiveness multiplier
     */
    private double getEffectiveness(PokemonType moveType, PokemonType defenderType) {
        return moveType.getEffectiveness(defenderType);
    }
    
    /**
     * Get player's active Pokemon (first in party)
     */
    private Pokemon getPlayerActivePokemon() {
        if (player.getPartyList().isEmpty()) {
            return null;
        }
        return player.getPartyList().get(0);
    }
    
    public String getBattleResult() {
        return battleResult;
    }
}
package dev.project.pokemon;

import java.util.ArrayList;
import java.util.Scanner;

public class Battle {
    // Attributes
    private final Player player;
    private final AITrainer aiTrainer;
    private final ArrayList<Pokemon> wildPokemon;
    private final Display display;
    private final Scanner scanner;
    private boolean battleEnded;
    private String battleResult; // "PLAYER_WIN", "AI_WIN", "DRAW"
    
    // Constructor
    public Battle(Player player, AITrainer aiTrainer) {
        this.player = player;
        this.aiTrainer = aiTrainer;
        this.wildPokemon = null;
        this.display = new Display();
        this.scanner = new Scanner(System.in);
        this.battleEnded = false;
    }
    
    // Constructor for wild Pokemon battle
    public Battle(Player player, ArrayList<Pokemon> wildPokemon) {
        this.player = player;
        this.aiTrainer = null;
        this.wildPokemon = wildPokemon;
        this.display = new Display();
        this.scanner = new Scanner(System.in);
        this.battleEnded = false;
    }
    
    /**
     * Start the battle sequence
     */
    public void start() {
        if (aiTrainer != null) {
            startTrainerBattle();
        } else if (wildPokemon != null && !wildPokemon.isEmpty()) {
            startWildBattle();
        } else {
            System.out.println("No valid battle opponent found!");
        }
    }
    
    /**
     * Start battle against AI Trainer
     */
    private void startTrainerBattle() {
        display.clearScreen();
        System.out.println("=== TRAINER BATTLE STARTED ===");
        System.out.println(player.getName() + " VS " + aiTrainer.getName());
        
        Pokemon playerPokemon = getPlayerActivePokemon();
        Pokemon aiPokemon = getAIActivePokemon();
        
        if (playerPokemon == null || aiPokemon == null) {
            System.out.println("Battle cannot start - missing Pokemon!");
            return;
        }
        
        System.out.println(player.getName() + " sends out " + playerPokemon.getName() + "!");
        System.out.println(aiTrainer.getName() + " sends out " + aiPokemon.getName() + "!");
        
        // Battle loop
        while (!battleEnded && !playerPokemon.isDefeated() && !aiPokemon.isDefeated()) {
            // Determine turn order based on speed
            if (playerPokemon.getSpeed() >= aiPokemon.getSpeed()) {
                playerTurn(playerPokemon, aiPokemon);
                if (!aiPokemon.isDefeated() && !battleEnded) {
                    enemyTurn(aiPokemon, playerPokemon);
                }
            } else {
                enemyTurn(aiPokemon, playerPokemon);
                if (!playerPokemon.isDefeated() && !battleEnded) {
                    playerTurn(playerPokemon, aiPokemon);
                }
            }
        }
        
        // Determine battle result
        if (playerPokemon.isDefeated()) {
            battleResult = "AI_WIN";
            System.out.println(playerPokemon.getName() + " was defeated!");
            Display.getDefeatedMenu();
        } else if (aiPokemon.isDefeated()) {
            battleResult = "PLAYER_WIN";
            System.out.println(aiPokemon.getName() + " was defeated!");
            System.out.println("You won the battle!");
        }
    }
    
    /**
     * Start battle against wild Pokemon
     */
    private void startWildBattle() {
        display.clearScreen();
        System.out.println("=== WILD POKEMON ENCOUNTER ===");
        
        Pokemon playerPokemon = getPlayerActivePokemon();
        Pokemon wild = wildPokemon.get(0); // Battle first wild Pokemon
        
        if (playerPokemon == null) {
            System.out.println("You have no Pokemon to battle with!");
            return;
        }
        
        System.out.println("A wild " + wild.getName() + " appeared!");
        System.out.println(player.getName() + " sends out " + playerPokemon.getName() + "!");
        
        // Battle loop
        while (!battleEnded && !playerPokemon.isDefeated() && !wild.isDefeated()) {
            // Show wild Pokemon battle options
            System.out.println("\n=== WILD POKEMON BATTLE ===");
            System.out.println("Wild " + wild.getName() + " (HP: " + wild.getHp() + "/" + wild.getMaxHp() + ")");
            System.out.println("Your " + playerPokemon.getName() + " (HP: " + playerPokemon.getHp() + "/" + playerPokemon.getMaxHp() + ")");
            
            System.out.println("\nWhat will you do?");
            System.out.println("C - Catch " + wild.getName());
            System.out.println("F - Fight");
            System.out.println("Q - Run away");
            System.out.print("Choose action: ");
            
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
                    wildBattleTurn(playerPokemon, wild);
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
        
        // Check battle result
        if (playerPokemon.isDefeated()) {
            battleResult = "WILD_WIN";
            System.out.println(playerPokemon.getName() + " was defeated!");
            Display.getDefeatedMenu();
        } else if (wild.isDefeated()) {
            battleResult = "PLAYER_WIN";
            System.out.println("Wild " + wild.getName() + " was defeated!");
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
        System.out.println("\n=== " + player.getName() + "'s Turn ===");
        System.out.println("Your " + playerPokemon.getName() + " (HP: " + 
                         playerPokemon.getHp() + "/" + playerPokemon.getMaxHp() + ")");
        System.out.println("Wild " + opponent.getName() + " (HP: " + 
                         opponent.getHp() + "/" + opponent.getMaxHp() + ")");
        
        System.out.println("\nWhat will " + playerPokemon.getName() + " do?");
        System.out.println("1. Attack");
        System.out.println("2. Switch Pokemon");
        System.out.println("3. Back to main options");
        System.out.print("Choose action: ");
        
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
        System.out.println("\n=== " + player.getName() + "'s Turn ===");
        System.out.println("Your " + playerPokemon.getName() + " (HP: " + 
                         playerPokemon.getHp() + "/" + playerPokemon.getMaxHp() + ")");
        System.out.println("Opponent " + opponent.getName() + " (HP: " + 
                         opponent.getHp() + "/" + opponent.getMaxHp() + ")");
        
        System.out.println("\nWhat will " + playerPokemon.getName() + " do?");
        System.out.println("1. Attack");
        System.out.println("2. Switch Pokemon");
        System.out.println("3. Run (if wild battle)");
        System.out.print("Choose action: ");
        
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
        progressBar progress = new progressBar(0, 100, 20);

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
        System.out.println("1. " + availableMove.getName() + 
                         " (Type: " + availableMove.getMoveType() + 
                         ", Power: " + availableMove.getPower() + ")");
        System.out.println("2. Back to action menu");
        
        System.out.print("Choose move (1-2): ");
        String moveChoice = scanner.nextLine();
        
        switch (moveChoice) {
            case "1" -> {
                // Use the move
                int damage = calculateDamage(playerPokemon, opponent);
                System.out.println(playerPokemon.getName() + " used " + availableMove.getName() + "!");
                inputHandler.progressInput(progress);
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
        System.out.println("\n=== Opponent's Turn ===");
        
        Move enemyMove = null;
        String trainerName = "Wild Pokemon";
        
        if (aiTrainer != null) {
            enemyMove = aiTrainer.autoChooseMove();
            trainerName = aiTrainer.getName();
        } else {
            enemyMove = enemyPokemon.getMove();
        }
        
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
        
        // Show effectiveness message
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
    
    /**
     * Get AI's active Pokemon (first in party)
     */
    private Pokemon getAIActivePokemon() {
        if (aiTrainer == null || aiTrainer.getPartyList().isEmpty()) {
            return null;
        }
        return aiTrainer.getPartyList().get(0);
    }
    
    // Getters
    public boolean isBattleEnded() {
        return battleEnded;
    }
    
    public String getBattleResult() {
        return battleResult;
    }
    
    public ArrayList<Pokemon> getWildPokemon() {
        return wildPokemon;
    }
}
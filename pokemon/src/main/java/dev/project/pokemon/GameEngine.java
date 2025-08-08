package dev.project.pokemon;

import dev.project.pokemon.battle.Battle;
import dev.project.pokemon.battle.ScoreManager;
import dev.project.pokemon.display.Display;
import dev.project.pokemon.player.*;
import dev.project.pokemon.player.items.*;
import dev.project.pokemon.pokemon.Move;
import dev.project.pokemon.pokemon.Pokemon;
import dev.project.pokemon.pokemon.PokemonType;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameEngine {
    // Attributes from UML
    private Player player;
    private final ArrayList<Pokemon> wildPokemon;
    private Battle battle;
    private final Display display;
    private final Database database;
    private final Scanner scanner;
    private final ScoreManager scoreManager;
    private final Random random;
    private boolean gameRunning;
    
    // Constructor
    public GameEngine() {
        this.display = new Display();
        this.database = new Database();
        this.scanner = new Scanner(System.in);
        this.scoreManager = new ScoreManager(database);
        this.random = new Random();
        this.gameRunning = true;
        
        // Load Pokemon roster from database
        this.wildPokemon = database.loadPokemonRoster();
        
        System.out.println("Game Engine initialized successfully!");
        System.out.println("Loaded " + wildPokemon.size() + " Pokemon species.");
    }
    
    /**
     * Start the main game loop
     */
    public void startGame() {
        display.clearScreen();
        display.displayTitleScreenMenu();
        
        // Initialize player
        initializePlayer();
        
        // Main game loop
        while (gameRunning) {
            display.clearScreen();
            mainMenu();
        }
        
        // Save player data before exit
        database.savePlayer(player);
        display.displayGameOver();
        scanner.close();
    }
    
    /**
     * Initialize player - load existing or create new
     */
    private void initializePlayer() {
        System.out.print("Enter your trainer name: ");
        String playerName = scanner.nextLine();
        
        // Try to load existing player by name
        Player loadedPlayer = database.loadPlayer(playerName);
        
        if (loadedPlayer != null) {
            // Found existing player
            this.player = loadedPlayer;
            display.displayFoundExistingPlayer(playerName, player);
        } else {
            this.player = new Player(playerName);
            
            // Give starter Pokemon
            giveStarterPokemon();
            System.out.println("New trainer registered: " + playerName);
            System.out.println("Current score: " + player.getScore());
        }

        // Add pause so user can read the messages
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
        
    /**
     * Give player a starter Pokemon
     */
    private void giveStarterPokemon() {
        int choice;

        display.displayGiveStarterPokemon();

        // Create starter options
        Pokemon charmander = new Pokemon("Charmander", "004", 3, 78, 84, 78, 65, PokemonType.FIRE);
        charmander.setMove(new Move("Ember", PokemonType.FIRE, 40));
        
        Pokemon squirtle = new Pokemon("Squirtle", "007", 3, 79, 83, 100, 43, PokemonType.WATER);
        squirtle.setMove(new Move("Water Gun", PokemonType.WATER, 40));
        
        Pokemon bulbasaur = new Pokemon("Bulbasaur", "001", 3, 80, 82, 83, 45, PokemonType.GRASS);
        bulbasaur.setMove(new Move("Vine Whip", PokemonType.GRASS, 45));

        do {
            System.out.print("Choose your starter (1-3): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }
        } while (choice < 1 || choice > 3);
        
        Pokemon starter = switch (choice) {
            case 1 -> charmander;
            case 2 -> squirtle;
            case 3 -> bulbasaur;
            default -> charmander;
        };
        
        player.addToCollection(starter);
        player.addPokemon(starter);
        
        System.out.println("You chose " + starter.getName() + "!");
        display.displayPokemonInfo(starter);
    }
    
    /**
     * Main menu handling
     */
    private void mainMenu() {
        display.displayStartMenu();
        String choice = scanner.nextLine().toUpperCase();
        
        switch (choice) {
            case "P" -> battleAndCatchMode();
            case "L" -> viewPlayerPokemon();
            case "S" -> viewTopScores();
            case "Q" -> {
                System.out.println("Thanks for playing!");
                gameRunning = false;
            }
            default -> {
                System.out.println( "Invalid option! Please try again.\n" +
                                    "Press Enter to continue...");
                scanner.nextLine();
            }
        }
    }
    
    /**
     * Battle and catch mode - core gameplay
     */
    private void battleAndCatchMode() {
        int choice;

        if (player.getPartyList().isEmpty()) {
            System.out.println( "You need at least one Pokemon in your party to battle!\n" +
                                "Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        // Generate random wild Pokemon encounters (1-3 Pokemon)
        ArrayList<Pokemon> encounter = generateWildEncounter();

        display.clearScreen();
        System.out.println("\n=== ENTERING WILD AREA ===\n" +
                            "You encountered wild Pokemon!");

        for (int i = 0; i < encounter.size(); i++) {
            System.out.println((i + 1) + ". " + encounter.get(i).getDisplayInfo());
        }
        
        // Let player choose which Pokemon to battle
        System.out.printf("Choose Pokemon to battle (1-%d): ", encounter.size());

        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            choice = 0;
        }
        
        if (choice < 0 || choice >= encounter.size()) {
            choice = 0;
        }
        
        Pokemon wildTarget = encounter.get(choice);
        ArrayList<Pokemon> battleList = new ArrayList<>();
        battleList.add(wildTarget);
        
        // Start battle
        battle = new Battle(player, battleList);
        battle.start();
        
        // Handle battle result
        handleBattleResult(wildTarget);
    }
    
    /**
     * Handle the result of a battle
     */
    private void handleBattleResult(Pokemon wildPokemon) {
        String result = battle.getBattleResult();
        
        switch (result) {
            case "CATCH_ATTEMPT" -> attemptCatch(wildPokemon);
            case "PLAYER_WIN" -> {
                System.out.println("Victory! You gained experience points!");
                int scoreGain = wildPokemon.getGrade() * 100;
                player.setScore(player.getScore() + scoreGain);
                System.out.println("Score gained: " + scoreGain);
                
                // Offer to attempt catch on defeated Pokemon
                System.out.print("Attempt to catch the defeated " + wildPokemon.getName() + "? (Y/N): ");
                if (scanner.nextLine().trim().toUpperCase().startsWith("Y")) {
                    attemptCatch(wildPokemon);
                }
            }
            case "WILD_WIN" -> System.out.println("Better luck next time.");
            case "RAN_AWAY" -> System.out.println("You escaped safely.");
            default -> System.out.println("Battle ended.");
        }
        
        // Save progress
        database.savePlayer(player);
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Attempt to catch a wild Pokemon
     */
    private void attemptCatch(Pokemon wildPokemon) {
        int ballChoice;

        display.displayAttemptCatch();

        try {
            ballChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            ballChoice = 1;
        }
        
        PokeBall ball = switch (ballChoice) {
            case 2 -> new GreatBall();
            case 3 -> new UltraBall();
            case 4 -> new MasterBall();
            default -> new StandardBall();
        };
        
        // Attempt catch
        boolean caught = ball.attemptCatch(wildPokemon);
        
        if (caught) {
            System.out.println("Success! " + wildPokemon.getName() + " was caught!");
            wildPokemon.heal(); // Heal Pokemon when caught

            player.addToCollection(wildPokemon);
            
            // Bonus score for catching
            int catchBonus = wildPokemon.getGrade() * 150;
            player.setScore(player.getScore() + catchBonus);
            System.out.println("Catch bonus: " + catchBonus + " points!");
            
            display.displayPokemonInfo(wildPokemon);
        } else {
            display.displayCatchFailed(wildPokemon);
        }
    }
    
    /**
     * Generate a random wild Pokemon encounter
     */
    private ArrayList<Pokemon> generateWildEncounter() {
        ArrayList<Pokemon> encounter = new ArrayList<>();
        
        // Generate 1-3 random wild Pokemon
        int numPokemon = random.nextInt(2) + 2;
        
        for (int i = 0; i < numPokemon; i++) {
            // Pick random Pokemon from roster
            Pokemon original = wildPokemon.get(random.nextInt(wildPokemon.size()));
            
            // Create a copy with random variations
            Pokemon wild = new Pokemon(
                original.getName(),
                original.getPokemonId(),
                Math.max(1, original.getGrade() + random.nextInt(3) - 1), // Grade ±1
                original.getMaxHp(),
                original.getAttack(),
                original.getDefense(),
                original.getSpeed(),
                original.getType()
            );
            
            // Copy the move
            if (original.getMove() != null) {
                Move originalMove = original.getMove();
                wild.setMove(new Move(originalMove.getName(), originalMove.getMoveType(), originalMove.getPower()));
            }
            
            encounter.add(wild);
        }
        
        return encounter;
    }
    
    /**
     * View player's Pokemon collection and party
     */
    private void viewPlayerPokemon() {
        display.clearScreen();
        System.out.println("\n=== YOUR POKEMON ===");
        
        display.displayPlayerParty(player);
        System.out.println();
        display.displayPlayerCollection(player);
        
        if (!player.getPokemonCollection().isEmpty()) {
            System.out.println( "\nOptions:\n" +
                                "1. Add Pokemon to party\n" +
                                "2. Remove Pokemon from party\n" +
                                "3. Heal Pokemon\n" +
                                "4. View detailed Pokemon info\n" +
                                "5. Back to main menu");

            System.out.print("Choose option (1-5): ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1" -> addPokemonToParty();
                case "2" -> removePokemonFromParty();
                case "3" -> healPokemon();
                case "4" -> viewDetailedPokemonInfo();
                default -> {
                    // Return to main menu
                }
            }
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Add Pokemon from collection to party
     */
    private void addPokemonToParty() {
        if (player.getPartyList().size() >= 3) {
            System.out.println("Your party is full! (Maximum 3 Pokemon)");
            return;
        }
        
        System.out.println("Choose Pokemon to add to party:");
        for (int i = 0; i < player.getPokemonCollection().size(); i++) {
            Pokemon p = player.getPokemonCollection().get(i);
            String status = player.getPartyList().contains(p) ? " (In Party)" : "";
            System.out.println((i + 1) + ". " + p.getName() + status);
        }
        
        System.out.printf("Choose Pokemon (1-%d): ", player.getPokemonCollection().size());
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < player.getPokemonCollection().size()) {
                Pokemon chosen = player.getPokemonCollection().get(choice);
                player.addPokemon(chosen);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
        }
    }
    
    /**
     * Remove Pokemon from party
     */
    private void removePokemonFromParty() {
        if (player.getPartyList().isEmpty()) {
            System.out.println("Your party is empty!");
            return;
        }
        
        System.out.println("Choose Pokemon to remove from party:");
        for (int i = 0; i < player.getPartyList().size(); i++) {
            System.out.println((i + 1) + ". " + player.getPartyList().get(i).getName());
        }
        
        System.out.printf("Choose Pokemon (1-%d): ", player.getPartyList().size());
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < player.getPartyList().size()) {
                Pokemon chosen = player.getPartyList().get(choice);
                player.removePokemon(chosen);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
        }
    }
    
  private void healPokemon() {
        System.out.println("\nChoose Pokemon to heal:");
        for (int i = 0; i < player.getPartyList().size(); i++) {
            System.out.printf("%d. %s HP: %d/%d\n",
                    i+1,
                    player.getPartyList().get(i).getName(),
                    player.getPartyList().get(i).getHp(),
                    player.getPartyList().get(i).getMaxHp()
            );
        }

        System.out.printf("Choose Pokemon (1-%d): ", player.getPartyList().size());

        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < player.getPartyList().size()) {
                Pokemon chosen = player.getPartyList().get(choice);
                chosen.heal();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
        }
  }

    /**
     * View detailed info for a specific Pokemon
     */
    private void viewDetailedPokemonInfo() {
        System.out.println("Choose Pokemon to view details:");
        for (int i = 0; i < player.getPokemonCollection().size(); i++) {
            System.out.println((i + 1) + ". " + player.getPokemonCollection().get(i).getName());
        }

        System.out.printf("Choose Pokemon (1-%d): ", player.getPokemonCollection().size());
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < player.getPokemonCollection().size()) {
                Pokemon chosen = player.getPokemonCollection().get(choice);
                display.displayPokemonInfo(chosen);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
        }
    }
    
    /**
     * View top scores
     */
    private void viewTopScores() {
        display.clearScreen();
        display.displayTopScores(scoreManager.getTopScores());
        
        // Check if current player's score qualifies
        if (scoreManager.isHighScore(player.getScore())) {
            System.out.printf("\nCongratulations! Your current score (%d) qualifies for the high score list!\n", player.getScore());
            scoreManager.addScore(player.getName(), player.getScore());
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}

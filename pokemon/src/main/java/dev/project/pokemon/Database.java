package dev.project.pokemon;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
    // File paths as specified in UML
    private static final String POKEMON_DATA_FILE = "pokemon_data.txt";
    private static final String PLAYER_SAVE_FILE = "player_save.txt";
    private static final String SCORES_FILE = "scores.txt";
    
    // Constructor
    public Database() {
        // Initialize files if they don't exist
        initializeFiles();
    }
    
    /**
     * Initialize data files if they don't exist
     */
    private void initializeFiles() {
        try {
            // Create Pokemon data file with default Pokemon if it doesn't exist
            File pokemonFile = new File(POKEMON_DATA_FILE);
            if (!pokemonFile.exists()) {
                createDefaultPokemonData();
            }
            
            // Create empty files for player and scores if they don't exist
            File playerFile = new File(PLAYER_SAVE_FILE);
            if (!playerFile.exists()) {
                playerFile.createNewFile();
            }
            
            File scoresFile = new File(SCORES_FILE);
            if (!scoresFile.exists()) {
                scoresFile.createNewFile();
            }
            
        } catch (IOException e) {
            System.err.println("Error initializing database files: " + e.getMessage());
        }
    }
    
    /**
     * Create default Pokemon data file with sample Pokemon
     */
    private void createDefaultPokemonData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(POKEMON_DATA_FILE))) {
            // Format: name|pokemonId|grade|maxHp|attack|defense|speed|type|moveName|movePower|moveType
            writer.println("Pikachu|001|5|100|80|60|90|ELECTRIC|Thunder Bolt|90|ELECTRIC");
            writer.println("Charmander|004|3|78|84|78|65|FIRE|Ember|40|FIRE");
            writer.println("Squirtle|007|3|79|83|100|43|WATER|Water Gun|40|WATER");
            writer.println("Bulbasaur|001|3|80|82|83|45|GRASS|Vine Whip|45|GRASS");
            writer.println("Geodude|074|4|80|120|130|20|ROCK|Rock Throw|50|ROCK");
            writer.println("Articuno|144|8|125|85|100|85|ICE|Ice Beam|90|ICE");
            writer.println("Nidoran|029|4|86|87|62|41|POISON|Poison Sting|15|POISON");
            writer.println("Raichu|026|6|120|90|55|110|ELECTRIC|Thunder|110|ELECTRIC");
            writer.println("Charizard|006|8|134|109|85|100|FIRE|Flamethrower|90|FIRE");
            writer.println("Blastoise|009|8|137|103|120|78|WATER|Hydro Pump|110|WATER");
            
            System.out.println("Default Pokemon data created successfully.");
            
        } catch (IOException e) {
            System.err.println("Error creating default Pokemon data: " + e.getMessage());
        }
    }
    
    /**
     * Load Pokemon roster from file
     * @return ArrayList of Pokemon loaded from file
     */
    public ArrayList<Pokemon> loadPokemonRoster() {
        ArrayList<Pokemon> pokemonList = new ArrayList<>();
        
        try (Scanner fileScanner = new Scanner(new File(POKEMON_DATA_FILE))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Pokemon pokemon = parsePokemonFromLine(line);
                    if (pokemon != null) {
                        pokemonList.add(pokemon);
                    }
                }
            }
            System.out.println("Loaded " + pokemonList.size() + " Pokemon from database.");
            
        } catch (FileNotFoundException e) {
            System.err.println("Pokemon data file not found: " + e.getMessage());
            createDefaultPokemonData();
            return loadPokemonRoster(); // Retry after creating default data
        } catch (Exception e) {
            System.err.println("Error loading Pokemon roster: " + e.getMessage());
        }
        
        return pokemonList;
    }
    
    /**
     * Parse a single Pokemon from a data line
     * @param line The data line to parse
     * @return Pokemon object or null if parsing failed
     */
    private Pokemon parsePokemonFromLine(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length >= 11) {
                String name = parts[0];
                String pokemonId = parts[1];
                int grade = Integer.parseInt(parts[2]);
                int maxHp = Integer.parseInt(parts[3]);
                int attack = Integer.parseInt(parts[4]);
                int defense = Integer.parseInt(parts[5]);
                int speed = Integer.parseInt(parts[6]);
                PokemonType type = PokemonType.valueOf(parts[7]);
                
                // Create move
                String moveName = parts[8];
                int movePower = Integer.parseInt(parts[9]);
                PokemonType moveType = PokemonType.valueOf(parts[10]);
                Move move = new Move(moveName, moveType, movePower);
                
                // Create Pokemon
                Pokemon pokemon = new Pokemon(name, pokemonId, grade, maxHp, attack, defense, speed, type);
                pokemon.setMove(move);
                
                return pokemon;
            }
        } catch (Exception e) {
            System.err.println("Error parsing Pokemon line: " + line + " - " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Save player data to file - UPDATED to support multiple players with improved error handling
     * @param player The player to save
     */
    public void savePlayer(Player player) {
        if (player == null || player.getName() == null || player.getName().trim().isEmpty()) {
            System.err.println("Cannot save player: Player or player name is null/empty");
            return;
        }
        
        ArrayList<Player> allPlayers = loadAllPlayers();
        
        // Find and update existing player or add new one
        boolean playerFound = false;
        for (int i = 0; i < allPlayers.size(); i++) {
            if (allPlayers.get(i).getName().equals(player.getName())) {
                allPlayers.set(i, player);
                playerFound = true;
                break;
            }
        }
        
        if (!playerFound) {
            allPlayers.add(player);
        }
        
        // Save all players back to file
        saveAllPlayers(allPlayers);
        System.out.println("Game progress saved!");
    }
    
    /**
     * Load all players from file
     * @return ArrayList of all saved players
     */
    private ArrayList<Player> loadAllPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        
        try (Scanner fileScanner = new Scanner(new File(PLAYER_SAVE_FILE))) {
            Player currentPlayer = null;
            String section = "";
            
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                
                if (line.equals("PLAYER_START")) {
                    if (currentPlayer != null) {
                        players.add(currentPlayer);
                    }
                    currentPlayer = new Player();
                    section = "";
                } else if (line.equals("PLAYER_END")) {
                    if (currentPlayer != null) {
                        players.add(currentPlayer);
                        currentPlayer = null;
                    }
                } else if (line.equals("PLAYER_INFO")) {
                    section = "PLAYER_INFO";
                } else if (line.equals("COLLECTION")) {
                    section = "COLLECTION";
                } else if (line.equals("PARTY")) {
                    section = "PARTY";
                } else if (!line.isEmpty() && currentPlayer != null) {
                    handlePlayerDataLine(currentPlayer, section, line);
                }
            }
            
            // Add last player if not properly closed
            if (currentPlayer != null) {
                players.add(currentPlayer);
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("No saved player data found. Starting with empty player list.");
        } catch (Exception e) {
            System.err.println("Error loading all players: " + e.getMessage());
        }
        
        return players;
    }
    
    /**
     * Save all players to file - IMPROVED WITH BETTER ERROR HANDLING
     * @param players ArrayList of all players to save
     */
    private void saveAllPlayers(ArrayList<Player> players) {
        // Create backup of existing file
        File originalFile = new File(PLAYER_SAVE_FILE);
        File backupFile = new File(PLAYER_SAVE_FILE + ".backup");
        
        try {
            if (originalFile.exists()) {
                // Create backup
                try (FileInputStream fis = new FileInputStream(originalFile);
                     FileOutputStream fos = new FileOutputStream(backupFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not create backup: " + e.getMessage());
        }
        
        // Save players to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(PLAYER_SAVE_FILE))) {
            
            for (Player player : players) {
                if (player == null || player.getName() == null) {
                    System.err.println("WARNING: Skipping null player or player with null name");
                    continue;
                }
                
                writer.println("PLAYER_START");
                
                // Save player basic info
                writer.println("PLAYER_INFO");
                writer.println(player.getName());
                writer.println(player.getScore());
                
                // Save Pokemon collection
                writer.println("COLLECTION");
                for (Pokemon pokemon : player.getPokemonCollection()) {
                    if (pokemon != null) {
                        String pokemonData = pokemonToString(pokemon);
                        writer.println(pokemonData);
                    }
                }
                
                // Save party list
                writer.println("PARTY");
                for (Pokemon pokemon : player.getPartyList()) {
                    if (pokemon != null) {
                        String pokemonData = pokemonToString(pokemon);
                        writer.println(pokemonData);
                    }
                }
                
                writer.println("PLAYER_END");
                writer.println(); // Empty line for readability
            }
            
            writer.flush(); // Force write to file
            
            // Delete backup if save was successful
            if (backupFile.exists()) {
                backupFile.delete();
            }
            
        } catch (IOException e) {
            System.err.println("Error saving player data: " + e.getMessage());
            
            // Try to restore backup
            if (backupFile.exists()) {
                try {
                    if (originalFile.exists()) {
                        originalFile.delete();
                    }
                    backupFile.renameTo(originalFile);
                    System.out.println("Backup restored due to save error");
                } catch (Exception restoreError) {
                    System.err.println("Could not restore backup: " + restoreError.getMessage());
                }
            }
        }
    }
    
    /**
     * Convert Pokemon to string format for saving - IMPROVED WITH NULL CHECKS
     * @param pokemon The Pokemon to convert
     * @return String representation
     */
    private String pokemonToString(Pokemon pokemon) {
        if (pokemon == null) {
            return "";
        }
        
        try {
            Move move = pokemon.getMove();
            if (move == null) {
                // Create a default move if none exists
                move = new Move("Tackle", PokemonType.ELECTRIC, 20); // Default move
            }
            
            String result = String.format("%s|%s|%d|%d|%d|%d|%d|%d|%s|%s|%d|%s",
                pokemon.getName() != null ? pokemon.getName() : "Unknown",
                pokemon.getPokemonId() != null ? pokemon.getPokemonId() : "000",
                pokemon.getGrade(),
                pokemon.getHp(),
                pokemon.getMaxHp(),
                pokemon.getAttack(),
                pokemon.getDefense(),
                pokemon.getSpeed(),
                pokemon.getType() != null ? pokemon.getType().toString() : "ELECTRIC",
                move.getName() != null ? move.getName() : "Tackle",
                move.getPower(),
                move.getMoveType() != null ? move.getMoveType().toString() : "ELECTRIC"
            );
            
            return result;
            
        } catch (Exception e) {
            System.err.println("Error converting Pokemon to string: " + e.getMessage());
            return ""; // Return empty string if conversion fails
        }
    }
    
    /**
     * Load specific player data by name - UPDATED to return null if not found
     * @param playerName The name of the player to load
     * @return Player object if found, null if not found
     */
    public Player loadPlayer(String playerName) {
        if (playerName == null || playerName.trim().isEmpty()) {
            System.err.println("Cannot load player: Player name is null or empty");
            return null;
        }
        
        ArrayList<Player> allPlayers = loadAllPlayers();
        
        for (Player player : allPlayers) {
            if (player.getName() != null && player.getName().equals(playerName)) {
                System.out.println("Found saved data for player: " + playerName);
                return player;
            }
        }
        
        System.out.println("No saved data found for player: " + playerName);
        return null; // Return null instead of creating new player
    }
    
    /**
     * Load player data from file - LEGACY method for backward compatibility
     * @return Player object or new Player if file doesn't exist
     */
    public Player loadPlayer() {
        ArrayList<Player> allPlayers = loadAllPlayers();
        if (!allPlayers.isEmpty()) {
            return allPlayers.get(0); // Return first player for backward compatibility
        }
        return new Player();
    }
    
    /**
     * Handle a single line of player data based on current section
     */
    private void handlePlayerDataLine(Player player, String section, String line) {
        try {
            switch (section) {
                case "PLAYER_INFO" -> {
                    if (player.getName() == null) {
                        player.setName(line);
                    } else {
                        try {
                            int score = Integer.parseInt(line);
                            player.setScore(score);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid score format: " + line);
                        }
                    }
                }
                case "COLLECTION" -> {
                    Pokemon collectionPokemon = parsePokemonFromSaveLine(line);
                    if (collectionPokemon != null) {
                        player.getPokemonCollection().add(collectionPokemon);
                    }
                }
                case "PARTY" -> {
                    Pokemon partyPokemon = parsePokemonFromSaveLine(line);
                    if (partyPokemon != null && player.getPartyList().size() < 3) {
                        player.getPartyList().add(partyPokemon);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error handling player data line: " + line + " - " + e.getMessage());
        }
    }
    
    /**
     * Parse Pokemon from saved data line (includes current HP) - IMPROVED ERROR HANDLING
     * @param line The data line to parse
     * @return Pokemon object or null if parsing failed
     */
    private Pokemon parsePokemonFromSaveLine(String line) {
        try {
            if (line == null || line.trim().isEmpty()) {
                return null;
            }
            
            String[] parts = line.split("\\|");
            if (parts.length >= 12) {
                String name = parts[0];
                String pokemonId = parts[1];
                int grade = Integer.parseInt(parts[2]);
                int currentHp = Integer.parseInt(parts[3]);
                int maxHp = Integer.parseInt(parts[4]);
                int attack = Integer.parseInt(parts[5]);
                int defense = Integer.parseInt(parts[6]);
                int speed = Integer.parseInt(parts[7]);
                PokemonType type = PokemonType.valueOf(parts[8]);
                
                // Create move
                String moveName = parts[9];
                int movePower = Integer.parseInt(parts[10]);
                PokemonType moveType = PokemonType.valueOf(parts[11]);
                Move move = new Move(moveName, moveType, movePower);
                
                // Create Pokemon
                Pokemon pokemon = new Pokemon(name, pokemonId, grade, maxHp, attack, defense, speed, type);
                pokemon.setHp(currentHp); // Set current HP
                pokemon.setMove(move);
                
                return pokemon;
            }
        } catch (Exception e) {
            System.err.println("Error parsing saved Pokemon: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Save high scores to file
     * @param scores ArrayList of ScoreEntry objects
     */
    public void saveScores(ArrayList<ScoreEntry> scores) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORES_FILE))) {
            for (ScoreEntry entry : scores) {
                writer.println(entry.getPlayerName() + "|" + entry.getScore());
            }
            
            System.out.println("Scores saved successfully.");
            
        } catch (IOException e) {
            System.err.println("Error saving scores: " + e.getMessage());
        }
    }
    
    /**
     * Load high scores from file
     * @return ArrayList of ScoreEntry objects
     */
    public ArrayList<ScoreEntry> loadScores() {
        ArrayList<ScoreEntry> scores = new ArrayList<>();
        
        try (Scanner fileScanner = new Scanner(new File(SCORES_FILE))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 2) {
                        try {
                            String playerName = parts[0];
                            int score = Integer.parseInt(parts[1]);
                            scores.add(new ScoreEntry(playerName, score));
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid score entry: " + line);
                        }
                    }
                }
            }
            
            System.out.println("Loaded " + scores.size() + " high scores.");
            
        } catch (FileNotFoundException e) {
            System.out.println("No saved scores found. Starting with empty score list.");
        } catch (Exception e) {
            System.err.println("Error loading scores: " + e.getMessage());
        }
        
        return scores;
    }
}
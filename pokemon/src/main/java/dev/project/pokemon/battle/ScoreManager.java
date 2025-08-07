
package dev.project.pokemon.battle;
import dev.project.pokemon.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreManager {
    private ArrayList<ScoreEntry> highScores;
    private Database database;
    private static final int MAX_HIGH_SCORES = 5;
    
    public ScoreManager(Database database) {
        this.database = database;
        this.highScores = database.loadScores();
    }

    public void addScore(String playerName, int score) {
        // Check if player already exists in high scores
        boolean playerExists = false;
        for (int i = 0; i < highScores.size(); i++) {
            ScoreEntry existingEntry = highScores.get(i);
            if (existingEntry.getPlayerName().equals(playerName)) {
                // Player exists - update only if new score is higher
                if (score > existingEntry.getScore()) {
                    highScores.set(i, new ScoreEntry(playerName, score));
                    System.out.println("Updated high score for " + playerName + ": " + score + " points (previous: " + existingEntry.getScore() + ")");
                } else {
                    System.out.println(playerName + "'s current score (" + score + ") is not higher than their best (" + existingEntry.getScore() + ")");
                }
                playerExists = true;
                break;
            }
        }

        // If player doesn't exist, add new entry
        if (!playerExists) {
            ScoreEntry newScore = new ScoreEntry(playerName, score);
            highScores.add(newScore);
            System.out.println("New high score added: " + newScore);
        }

        // Sort scores in descending order
        Collections.sort(highScores, new Comparator<ScoreEntry>() {
            @Override
            public int compare(ScoreEntry a, ScoreEntry b) {
                return Integer.compare(b.getScore(), a.getScore());
            }
        });

        // Keep only top 5 scores
        if (highScores.size() > MAX_HIGH_SCORES) {
            highScores = new ArrayList<>(highScores.subList(0, MAX_HIGH_SCORES));
        }

        // Save to file
        database.saveScores(highScores);
    }
    
    public ArrayList<ScoreEntry> getTopScores() {
        return new ArrayList<>(highScores);
    }
    
    // Added missing method that GameEngine calls
    public boolean isHighScore(int score) {
        if (highScores.size() < MAX_HIGH_SCORES) {
            return true; // If we don't have 5 scores yet, any score qualifies
        }
        
        // Check if score is higher than the lowest score in the list
        ScoreEntry lowestScore = highScores.get(highScores.size() - 1);
        return score > lowestScore.getScore();
    }
}
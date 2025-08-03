
package dev.project.pokemon;
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
        ScoreEntry newScore = new ScoreEntry(playerName, score);
        highScores.add(newScore);
        
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
        
        System.out.println("Score added: " + newScore);
    }
    
    public ArrayList<ScoreEntry> getTopScores() {
        return new ArrayList<>(highScores);
    }
    
    public void displayTopScores() {
        System.out.println("\n=== TOP 5 HIGH SCORES ===");
        if (highScores.isEmpty()) {
            System.out.println("No scores recorded yet.");
        } else {
            for (int i = 0; i < highScores.size(); i++) {
                System.out.println((i + 1) + ". " + highScores.get(i));
            }
        }
        System.out.println("========================\n");
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
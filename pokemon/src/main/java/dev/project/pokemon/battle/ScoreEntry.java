package dev.project.pokemon.battle;

public class ScoreEntry {
    // Attributes
    private String playerName;
    private int score;
    
    // Constructor
    public ScoreEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }
    
    // Getters
    public String getPlayerName() {
        return playerName;
    }
    
    public int getScore() {
        return score;
    }
    
    @Override
    public String toString() {
        return playerName + " - " + score + " points";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ScoreEntry that = (ScoreEntry) obj;
        return score == that.score && playerName.equals(that.playerName);
    }
    
    @Override
    public int hashCode() {
        return playerName.hashCode() + Integer.hashCode(score);
    }
}
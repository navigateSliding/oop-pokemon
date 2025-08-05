package dev.project.pokemon.pokemon;

public class Move {
    // Attributes
    private String name;
    private PokemonType moveType;
    private int power;
    
    // Constructor
    public Move(String name, PokemonType moveType, int power) {
        this.name = name;
        this.moveType = moveType;
        this.power = power;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getPower() {
        return power;
    }
    
    public void setPower(int power) {
        this.power = power;
    }
    
    public PokemonType getMoveType() {
        return moveType;
    }
    
    public void setMoveType(PokemonType moveType) {
        this.moveType = moveType;
    }
    
    // Methods
    public void useMove() {
        System.out.println("Using move: " + name + " (Power: " + power + ")");
    }
    
    @Override
    public String toString() {
        return name + " (" + moveType + ", Power: " + power + ")";
    }
}

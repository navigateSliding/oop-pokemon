package dev.project.pokemon.pokemon;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    // Attributes
    private String name;
    private String pokemonId;
    private int grade;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private int speed;
    private PokemonType type;
    private Move move;
    
    // Constructors
    public Pokemon(String name) {
        this.name = name;
        this.hp = 100;
        this.maxHp = 100;
    }
    
    public Pokemon(String name, String pokemonId, int grade, int maxHp, int attack, int defense, int speed, PokemonType type) {
        this.name = name;
        this.pokemonId = pokemonId;
        this.grade = grade;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.type = type;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPokemonId() {
        return pokemonId;
    }
    
    public int getGrade() {
        return grade;
    }
    
    public int getHp() {
        return hp;
    }
    
    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));
    }
    
    public int getMaxHp() {
        return maxHp;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public int getDefense() {
        return defense;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public Move getMove() {
        return move;
    }
    
    public void setMove(Move move) {
        this.move = move;
    }
    
    public PokemonType getType() {
        return type;
    }
    
    // TODO: need this if we use multiple moves
    public List<Move> getMoves() {
        List<Move> moves = new ArrayList<>();
        if (move != null) {
            moves.add(move);
        }
        return moves;
    }
    
    // Battle methods
    public void takeDamage(int damage) {
        this.hp = Math.max(0, this.hp - damage);
        System.out.println(name + " took " + damage + " damage! HP: " + hp + "/" + maxHp);
    }
    
    public boolean isDefeated() {
        return hp <= 0;
    }
    
    public void heal() {
        this.hp = maxHp;
        System.out.println(name + " has been fully healed!");
    }
    
    public String getDisplayInfo() {
        return String.format("%s (Grade %d) - HP: %d/%d, Type: %s", 
                           name, grade, hp, maxHp, type);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pokemon: ").append(name)
          .append(" | ID: ").append(pokemonId)
          .append(" | Grade: ").append(grade)
          .append(" | HP: ").append(hp).append("/").append(maxHp)
          .append(" | Type: ").append(type)
          .append(" | Attack: ").append(attack)
          .append(" | Defense: ").append(defense)
          .append(" | Speed: ").append(speed);
        
        if (move != null) {
            sb.append(" | Move: ").append(move.toString());
        }
        
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pokemon pokemon = (Pokemon) obj;
        return pokemonId != null ? pokemonId.equals(pokemon.pokemonId) : name.equals(pokemon.name);
    }
    
    @Override
    public int hashCode() {
        return pokemonId != null ? pokemonId.hashCode() : name.hashCode();
    }
}
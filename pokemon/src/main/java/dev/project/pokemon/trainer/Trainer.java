package dev.project.pokemon.trainer;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
   //attributes
    private String name;
    private int age;
    private List<Pokemon> allPokemons;
    private List<Pokemon> partyList; 
    private static final int MAX_PARTY_SIZE = 3;

    //constructor
    public Trainer() {
        this.allPokemons = new ArrayList<>();
        this.partyList = new ArrayList<>();
    }

    public Trainer(String name, int age) {
        this.name = name;
        this.age = age;
        this.allPokemons = new ArrayList<>();
        this.partyList = new ArrayList<>();
    }

    //setters and getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Pokemon> getAllPokemons() {
        return allPokemons;
    }

    public List<Pokemon> getpartyList() {
        return partyList;
    }

    //other methods
    public Move chooseMove() {
        if (!partyList.isEmpty()) {
            Pokemon pokemon = partyList.get(0);
            List<Move> moves = pokemon.getMoves();
            if (!moves.isEmpty()) {
                return moves.get(0); 
            }
        }
        return null;
    }

    public void switchPokemon() {
        if (partyList.size() > 1) {
            Pokemon current = partyList.remove(0);
            partyList.add(current);
            System.out.println("Switched to " + partyList.get(0).getName() + "!");
        } else {
            System.out.println("No other Pokemon to switch to.");
        }
    }

    public void addPokemon(Pokemon p) {
        if (!allPokemons.contains(p)) {
            System.out.println("You don't own this Pokémon.");
        } else if (partyList.contains(p)) {
            System.out.println("This Pokémon is already in your party.");
        } else if (partyList.size() < MAX_PARTY_SIZE) {
            partyList.add(p);
            System.out.println("Pokemon " + p.getName() + " added to your party!");
        } else {
            System.out.println("Party full! Remove a Pokemon before adding a new one.");
        }
    }

    public void removePokemon(Pokemon p) {
        if (partyList.remove(p)) {
            System.out.println("Pokemon " + p.getName() + " removed from your party.");
        } else {
            System.out.println("Pokemon not found in your party.");
        }
    }

    //toString method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Trainer: ").append(name)
          .append(" | Age: ").append(age)
          .append(" | Party: ");
        for (int i = 0; i < partyList.size(); i++) {
            sb.append(partyList.get(i).getName());
            if (i < partyList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

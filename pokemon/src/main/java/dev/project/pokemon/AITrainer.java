package dev.project.pokemon;

import dev.project.pokemon.pokemon.Move;
import dev.project.pokemon.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Collections;

public class AITrainer {
    // attributes
    private String name;
    private int age;
    private ArrayList<Pokemon> partyList;
    private static final int MAX_PARTY_SIZE = 3;

    // constructors
    public AITrainer() {
        this.partyList = new ArrayList<>();
    }

    public AITrainer(String name, int age) {
        this.name = name;
        this.age = age;
        this.partyList = new ArrayList<>();
    }

     // setters and getters
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

    public ArrayList<Pokemon> getPartyList() {
        return partyList;
    }

    // other methods
    public void autoSelectParty(ArrayList<Pokemon> available) {
        Collections.shuffle(available);
        partyList.clear();
        for (int i = 0; i < Math.min(MAX_PARTY_SIZE, available.size()); i++) {
            // Create copies to avoid modifying original data
            Pokemon original = available.get(i);
            Pokemon copy = new Pokemon(original.getName(), original.getPokemonId(), 
                                     original.getGrade(), original.getMaxHp(), 
                                     original.getAttack(), original.getDefense(), 
                                     original.getSpeed(), original.getType());
            copy.setMove(original.getMove());
            partyList.add(copy);
        }
        System.out.println("AITrainer " + name + " selected party Pokémon:");
        for (Pokemon p : partyList) {
            System.out.println("- " + p.getName());
        }
    }

    public void autoSwitchPokemon() {
        // Find next healthy Pokemon
        for (int i = 0; i < partyList.size(); i++) {
            if (!partyList.get(i).isDefeated()) {
                // Move the healthy Pokemon to front
                Pokemon healthyPokemon = partyList.remove(i);
                partyList.add(0, healthyPokemon);
                System.out.println(name + " switched to " + healthyPokemon.getName());
                return;
            }
        }
        System.out.println(name + " has no Pokémon to switch.");
    }

    public Move autoChooseMove() {
        // Get the first non-defeated Pokemon
        for (Pokemon pokemon : partyList) {
            if (!pokemon.isDefeated()) {
                if (pokemon.getMove() != null) {
                    return pokemon.getMove();
                }
            }
        }
        return null;
    }

    // toString method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AITrainer: ").append(name)
          .append(" | Age: ").append(age)
          .append(" | Party: [");
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

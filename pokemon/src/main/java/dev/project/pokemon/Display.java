package dev.project.pokemon;

import dev.project.pokemon.player.Player;
import dev.project.pokemon.pokemon.Pokemon;

public class Display {
    
    public void clearScreen() {
        // Clear screen for different operating systems
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[2J\033[H");
            }
        } catch (Exception e) {
            // If clearing fails, just print some newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public void displayTitleScreenMenu() {
        System.out.println(AsciiArt.getLogo());
        System.out.println("Welcome to Pokemon Battle & Catch!");
    }

    public void displayStartMenu() {
        System.out.println(AsciiArt.getStartMenu());
        System.out.print("Choose option: ");
    }

    public void displayCatchFailed() {
        System.out.println(AsciiArt.getCatchFailed());
    }

    public void displayDefeat() {
        System.out.println(AsciiArt.getDefeat());
    }

    public void displayGameOver() {
        System.out.println("\n=== GAME OVER ===");
        System.out.println("Thanks for playing!");
    }

    public void displayPokemonInfo(Pokemon pokemon) {
        System.out.println("\n=== POKEMON INFO ===");
        System.out.println("Name: " + pokemon.getName());
        System.out.println("ID: " + pokemon.getPokemonId());
        System.out.println("Grade: " + pokemon.getGrade());
        System.out.println("HP: " + pokemon.getHp() + "/" + pokemon.getMaxHp());
        System.out.println("Attack: " + pokemon.getAttack());
        System.out.println("Defense: " + pokemon.getDefense());
        System.out.println("Speed: " + pokemon.getSpeed());
        System.out.println("Type: " + pokemon.getType());
        if (pokemon.getMove() != null) {
            System.out.println("Move: " + pokemon.getMove().toString());
        }
        System.out.println("==================");
    }
    
    public void displayPlayerParty(Player player) {
        System.out.println("\n=== YOUR PARTY ===");
        if (player.getPartyList().isEmpty()) {
            System.out.println("No Pokemon in party.");
        } else {
            for (int i = 0; i < player.getPartyList().size(); i++) {
                Pokemon p = player.getPartyList().get(i);
                System.out.println((i + 1) + ". " + p.getDisplayInfo());
            }
        }
    }
    
    public void displayPlayerCollection(Player player) {
        System.out.println("\n=== YOUR COLLECTION ===");
        if (player.getPokemonCollection().isEmpty()) {
            System.out.println("No Pokemon in collection.");
        } else {
            for (int i = 0; i < player.getPokemonCollection().size(); i++) {
                Pokemon p = player.getPokemonCollection().get(i);
                String inParty = player.getPartyList().contains(p) ? " (In Party)" : "";
                System.out.println((i + 1) + ". " + p.getDisplayInfo() + inParty);
            }
        }
    }
}
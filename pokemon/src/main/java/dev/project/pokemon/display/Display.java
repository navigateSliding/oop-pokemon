package dev.project.pokemon.display;

import dev.project.pokemon.battle.ScoreEntry;
import dev.project.pokemon.player.Player;
import dev.project.pokemon.pokemon.Pokemon;

import java.util.ArrayList;

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

    public void displayFoundExistingPlayer(String playerName, Player player) {
        System.out.println("Welcome back, " + playerName + "!");
        System.out.println("Loaded your progress:");
        System.out.println("- Pokemon in collection: " + player.getPokemonCollection().size());
        System.out.println("- Pokemon in party: " + player.getPartyList().size());
        System.out.println("Current score: " + player.getScore());
    }

    public void displayGiveStarterPokemon() {
        System.out.println( "\n=== CHOOSE YOUR STARTER POKEMON ===\n" +
                            "1. Charmander (Fire Type)\n" +
                            "2. Squirtle (Water Type)\n" +
                            "3. Bulbasaur (Grass Type)");
    }

    public void displayStartMenu() {
        System.out.println(AsciiArt.getStartMenu());
        System.out.print("Choose option: ");
    }

    public void displaySwitchPokemon(String pokemonName) {
        System.out.println(AsciiArt.getSwitchPokemon());
        System.out.println("Switched to " + pokemonName + "!");
    }

    public void displayCritChanceTip() {
        System.out.println(AsciiArt.getCritChanceTip());
    }

    public void displayCatchFailed(Pokemon target) {
        System.out.println(AsciiArt.getCatchFailed());
        System.out.println("Oh no! " + target.getName() + " broke free!");
    }

    public void displayDefeat(String activePokemonName) {
        System.out.println(AsciiArt.getDefeat());
        System.out.printf(  "Your Pokemon(%s) was defeated\n" +
                            "Heal Your Pokemon in 'View Pokemon'\n", activePokemonName);
    }

    public void displayGameOver() {
        System.out.println("\n=== GAME OVER ===");
        System.out.println("Thanks for playing!");
    }

    public void displayWildPokemonBattleOptions(Pokemon wild, Pokemon playerPokemon) {
        System.out.println("\n=== WILD POKEMON BATTLE ===");
        System.out.println("Wild " + wild.getName() + " (HP: " + wild.getHp() + "/" + wild.getMaxHp() + ")");
        System.out.println("Your " + playerPokemon.getName() + " (HP: " + playerPokemon.getHp() + "/" + playerPokemon.getMaxHp() + ")");
        System.out.println("===========================");

        System.out.println( "\nWhat will you do?\n" +
                            "C - Catch " + wild.getName() + "\n" +
                            "F - Fight\n" +
                            "Q - Run away\n");
        System.out.print("Choose action: ");
    }

    public void displayPlayerTurn(Player player, Pokemon wild, Pokemon playerPokemon) {
        String dynamicSeparator = "=".repeat(player.getName().length());

        System.out.println("\n=== " + player.getName() + "'s Turn ===");
        System.out.println("Your " + playerPokemon.getName() + " (HP: " +
                playerPokemon.getHp() + "/" + playerPokemon.getMaxHp() + ")");
        System.out.println("Wild " + wild.getName() + " (HP: " +
                wild.getHp() + "/" + wild.getMaxHp() + ")");
        System.out.printf("====%s===========", dynamicSeparator);

        System.out.println( "\nWhat will " + playerPokemon.getName() + " do?\n" +
                            "1. Attack\n" +
                            "2. Switch Pokemon\n" +
                            "3. Back to main options");
        System.out.print("Choose action: ");
    }

    public void displayAttemptCatch() {
        System.out.println( "\n=== CATCHING ATTEMPT ===\n" +
                            "Choose your Poke Ball:\n" +
                            "1. Poke Ball (Standard)\n" +
                            "2. Great Ball (1.5x catch rate)\n" +
                            "3. Ultra Ball (2x catch rate)\n" +
                            "4. Master Ball (Guaranteed)\n");
        System.out.print("Choose ball (1-4): ");
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

    public void displayTopScores(ArrayList<ScoreEntry> highScores) {
        System.out.println("\n=== HIGH SCORES ===");
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
}
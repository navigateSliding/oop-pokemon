package dev.project.pokemon.trainer;

public class TestTrainer {
    public static void main(String[] args) {
        Trainer ash = new Trainer ("Ash", 10);

        // Assuming Moves class exists
        Move tackle = new Move("Tackle", 40);
        Move growl = new Move("Growl", 0);
        Move ember = new Move("Ember", 40);

        // Assuming Pokemon class exists
        Pokemon pikachu = new Pokemon("Pikachu");
        Pokemon charmander = new Pokemon("Charmander");
        Pokemon bulbasaur = new Pokemon("Bulbasaur");
        Pokemon squirtle = new Pokemon("Squirtle");

        // Add to Collection
        ash.addToCollection(pikachu);
        ash.addToCollection(charmander);
        ash.addToCollection(squirtle);
        ash.addToCollection(bulbasaur);
    
        // Add to Party
        ash.addPokemon(pikachu);
        ash.addPokemon(charmander);
        ash.addPokemon(squirtle);

        System.out.println(ash);

        // Try to add more than 3 pokemons
        ash.addPokemon(bulbasaur);

        ash.switchPokemon();
        ash.switchPokemon();

        System.out.println(ash);

        // Remove a pokemon and add new one
        ash.removePokemon(squirtle);
        ash.addPokemon(bulbasaur);

        System.out.println(ash);
    }
}

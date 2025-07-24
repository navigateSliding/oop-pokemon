import java.util.ArrayList;
import java.util.List;

public class Trainer {
    //attributes
    private String name;
    private int age;
    private List<Pokemon> pokemons;

    //constructor
    public Trainer() {

    }

    public Trainer(String name, int age) {
        this.name = name;
        this.age = age;
        this.pokemons = new ArrayList<>();
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

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    //other methods
    public Move chooseMove() {
        if (!pokemons.isEmpty()) {
            Pokemon pokemon = pokemons.get(0);
            List<Move> moves = pokemons.getMoves();
            if (!moves.isEmpty()) {
                return moves.get(0); 
            }
        }
        return null; 
    }

    public void switchPokemon() {
        if (pokemons.size() > 1) {
            Pokemon current = pokemons.remove(0);
            pokemons.add(current);
            System.out.println("Switched to " + pokemons.get(0).getName() + "!");
        } else {
            System.out.println("No other Pokemon to switch to!");
        }
    }

    public void addPokemon(Pokemon p) {
        pokemons.add(p);
        System.out.println("Pokemon " + p.getName() + "added to your team!");
    }

    public void removePokemon(Pokemon p) {
        pokemons.remove(p);
        System.out.println("Pokemon " + p.getName() + " removed from your team!");
    }

    //toString method
    @Override
    public String toString() {
        return "Trainer: " + name + ", Age: " + age + ", Pokemons: " + pokemons;
    }
}

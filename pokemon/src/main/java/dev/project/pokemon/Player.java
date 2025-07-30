import java.util.ArrayList;
import java.util.List;

public class Player {
    //attributes
    private String name;
    private ArrayList<Pokemon> pokemonCollection;
    private ArrayList<Pokemon> partyList; 
    private int score;
    private static final int MAX_PARTY_SIZE = 3;

    //constructor
    public Player() {
        this.pokemonCollection = new ArrayList<>();
        this.partyList = new ArrayList<>();
    }

    public Player(String name) {
        this.name = name;
        this.pokemonCollection = new ArrayList<>();
        this.partyList = new ArrayList<>();
        this.score = 0;
    }

    //setters and getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Pokemon> getPokemonCollection() {
        return pokemonCollection;
    }

    public ArrayList<Pokemon> getPartyList() {
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
        if (!pokemonCollection.contains(p)) {
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

    public void addToCollection(Pokemon p) {
        if (!pokemonCollection.contains(p)) {
            pokemonCollection.add(p);
            System.out.println("Pokemon " + p.getName() + " added to your collection!");
        } else {
            System.out.println("This Pokémon is already in your collection.");
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


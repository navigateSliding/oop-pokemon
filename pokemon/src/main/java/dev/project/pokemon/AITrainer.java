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
            partyList.add(available.get(i));
        }
        System.out.println("AITrainer " + name + " selected party Pokémon:");
        for (Pokemon p : partyList) {
            System.out.println("- " + p.getName());
        }
    }

    public void autoSwitchPokemon() {
        if (!partyList.isEmpty()) {
            Collections.shuffle(partyList);
            Pokemon switched = partyList.get(0);
            System.out.println(name + " switched to " + switched.getName());
        } else {
            System.out.println(name + " has no Pokémon to switch.");
        }
    }

    public Move autoChooseMove() {
        if (!partyList.isEmpty()) {
            Pokemon pokemon = partyList.get(0);
            ArrayList<Move> moves = pokemon.getMoves();
            if (!moves.isEmpty()) {
                Move strongest = moves.get(0);
                for (Move m : moves) {
                    if (m.getPower() > strongest.getPower()) {
                        strongest = m;
                    }
                }
                return strongest;
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


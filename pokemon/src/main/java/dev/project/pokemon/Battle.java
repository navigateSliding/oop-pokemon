import java.util.ArrayList;

public class Battle {
    private Player player;
    private AITrainer aiTrainer;
    private Display display;

    public Battle(Player player, AITrainer aiTrainer) {
        this.player = player;
        this.aiTrainer = aiTrainer;
        this.display = new Display();
    }

    public void start() {
        System.out.println("\n=== Battle Start ===");
        ArrayList<Pokemon> playerParty = player.getPartyList();
        ArrayList<Pokemon> aiParty = aiTrainer.getPartyList();

        int playerIndex = 0;
        int aiIndex = 0;

        while (playerIndex < playerParty.size() && aiIndex < aiParty.size()) {
            Pokemon playerPokemon = playerParty.get(playerIndex);
            Pokemon aiPokemon = aiParty.get(aiIndex);

            System.out.println("\n" + player.getName() + " sends out " + playerPokemon.getName());
            System.out.println(aiTrainer.getName() + " sends out " + aiPokemon.getName());

            while (!playerPokemon.isDefeated() && !aiPokemon.isDefeated()) {
                playerTurn(playerPokemon, aiPokemon);
                if (aiPokemon.isDefeated()) {
                    System.out.println(aiPokemon.getName() + " is defeated!");
                    aiIndex++;
                    break;
                }

                enemyTurn(aiPokemon, playerPokemon);
                if (playerPokemon.isDefeated()) {
                    System.out.println(playerPokemon.getName() + " is defeated!");
                    playerIndex++;
                    break;
                }
            }
        }

        if (playerIndex < playerParty.size()) {
            System.out.println("\n" + player.getName() + " wins the battle!");
        } else {
            System.out.println("\n" + aiTrainer.getName() + " wins the battle!");
        }
    }

    private void playerTurn(Pokemon playerPokemon, Pokemon aiPokemon) {
        Move move = playerPokemon.getMove();
        System.out.println(playerPokemon.getName() + " uses " + move.getName() + "!");
        int damage = calculateDamage(playerPokemon, aiPokemon);
        aiPokemon.takeDamage(damage);
        System.out.println(aiPokemon.getName() + " takes " + damage + " damage! Remaining HP: " + aiPokemon.getHp());
    }

    private void enemyTurn(Pokemon aiPokemon, Pokemon playerPokemon) {
        Move move = aiPokemon.getMove();
        System.out.println(aiPokemon.getName() + " uses " + move.getName() + "!");
        int damage = calculateDamage(aiPokemon, playerPokemon);
        playerPokemon.takeDamage(damage);
        System.out.println(playerPokemon.getName() + " takes " + damage + " damage! Remaining HP: " + playerPokemon.getHp());
    }

    private int calculateDamage(Pokemon attacker, Pokemon defender) {
        int baseDamage = attacker.getAttack() - defender.getDefense();
        if (baseDamage < 1) baseDamage = 1;

        double effectiveness = getEffectiveness(attacker.getMove().getMoveType(), defender.getType());
        baseDamage *= effectiveness;

        return baseDamage;
    }
}
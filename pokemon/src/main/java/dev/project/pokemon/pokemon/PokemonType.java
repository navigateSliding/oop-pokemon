package dev.project.pokemon.pokemon;

import java.util.List;

public enum PokemonType {
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    ROCK,
    ICE,
    POISON;

    private List<PokemonType> strongAgainst;

    static {
        FIRE.strongAgainst = List.of(GRASS, ICE);
        WATER.strongAgainst = List.of(FIRE, ROCK);
        GRASS.strongAgainst = List.of(WATER, ROCK);
        ELECTRIC.strongAgainst = List.of(WATER);
        ROCK.strongAgainst = List.of(FIRE, ICE);
        ICE.strongAgainst = List.of(GRASS);
        POISON.strongAgainst = List.of(GRASS);
    }

    public List<PokemonType> strongAgainst() {
        return strongAgainst;
    }
    
    /**
     * Get type effectiveness multiplier
     * @param defenderType The defending Pokemon's type
     * @return 2.0 for super effective, 0.5 for not very effective, 1.0 for normal
     */
    public double getEffectiveness(PokemonType defenderType) {
        if (this.strongAgainst.contains(defenderType)) {
            return 2.0; // Super effective
        }
        
        // Check if defender is strong against this type (not very effective)
        if (defenderType.strongAgainst.contains(this)) {
            return 0.5; // Not very effective
        }
        
        return 1.0; // Normal effectiveness
    }
}
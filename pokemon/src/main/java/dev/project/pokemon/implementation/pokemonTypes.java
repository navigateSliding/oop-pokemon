package dev.project.pokemon.implementation;

import java.util.List;

public enum pokemonTypes {
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    ROCK,
    ICE,
    POISON;

    private List<pokemonTypes> strongAgainst;

    static {
        FIRE.strongAgainst = List.of(ROCK, FIRE);
        WATER.strongAgainst = List.of(ROCK, FIRE);
        GRASS.strongAgainst = List.of(ROCK, WATER);
        ELECTRIC.strongAgainst = List.of(WATER);
        ROCK.strongAgainst = List.of(FIRE, ICE);
        ICE.strongAgainst = List.of(GRASS);
        POISON.strongAgainst = List.of(GRASS);
    }

    public List<pokemonTypes> strongAgainst() {
        return strongAgainst;
    }
//    FIRE("GRASS", "ICE"),
//    WATER("ROCK", "FIRE"),
//    GRASS("ROCK", "WATER"),
//    ELECTRIC("WATER", ""),
//    ROCK("FIRE", "ICE"),
//    ICE("GRASS", ""),
//    POISON("GRASS", "");
}

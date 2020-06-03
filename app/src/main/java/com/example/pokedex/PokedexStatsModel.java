package com.example.pokedex;

public class PokedexStatsModel {

    private String pokeStatsName;
    private String pokeStatsValue;

    public PokedexStatsModel(String pokeStatsName, String pokeStatsValue) {
        this.pokeStatsName = pokeStatsName;
        this.pokeStatsValue = pokeStatsValue;
    }

    public String getPokeStatsName() { return pokeStatsName; }

    public String getPokeStatsValue() { return pokeStatsValue; }
}


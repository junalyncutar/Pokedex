package com.example.pokedex;

public class PokedexItem {

    private String pokeName;
    private String pokeUrl;

    public PokedexItem(String pokeName, String pokeUrl) {
        this.pokeName = pokeName;
        this.pokeUrl = pokeUrl;
    }

    public String getPokeName() { return pokeName; }

    public String getPokeUrl() { return pokeUrl; }
}


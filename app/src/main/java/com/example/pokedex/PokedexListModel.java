package com.example.pokedex;

public class PokedexListModel {

    private String pokeName;
    private String pokeUrl;

    public PokedexListModel(String pokeName, String pokeUrl) {
        this.pokeName = pokeName;
        this.pokeUrl = pokeUrl;
    }

    public String getPokeName() { return pokeName; }

    public String getPokeUrl() { return pokeUrl; }
}


package com.example.recipebook.helper;

import com.example.recipebook.model.Recipe;

import java.util.ArrayList;

public class DataHolder {
    private static DataHolder instance;
    private ArrayList<Recipe> recipes = new ArrayList<>();

    private DataHolder() {}

    public static DataHolder getInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}


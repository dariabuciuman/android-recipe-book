package com.example.recipebook.model;

import java.util.List;

public class Recipe {
    private Long id;
    private String title;
    private String image;
    private String readyInMinutes;
    private List<String> ingredients;
    private List<String > instructions;
    private String sourceURL;

    public Recipe(Long id, String title, String image, String readyInMinutes, List<String> ingredients, List<String> instructions, String sourceURL) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.readyInMinutes = readyInMinutes;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.sourceURL = sourceURL;
    }

    public Recipe(Long id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(String readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String>instructions) {
        this.instructions = instructions;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public String ingredientsToString(){
        String allIngredients = "Ingredients: \n";
        for (int i = 0; i < ingredients.size(); i++) {
            allIngredients += ingredients.get(i) + "\n";
        }
        //allIngredients += ingredients.get(ingredients.size() - 1) + ".";
        return allIngredients;
    }
}

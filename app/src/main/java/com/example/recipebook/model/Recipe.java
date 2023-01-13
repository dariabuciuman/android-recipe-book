package com.example.recipebook.model;

import java.util.List;

public class Recipe {
    private Long id;
    private String title;
    private String image;
    private String readyInMinutes;
    private List<String> ingredients;
    private List<String> steps;

    public Recipe(Long id, String title, String image, String readyInMinutes, List<String> ingredients, List<String> steps) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.readyInMinutes = readyInMinutes;
        this.ingredients = ingredients;
        this.steps = steps;
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

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}

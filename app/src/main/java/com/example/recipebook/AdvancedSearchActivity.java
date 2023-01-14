package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class AdvancedSearchActivity extends AppCompatActivity {
    EditText searchAdvancedRecipeInput, includeIngredientsInput, excludeIngredientsInput;
    CheckBox dairy, gluten, peanut, egg, sesame, soy, seafood, shellfish, wheat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        searchAdvancedRecipeInput = findViewById(R.id.searchAdvancedRecipeInput);
        includeIngredientsInput = findViewById(R.id.includeIngredientsInput);
        excludeIngredientsInput = findViewById(R.id.excludeIngredientsInput);

        dairy = findViewById(R.id.dairyCheckBox);
        gluten = findViewById(R.id.glutenCheckBox);
        peanut = findViewById(R.id.peanutCheckBox);
        egg = findViewById(R.id.eggCheckBox);
        sesame = findViewById(R.id.sesameCheckBox);
        soy = findViewById(R.id.soyCheckBox);
        seafood = findViewById(R.id.seafoodCheckBox);
        shellfish = findViewById(R.id.shellfishCheckBox);
        wheat = findViewById(R.id.wheatCheckBox);

    }

    public void onBackClickAdvanced(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onNextClickAdvanced(View view){
        String query = searchAdvancedRecipeInput.getText().toString();
        String includeIngredients = includeIngredientsInput.getText().toString();
        String excludeIngredients = excludeIngredientsInput.getText().toString();
        String intolerances = "";
        if(dairy.isChecked())
            intolerances += "dairy,";
        if(gluten.isChecked())
            intolerances += "gluten,";
        if(peanut.isChecked())
            intolerances += "peanut,";
        if(egg.isChecked())
            intolerances += "egg,";
        if(sesame.isChecked())
            intolerances += "sesame,";
        if(soy.isChecked())
            intolerances += "soy,";
        if(seafood.isChecked())
            intolerances += "seafood,";
        if(shellfish.isChecked())
            intolerances += "shellfish,";
        if(wheat.isChecked())
            intolerances += "wheat,";
        Intent intent = new Intent(this, AdvancedResultActivity.class);
        String queryURL = "https://api.spoonacular.com/recipes/complexSearch?query=" + query +
                "&includeIngredients=" + includeIngredients + "&excludeIngredients=" +
                excludeIngredients + "&intolerances=" + intolerances;
        intent.putExtra("queryURL", queryURL);
        startActivity(intent);
    }
}
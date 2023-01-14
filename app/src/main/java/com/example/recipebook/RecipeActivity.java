package com.example.recipebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipebook.helper.DownloadImageFromInternet;
import com.example.recipebook.helper.HttpRequest;
import com.example.recipebook.model.Recipe;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    ImageView imageView;
    TextView titleTextView, minutesTextView, sourceURL;
    ListView ingredientsListView, stepsListView;
    ArrayAdapter<String> arrayAdapter, arrayAdapter2;
    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Long id = getIntent().getLongExtra("id", 0);
        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");

        titleTextView = findViewById(R.id.titleTextView);
        imageView = findViewById(R.id.recipeImageView);
        minutesTextView = findViewById(R.id.minutesTextView);
        ingredientsListView = findViewById(R.id.ingredientsListView);
        stepsListView = findViewById(R.id.stepsListView);
        sourceURL = findViewById(R.id.URLTextView);
        getRecipeInformation(getApplicationContext(), id);
        new DownloadImageFromInternet(imageView).execute(image);
    }

    public void onBackClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("back", true);
        startActivity(intent);
    }

    public void getRecipeInformation(Context context, Long id) {
        HttpRequest httpRequest = new HttpRequest();
        try {
            Recipe recipe = httpRequest.getRecipeInformation(context, id);
            titleTextView.setText(recipe.getTitle());
            minutesTextView.setText(recipe.getReadyInMinutes() + " min");
            arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
            ingredientsListView.setAdapter(arrayAdapter);
            arrayAdapter.addAll(recipe.getIngredients());
            arrayAdapter.notifyDataSetChanged();
            arrayAdapter2 = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
            stepsListView.setAdapter(arrayAdapter2);
            arrayAdapter2.addAll(recipe.getInstructions());
            arrayAdapter2.notifyDataSetChanged();
            sourceURL.setText(recipe.getSourceURL());
        } catch (InterruptedException | IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}

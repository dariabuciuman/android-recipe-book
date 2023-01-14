package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.recipebook.helper.HttpRequest;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.recycler.RecipeAdapter;
import com.example.recipebook.recycler.RecyclerViewInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class AdvancedResultActivity extends AppCompatActivity implements RecyclerViewInterface {
    private static final String TAG = "AdvancedResultActivity";
    RecyclerView advancedRecyclerView;
    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_result);

        String queryURL = getIntent().getStringExtra("queryURL");
        Log.i(TAG, "onCreate: queryURL " + queryURL);
        advancedRecyclerView = findViewById(R.id.advancedRecyclerView);
        queryRecipes(queryURL);
    }

    private void queryRecipes(String URL) {
        HttpRequest httpRequest = new HttpRequest();
        Integer totalResults = 0;
        Integer number = 0;
        recipes = new ArrayList<>();
        try {
            String response = httpRequest.getRecipesByURL(URL);
            JSONObject jsonObject = new JSONObject(response);
            totalResults = jsonObject.getInt("totalResults");
            number = jsonObject.getInt("number");
            if (totalResults == 0)
                Toast.makeText(AdvancedResultActivity.this, "Could not find any recipe with that title", Toast.LENGTH_SHORT).show();
            else {
                int n;
                if (totalResults <= 10)
                    n = totalResults;
                else n = number;
                for (int i = 0; i < n; i++) {
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    String title = jsonArray.getJSONObject(i).getString("title");
                    String image = jsonArray.getJSONObject(i).getString("image");
                    Long id = jsonArray.getJSONObject(i).getLong("id");
                    Recipe recipe = new Recipe(id, title, image);
                    recipes.add(recipe);
                }
            }
        } catch (JSONException | InterruptedException | IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong. Try again.", Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG, "onClickSearch: number " + totalResults);
        if (totalResults > 0) {
            RecipeAdapter adapter = new RecipeAdapter(AdvancedResultActivity.this, recipes, this);
            Log.i(TAG, "onClickSearch: array size " + recipes.size());
            advancedRecyclerView.setAdapter(adapter);
            advancedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            for (int i = 0; i < recipes.size(); i++) {
                Log.i(TAG, "onClickSearch: Array " + i + " " + recipes.get(i).getTitle());
            }
        }
    }

    public void onClickBackToAdvancedSearch(View view) {
        Intent intent = new Intent(this, AdvancedSearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Log.i(TAG, "onItemClick: position " + position);
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("id", recipes.get(position).getId());
        intent.putExtra("image", recipes.get(position).getImage());
        intent.putExtra("activity", "AdvancedResultActivity");
        startActivity(intent);
    }
}
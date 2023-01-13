package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private static final String TAG = "MainActivity";
    EditText searchRecipeInput;
    RecyclerView recyclerView;
    HttpRequest httpRequest = new HttpRequest();
    ArrayList<Recipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchRecipeInput = findViewById(R.id.searchRecipeInput);
        recyclerView = findViewById(R.id.recyclerView);

        // Greeting dialog
        handleGreetingDialog();
    }

    public void onClickSearch(View view) {
        String query = searchRecipeInput.getText().toString();
        Integer totalResults = 0;
        Integer number = 0;
        recipes = new ArrayList<>();
        try {
            String response = httpRequest.getRecipe(getApplicationContext(), query);
            JSONObject jsonObject = new JSONObject(response);
            totalResults = jsonObject.getInt("totalResults");
            number = jsonObject.getInt("number");
            if (totalResults == 0)
                Toast.makeText(this, "Could not find any recipe with that title", Toast.LENGTH_SHORT).show();
            else{
                int n;
                if(totalResults <= 10)
                    n = totalResults;
                else n = number;
                for (int i = 0; i < n; i++) {
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    String title = jsonArray.getJSONObject(i).getString("title");
                    String image = jsonArray.getJSONObject(i).getString("image");
                    Long id = jsonArray.getJSONObject(i).getLong("id");
                    Recipe recipe = new Recipe(id, title, image);
                    recipes.add(recipe);
                }}
        } catch (JSONException | InterruptedException | IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong. Try again.", Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG, "onClickSearch: number " + totalResults);
        if (totalResults > 0) {
            RecipeAdapter adapter = new RecipeAdapter(this, recipes, this);
            Log.i(TAG, "onClickSearch: array size " + recipes.size());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            for (int i = 0; i < recipes.size(); i++) {
                Log.i(TAG, "onClickSearch: Array " + i + " " + recipes.get(i).getTitle());
            }
        }
    }

    private void handleGreetingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_info, null);
        CheckBox checkBox = view.findViewById(R.id.checkBox);
        builder.setTitle("Welcome to your online recipe book!");
        builder.setMessage("\nThis app allows you to search recipes by word, ingredients or type of dish. " +
                "Also, you can exclude recipes by intolerances and ingredients.\n\nIf you like a recipe, you " +
                "can save it on your device, so you can have some recipes even when you don't have an internet connection.");
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    storeDialogStatus(true);
                } else {
                    storeDialogStatus(false);
                }
            }
        });

        if (getDialogStatus()) {
            alertDialog.hide();
        } else {
            alertDialog.show();
        }
    }

    private void storeDialogStatus(boolean isChecked) {
        SharedPreferences sharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("greeting", isChecked);
        editor.apply();
    }

    private boolean getDialogStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        return sharedPreferences.getBoolean("greeting", false);
    }

    @Override
    public void onItemClick(int position) {
        Log.i(TAG, "onItemClick: position " + position);
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("id", recipes.get(position).getId());
        intent.putExtra("title", recipes.get(position).getTitle());
        intent.putExtra("image", recipes.get(position).getImage());
        startActivity(intent);
    }
}
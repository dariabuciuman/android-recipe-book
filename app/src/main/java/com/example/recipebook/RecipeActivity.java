package com.example.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipebook.helper.DownloadImageFromInternet;
import com.example.recipebook.model.Recipe;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    ImageView imageView;
    TextView titleTextView, idTextView;
    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Long id = getIntent().getLongExtra("id", 0);
        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");

        titleTextView = findViewById(R.id.titleTextView);
        idTextView = findViewById(R.id.idTextView);
        imageView = findViewById(R.id.recipeImageView);

        titleTextView.setText(title);
        idTextView.setText(id.toString());
        new DownloadImageFromInternet(imageView).execute(image);
    }

    public void onBackClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("back", true);
        startActivity(intent);
    }
}

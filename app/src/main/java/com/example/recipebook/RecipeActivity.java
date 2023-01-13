package com.example.recipebook;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipebook.helper.DownloadImageFromInternet;

public class RecipeActivity extends AppCompatActivity {
    ImageView imageView;
    TextView titleTextView, idTextView;

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
}

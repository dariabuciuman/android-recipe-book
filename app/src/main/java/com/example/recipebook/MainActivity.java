package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Greeting dialog
        handleGreetingDialog();
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
                if(compoundButton.isChecked()){
                    storeDialogStatus(true);
                }else{
                    storeDialogStatus(false);
                }
            }
        });

        if(getDialogStatus()){
            alertDialog.hide();
        }else{
            alertDialog.show();
        }
    }

    private void storeDialogStatus(boolean isChecked){
        SharedPreferences sharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("greeting", isChecked);
        editor.apply();
    }

    private boolean getDialogStatus(){
        SharedPreferences sharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        return sharedPreferences.getBoolean("greeting", false);
    }
}
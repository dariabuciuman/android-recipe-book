package com.example.recipebook.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.R;

public class MyViewHolder2 extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameTextView;

    public MyViewHolder2(View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);

        imageView = itemView.findViewById(R.id.recipeImageView);
        nameTextView = itemView.findViewById(R.id.nameTextView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerViewInterface != null){
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            }
        });
    }
}
package com.example.closetmanagementservicesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddMenu extends AppCompatActivity {

    protected void AddBtn(View addView) {

        Button BtnAddClothes = (Button) addView.findViewById(R.id.btnAddClothes);
        Button BtnAddCodey = (Button) addView.findViewById(R.id.btnAddCodey);

        BtnAddClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addView.getContext(), Post.class);
                addView.getContext().startActivity(intent);
            }
        });
        BtnAddCodey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addView.getContext(), CodyAdd.class);
                addView.getContext().startActivity(intent);
            }
        });
    }
}

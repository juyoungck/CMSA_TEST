package com.example.closetmanagementservicesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddMenu extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmenu);

        Button BtnAddClothes = (Button) findViewById(R.id.btnAddClothes);
        Button BtnAddCodey = (Button) findViewById(R.id.btnAddCodey);

        BtnAddClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Post.class);
                startActivity(intent);
            }
        });

        BtnAddCodey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CodyAdd.class);
                startActivity(intent);
            }
        });
    }
}

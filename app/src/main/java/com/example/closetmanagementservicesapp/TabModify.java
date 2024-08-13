package com.example.closetmanagementservicesapp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class TabModify extends AppCompatActivity {
    protected void modifyButton(View view) {

        Button modify_Add = (Button) view.findViewById(R.id.modify_Add);
        Button modify_Modify = (Button) view.findViewById(R.id.modify_Modify);
        Button modify_delete = (Button) view.findViewById(R.id.modify_delete);
        ImageButton modify_closet_edit = (ImageButton) view.findViewById(R.id.modify_closet_edit);
        TextView closet1 = (TextView) view.findViewById(R.id.closet1);
        EditText modify_closet_editText = (EditText) view.findViewById(R.id.modify_closet_editText);
        ImageButton modify_closet_delete = (ImageButton) view.findViewById(R.id.modify_closet_delete);

        modify_closet_edit.setVisibility(View.INVISIBLE);
        modify_closet_delete.setVisibility(View.INVISIBLE);
        modify_closet_editText.setVisibility(View.INVISIBLE);

        modify_Modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_closet_edit.setVisibility(View.VISIBLE);
            }
        });

        modify_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_closet_delete.setVisibility(View.VISIBLE);
            }
        });
    }
}

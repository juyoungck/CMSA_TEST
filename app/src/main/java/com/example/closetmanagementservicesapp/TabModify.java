package com.example.closetmanagementservicesapp;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class TabModify {

    public void modifyButton(View modifyView) {
        Button modify_Add = modifyView.findViewById(R.id.modify_Add);
        Button modify_Modify = modifyView.findViewById(R.id.modify_Modify);
        Button modify_delete = modifyView.findViewById(R.id.modify_delete);
        ImageButton modify_closet_edit = modifyView.findViewById(R.id.modify_closet_edit);
        TextView closet1 = modifyView.findViewById(R.id.closet1);
        EditText modify_closet_editText = modifyView.findViewById(R.id.modify_closet_editText);
        ImageButton modify_closet_delete = modifyView.findViewById(R.id.modify_closet_delete);

        modify_closet_edit.setVisibility(View.INVISIBLE);
        modify_closet_delete.setVisibility(View.INVISIBLE);
        modify_closet_editText.setVisibility(View.INVISIBLE);

        /* 추가 버튼 클릭시 다이얼로그
        충돌 발생해서 protected void --> public void 으로 변경했습니다.*/

        modify_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(modifyView.getContext());
                builder.setTitle("옷장 추가");

                final EditText input = new EditText(modifyView.getContext());
                input.setHint("옷장 이름을 입력해주세요.");
                builder.setView(input);

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String closetName = input.getText().toString();
                        if (!closetName.isEmpty()) {
                            Toast.makeText(modifyView.getContext(), "입력한 옷장 이름: " + closetName, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(modifyView.getContext(), "옷장 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.show();
            }
        });

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

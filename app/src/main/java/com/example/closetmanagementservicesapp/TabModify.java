package com.example.closetmanagementservicesapp;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TabModify {

    ListView listView;
    ArrayList<String> itemData;
    TabModifyAdapter tabModifyAdapter;


    public void modifyButton(View modifyView) {
        Button modify_Add = modifyView.findViewById(R.id.modify_Add);
        CheckBox modify_Modify = modifyView.findViewById(R.id.modify_Modify);

        // 리스트뷰 참조
        listView = modifyView.findViewById(R.id.list_item);
        itemData = new ArrayList<String>();

        // 어댑터 클래스 참고
        tabModifyAdapter = new TabModifyAdapter(modifyView.getContext(), itemData, new TabModifyAdapter.DeleteItem(){
            @Override
            public void Delete(int position) {
                itemData.remove(position);
                tabModifyAdapter.notifyDataSetChanged();
            }
        });


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
                                int dataNo = itemData.size() + 1; // 리스트뷰의 아이템을 차례대로 추가
                                listView = modifyView.findViewById(R.id.list_item);
                                listView.setAdapter(tabModifyAdapter);
                                itemData.add(closetName);
                                tabModifyAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(modifyView.getContext(), "옷장 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.show();
            }
        });

        // 수정 - 수정 버튼 동작
        modify_Modify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tabModifyAdapter.setVisibility(isChecked);
                if(modify_Modify.isChecked()) {
                    modify_Modify.setBackgroundColor(Color.parseColor("#ff6e6e"));
                    modify_Modify.setText("취소");
                    modify_Add.setEnabled(false);
                } else {
                    modify_Modify.setBackgroundColor(Color.parseColor("#e9ecef"));
                    modify_Modify.setText("수정");
                    modify_Add.setEnabled(true);
                }
            }
        });
    }
}

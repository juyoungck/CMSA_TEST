package com.example.closetmanagementservicesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TabModifyAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<String> data;
    boolean isVisible;
    DeleteItem deleteItem;


    public TabModifyAdapter(Context context, ArrayList<String>data, DeleteItem deleteItem) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.isVisible = false;
        this.deleteItem = deleteItem;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View adapterView = layoutInflater.inflate(R.layout.tab_modify_item, null);

        TextView modifytext = adapterView.findViewById(R.id.modify_closet_Text);
        ImageButton modifyedit = adapterView.findViewById(R.id.modify_closet_edit);
        ImageButton modifydelete = adapterView.findViewById(R.id.modify_closet_delete);

        // 수정 버튼 누를 시 아이콘 표시 / 숨기기
        modifyedit.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        modifydelete.setVisibility(isVisible ? View.VISIBLE : View.GONE);

        modifydelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("경고");
                builder.setMessage("정말 삭제하시겠습니까?");

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (deleteItem != null) {
                            deleteItem.Delete(position);
                            Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        String item = data.get(position);

        modifytext.setText(item);

        modifyedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("이름 수정");

                final EditText input = new EditText(context);
                input.setText(item); // 현재 이름을 EditText에 설정
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
                        String newName = input.getText().toString();
                        if (!newName.isEmpty()) {
                            data.set(position, newName); // 데이터 변경
                            notifyDataSetChanged(); // 리스트뷰 갱신
                            Toast.makeText(context, "이름이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.show();
            }
        });

        return adapterView;
    }

    public void setVisibility(boolean visible) {
        this.isVisible = visible;
        notifyDataSetChanged();
    }

    public interface DeleteItem {
        void Delete(int position);
    }

}

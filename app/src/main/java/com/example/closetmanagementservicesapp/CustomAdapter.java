package com.example.closetmanagementservicesapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends ArrayAdapter<HashMap<String, String>> {

    private Context mContext;
    private ArrayList<HashMap<String, String>> mData;

    public CustomAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        super(context, 0, data);
        mContext = context;
        mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        HashMap<String, String> item = mData.get(position);

        TextView textView = listItem.findViewById(R.id.textView);
        textView.setText(item.get("Name")); // "Name" 값 설정

        ImageView imageView = listItem.findViewById(R.id.listview_img);
        String imageUrl = item.get("Img_Src"); // "Img_Src" 값 가져오기

        // Glide를 사용하여 이미지 로드 및 표시
        Glide.with(mContext)
                .load(imageUrl)
                .override(200, 200)
                .into(imageView);

        return listItem;
    }
}
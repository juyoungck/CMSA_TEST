package com.example.closetmanagementservicesapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TabSort_Cody extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<Integer> sort_cod_id = new ArrayList<>();
    private Context context;
    private TabSortCallback callback;
    private BottomSheetDialog bottomSheetDialog;

    public TabSort_Cody(Context context, BottomSheetDialog bottomSheetDialog, TabSortCallback callback) {
        this.context = context;
        this.callback = callback;
        this.bottomSheetDialog = bottomSheetDialog;
    }

    public void sortApply(View view) {
        dbHelper = MyApplication.getDbHelper();
        db = dbHelper.getWritableDatabase();

        weatherSelect(view);
        weatherButtonBase(view);

        Button tag_refresh = (Button) view.findViewById(R.id.tag_refresh);

        CheckBox Spring = (CheckBox) view.findViewById(R.id.weatherSelect_spring);
        CheckBox Summer = (CheckBox) view.findViewById(R.id.weatherSelect_summer);
        CheckBox Fall = (CheckBox) view.findViewById(R.id.weatherSelect_fall);
        CheckBox Winter = (CheckBox) view.findViewById(R.id.weatherSelect_winter);
        CheckBox Com = (CheckBox) view.findViewById(R.id.weatherSelect_communal);

        CheckBox sortSelect_name = (CheckBox) view.findViewById(R.id.sortSelect_name);
        CheckBox sortSelect_asc = (CheckBox) view.findViewById(R.id.sortSelect_asc);

        Button sort_apply = (Button) view.findViewById(R.id.sort_apply);

        sort_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<String> tagList = new HashSet<>();

                if (Com.isChecked()) { tagList.addAll(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")); }
                else {
                    if (Spring.isChecked()) { tagList.addAll(Arrays.asList("1", "5", "6", "7", "11", "12", "13", "15")); }
                    if (Summer.isChecked()) { tagList.addAll(Arrays.asList("2", "5", "8", "9", "11", "12", "14", "15")); }
                    if (Fall.isChecked()) { tagList.addAll(Arrays.asList("3", "6", "8", "10", "11", "13", "14", "15")); }
                    if (Winter.isChecked()) { tagList.addAll(Arrays.asList("4", "7", "9", "10", "12", "13", "14", "15")); }
                }
                ArrayList<String> tagArgs = new ArrayList<>(tagList);

                String orderBy = "";
                if (sortSelect_name.isChecked()) {
                    orderBy = "cod_name";
                } else if (!sortSelect_name.isChecked()){
                    orderBy = "cod_date";
                }

                if (sortSelect_asc.isChecked()) {
                    orderBy += " DESC";
                } else if (!sortSelect_asc.isChecked()) {
                    orderBy += " ASC";
                }

                if (!tagArgs.isEmpty()) {
                    StringBuilder cody_builder = new StringBuilder();
                    cody_builder.append("cod_tag IN (");
                    for (int i = 0; i < tagArgs.size(); i++) {
                        cody_builder.append("?");
                        if (i < tagArgs.size() - 1) {
                            cody_builder.append(", ");
                        }
                    }
                    cody_builder.append(")");

                    Cursor cursor = db.query("Coordy", new String[]{"cod_id"}, cody_builder.toString(), tagArgs.toArray(new String[0]), null, null, orderBy);

                    if (cursor != null && cursor.moveToFirst()) {
                        sort_cod_id.clear();

                        do {
                            int cod_id = cursor.getInt(cursor.getColumnIndexOrThrow("cod_id"));
                            sort_cod_id.add(cod_id);
                        } while (cursor.moveToNext());
                        cursor.close();
                    }
                }

                if (callback != null) {
                    callback.onSortResult(sort_cod_id, orderBy);
                }
                bottomSheetDialog.dismiss();
            }
        });

        tag_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Com.setChecked(true);
                sortSelect_name.setChecked(false);
                sortSelect_asc.setChecked(false);
            }
        });
    }

    // 계절 선택
    public void weatherSelect(View view) {
        // 정렬 버튼 (계절)
        CheckBox weatherSelect_spring = (CheckBox) view.findViewById(R.id.weatherSelect_spring);
        CheckBox weatherSelect_summer = (CheckBox) view.findViewById(R.id.weatherSelect_summer);
        CheckBox weatherSelect_fall = (CheckBox) view.findViewById(R.id.weatherSelect_fall);
        CheckBox weatherSelect_winter = (CheckBox) view.findViewById(R.id.weatherSelect_winter);
        CheckBox weatherSelect_communal = (CheckBox) view.findViewById(R.id.weatherSelect_communal);

        weatherSelect_spring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (weatherSelect_summer.isChecked() && weatherSelect_fall.isChecked()
                            && weatherSelect_winter.isChecked()) {
                        weatherSelect_communal.setChecked(true);
                    }
                } else {
                    if(weatherSelect_communal.isChecked())
                    {
                        weatherSelect_communal.setChecked(false);
                        weatherSelect_spring.setChecked(true);
                        weatherSelect_summer.setChecked(false);
                        weatherSelect_fall.setChecked(false);
                        weatherSelect_winter.setChecked(false);
                    }
                }
                weatherButtonBase(view);
            }
        });

        weatherSelect_summer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (weatherSelect_spring.isChecked() && weatherSelect_fall.isChecked()
                            && weatherSelect_winter.isChecked()) {
                        weatherSelect_communal.setChecked(true);
                    }
                } else {
                    if(weatherSelect_communal.isChecked())
                    {
                        weatherSelect_communal.setChecked(false);
                        weatherSelect_spring.setChecked(false);
                        weatherSelect_summer.setChecked(true);
                        weatherSelect_fall.setChecked(false);
                        weatherSelect_winter.setChecked(false);
                    }
                }
                weatherButtonBase(view);
            }
        });

        weatherSelect_fall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (weatherSelect_spring.isChecked() && weatherSelect_summer.isChecked()
                            && weatherSelect_winter.isChecked()) {
                        weatherSelect_communal.setChecked(true);
                    }
                } else {
                    if(weatherSelect_communal.isChecked())
                    {
                        weatherSelect_communal.setChecked(false);
                        weatherSelect_spring.setChecked(false);
                        weatherSelect_summer.setChecked(false);
                        weatherSelect_fall.setChecked(true);
                        weatherSelect_winter.setChecked(false);
                    }
                }
                weatherButtonBase(view);
            }
        });

        weatherSelect_winter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (weatherSelect_spring.isChecked() && weatherSelect_summer.isChecked()
                            && weatherSelect_fall.isChecked()) {
                        weatherSelect_communal.setChecked(true);
                    }
                } else {
                    if(weatherSelect_communal.isChecked())
                    {
                        weatherSelect_communal.setChecked(false);
                        weatherSelect_spring.setChecked(false);
                        weatherSelect_summer.setChecked(false);
                        weatherSelect_fall.setChecked(false);
                        weatherSelect_winter.setChecked(true);
                    }
                }
                weatherButtonBase(view);
            }
        });

        // 정렬 버튼 (계절 - 전체 선택)
        weatherSelect_communal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 전체 선택 버튼이 선택된 경우
                    weatherSelect_spring.setChecked(true);
                    weatherSelect_summer.setChecked(true);
                    weatherSelect_fall.setChecked(true);
                    weatherSelect_winter.setChecked(true);

                } else {
                    if (!weatherSelect_spring.isChecked() || !weatherSelect_summer.isChecked()
                            || !weatherSelect_fall.isChecked() || !weatherSelect_winter.isChecked()) {
                        weatherSelect_communal.setChecked(false);
                    } else {
                        weatherSelect_spring.setChecked(false);
                        weatherSelect_summer.setChecked(false);
                        weatherSelect_fall.setChecked(false);
                        weatherSelect_winter.setChecked(false);
                    }
                    weatherButtonBase(view);
                }
            }
        });

        // 정렬 버튼 (하단 정렬)
        CheckBox sortSelect_name = (CheckBox) view.findViewById(R.id.sortSelect_name);
        CheckBox sortSelect_asc = (CheckBox) view.findViewById(R.id.sortSelect_asc);

        sortSelect_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sortSelect_name.isChecked()) {
                    sortSelect_name.setBackgroundResource(R.drawable.left_rounded_on);
                    sortSelect_name.setText("날짜순정렬");
                } else {
                    sortSelect_name.setBackgroundResource(R.drawable.left_rounded_off);
                    sortSelect_name.setText("이름순정렬");
                }
            }
        });
        sortSelect_asc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sortSelect_asc.isChecked()) {
                    sortSelect_asc.setBackgroundResource(R.drawable.right_rounded_on);
                    sortSelect_asc.setText("↓ 내림차순");
                } else {
                    sortSelect_asc.setBackgroundResource(R.drawable.right_rounded_off);
                    sortSelect_asc.setText("↑ 오름차순");
                }
            }
        });
    }

    // 계절 버튼 클릭 시 색상 변환
    private void weatherButtonBase (View view) {
        CheckBox weatherSelect_spring = (CheckBox) view.findViewById(R.id.weatherSelect_spring);
        CheckBox weatherSelect_summer = (CheckBox) view.findViewById(R.id.weatherSelect_summer);
        CheckBox weatherSelect_fall = (CheckBox) view.findViewById(R.id.weatherSelect_fall);
        CheckBox weatherSelect_winter = (CheckBox) view.findViewById(R.id.weatherSelect_winter);
        CheckBox weatherSelect_communal = (CheckBox) view.findViewById(R.id.weatherSelect_communal);
        if (weatherSelect_spring.isChecked()) {
            weatherSelect_spring.setBackgroundResource(R.drawable.left_rounded_on);
        } else {
            weatherSelect_spring.setBackgroundResource(R.drawable.left_rounded_off);
        }
        if (weatherSelect_summer.isChecked()) {
            weatherSelect_summer.setBackgroundColor(Color.parseColor("#808080"));
        } else {
            weatherSelect_summer.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_fall.isChecked()) {
            weatherSelect_fall.setBackgroundColor(Color.parseColor("#808080"));
        } else {
            weatherSelect_fall.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_winter.isChecked()) {
            weatherSelect_winter.setBackgroundColor(Color.parseColor("#808080"));
        } else {
            weatherSelect_winter.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_communal.isChecked()) {
            weatherSelect_communal.setBackgroundResource(R.drawable.right_rounded_on);
        } else {
            weatherSelect_communal.setBackgroundResource(R.drawable.right_rounded_off);
        }
    }
}

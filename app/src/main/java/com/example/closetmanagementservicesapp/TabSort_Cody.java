package com.example.closetmanagementservicesapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TabSort_Cody extends AppCompatActivity {

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
                    sortSelect_name.setBackgroundColor(Color.parseColor("#ced4da"));
                    sortSelect_name.setText("날짜순정렬");
                } else {
                    sortSelect_name.setBackgroundColor(Color.parseColor("#e9ecef"));
                    sortSelect_name.setText("이름순정렬");
                }
            }
        });
        sortSelect_asc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sortSelect_asc.isChecked()) {
                    sortSelect_asc.setBackgroundColor(Color.parseColor("#ced4da"));
                    sortSelect_asc.setText("↓ 내림차순");
                } else {
                    sortSelect_asc.setBackgroundColor(Color.parseColor("#e9ecef"));
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
            weatherSelect_spring.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_spring.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_summer.isChecked()) {
            weatherSelect_summer.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_summer.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_fall.isChecked()) {
            weatherSelect_fall.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_fall.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_winter.isChecked()) {
            weatherSelect_winter.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_winter.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
        if (weatherSelect_communal.isChecked()) {
            weatherSelect_communal.setBackgroundColor(Color.parseColor("#a374db"));
        } else {
            weatherSelect_communal.setBackgroundColor(Color.parseColor("#e9ecef"));
        }
    }
}
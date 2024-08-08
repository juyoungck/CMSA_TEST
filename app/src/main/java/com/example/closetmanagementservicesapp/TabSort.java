package com.example.closetmanagementservicesapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TabSort extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_sort);
    }

    protected void clothesSelect(View view) {
        CheckBox clothesSelect_all = (CheckBox) view.findViewById(R.id.clothesSelect_all);
        CheckBox clothesSelect_top = (CheckBox) view.findViewById(R.id.clothesSelect_top);
        CheckBox clothesSelect_bottom = (CheckBox) view.findViewById(R.id.clothesSelect_bottom);
        CheckBox clothesSelect_outer = (CheckBox) view.findViewById(R.id.clothesSelect_outer);
        CheckBox clothesSelect_shoes = (CheckBox) view.findViewById(R.id.clothesSelect_shoes);
        CheckBox clothesSelect_onepiece = (CheckBox) view.findViewById(R.id.clothesSelect_onepiece);
        CheckBox clothesSelect_hat = (CheckBox) view.findViewById(R.id.clothesSelect_hat);
        CheckBox clothesSelect_bag = (CheckBox) view.findViewById(R.id.clothesSelect_bag);
        CheckBox clothesSelect_etc = (CheckBox) view.findViewById(R.id.clothesSelect_etc);

        clothesSelect_top.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                            && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                            && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                            && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                        clothesSelect_all.setChecked(true);
                    }
                } else {
                    if(clothesSelect_all.isChecked())
                    {
                        clothesSelect_all.setChecked(false);
                        clothesSelect_top.setChecked(true);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_onepiece.setChecked(false);
                        clothesSelect_hat.setChecked(false);
                        clothesSelect_bag.setChecked(false);
                        clothesSelect_etc.setChecked(false);
                    }
                }
            }
        });

        clothesSelect_bottom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                            && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                            && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                            && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                        clothesSelect_all.setChecked(true);
                    }
                } else {
                    if(clothesSelect_all.isChecked())
                    {
                        clothesSelect_all.setChecked(false);
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(true);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_onepiece.setChecked(false);
                        clothesSelect_hat.setChecked(false);
                        clothesSelect_bag.setChecked(false);
                        clothesSelect_etc.setChecked(false);
                    }
                }
            }
        });

        clothesSelect_outer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                            && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                            && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                            && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                        clothesSelect_all.setChecked(true);
                    }
                } else {
                    if(clothesSelect_all.isChecked())
                    {
                        clothesSelect_all.setChecked(false);
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(true);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_onepiece.setChecked(false);
                        clothesSelect_hat.setChecked(false);
                        clothesSelect_bag.setChecked(false);
                        clothesSelect_etc.setChecked(false);
                    }
                }
            }
        });

        clothesSelect_shoes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                            && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                            && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                            && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                        clothesSelect_all.setChecked(true);
                    }
                } else {
                    if(clothesSelect_all.isChecked())
                    {
                        clothesSelect_all.setChecked(false);
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(true);
                        clothesSelect_onepiece.setChecked(false);
                        clothesSelect_hat.setChecked(false);
                        clothesSelect_bag.setChecked(false);
                        clothesSelect_etc.setChecked(false);
                    }
                }
            }
        });

        clothesSelect_onepiece.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                            && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                            && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                            && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                        clothesSelect_all.setChecked(true);
                    }
                } else {
                    if(clothesSelect_all.isChecked())
                    {
                        clothesSelect_all.setChecked(false);
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_onepiece.setChecked(true);
                        clothesSelect_hat.setChecked(false);
                        clothesSelect_bag.setChecked(false);
                        clothesSelect_etc.setChecked(false);
                    }
                }
            }
        });

        clothesSelect_hat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                            && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                            && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                            && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                        clothesSelect_all.setChecked(true);
                    }
                } else {
                    if(clothesSelect_all.isChecked())
                    {
                        clothesSelect_all.setChecked(false);
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_onepiece.setChecked(false);
                        clothesSelect_hat.setChecked(true);
                        clothesSelect_bag.setChecked(false);
                        clothesSelect_etc.setChecked(false);
                    }
                }
            }
        });

        clothesSelect_bag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                            && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                            && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                            && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                        clothesSelect_all.setChecked(true);
                    }
                } else {
                    if(clothesSelect_all.isChecked())
                    {
                        clothesSelect_all.setChecked(false);
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_onepiece.setChecked(false);
                        clothesSelect_hat.setChecked(false);
                        clothesSelect_bag.setChecked(true);
                        clothesSelect_etc.setChecked(false);
                    }
                }
            }
        });

        clothesSelect_etc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                            && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                            && clothesSelect_onepiece.isChecked() && clothesSelect_hat.isChecked()
                            && clothesSelect_bag.isChecked() && clothesSelect_etc.isChecked()) {
                        clothesSelect_all.setChecked(true);
                    }
                } else {
                    if(clothesSelect_all.isChecked())
                    {
                        clothesSelect_all.setChecked(false);
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_onepiece.setChecked(false);
                        clothesSelect_hat.setChecked(false);
                        clothesSelect_bag.setChecked(false);
                        clothesSelect_etc.setChecked(true);
                    }
                }
            }
        });

        
        // 정렬 버튼(옷 종류 - 모두 선택)
        clothesSelect_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clothesSelect_top.setChecked(true);
                    clothesSelect_bottom.setChecked(true);
                    clothesSelect_outer.setChecked(true);
                    clothesSelect_shoes.setChecked(true);
                    clothesSelect_onepiece.setChecked(true);
                    clothesSelect_hat.setChecked(true);
                    clothesSelect_bag.setChecked(true);
                    clothesSelect_etc.setChecked(true);
                } else {
                    if (!clothesSelect_top.isChecked() || !clothesSelect_bottom.isChecked()
                            || !clothesSelect_outer.isChecked() || !clothesSelect_shoes.isChecked()
                            || !clothesSelect_onepiece.isChecked() || !clothesSelect_hat.isChecked()
                            || !clothesSelect_bag.isChecked() || !clothesSelect_etc.isChecked()) {
                        clothesSelect_all.setChecked(false);
                    } else {
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_onepiece.setChecked(false);
                        clothesSelect_hat.setChecked(false);
                        clothesSelect_bag.setChecked(false);
                        clothesSelect_etc.setChecked(false);
                    }
                }
            }
        });
    }

    protected void weatherSelect(View view) {
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


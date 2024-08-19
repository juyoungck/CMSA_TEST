package com.example.closetmanagementservicesapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TabSort extends AppCompatActivity {

    // 옷 선택
    protected void clothesSelect(View view) {
        CheckBox clothesSelect_all = (CheckBox) view.findViewById(R.id.clothesSelect_all);
        CheckBox clothesSelect_all_clothes = (CheckBox) view.findViewById(R.id.clothesSelect_all_clothes);
        CheckBox clothesSelect_all_access = (CheckBox) view.findViewById(R.id.clothesSelect_all_access);
        CheckBox clothesSelect_all_etc = (CheckBox) view.findViewById(R.id.clothesSelect_all_etc);
        CheckBox clothesSelect_top = (CheckBox) view.findViewById(R.id.clothesSelect_top);
        CheckBox clothesSelect_bottom = (CheckBox) view.findViewById(R.id.clothesSelect_bottom);
        CheckBox clothesSelect_outer = (CheckBox) view.findViewById(R.id.clothesSelect_outer);
        CheckBox clothesSelect_shoes = (CheckBox) view.findViewById(R.id.clothesSelect_shoes);
        CheckBox clothesSelect_under = (CheckBox) view.findViewById(R.id.clothesSelect_under);
        CheckBox clothesSelect_socks = (CheckBox) view.findViewById(R.id.clothesSelect_socks);
        CheckBox clothesSelect_hat = (CheckBox) view.findViewById(R.id.clothesSelect_hat);
        CheckBox clothesSelect_access = (CheckBox) view.findViewById(R.id.clothesSelect_access);
        CheckBox clothesSelect_bag = (CheckBox) view.findViewById(R.id.clothesSelect_bag);
        CheckBox clothesSelect_set = (CheckBox) view.findViewById(R.id.clothesSelect_set);
        CheckBox clothesSelect_etc = (CheckBox) view.findViewById(R.id.clothesSelect_etc);
        CheckBox clothesSelect_input = (CheckBox) view.findViewById(R.id.clothesSelect_input);

        clothesSelect_top.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                            && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                            && clothesSelect_under.isChecked() && clothesSelect_socks.isChecked()) {
                        clothesSelect_all_clothes.setChecked(true);
                    }
                } else {
                    if (clothesSelect_all_clothes.isChecked())
                    {
                        clothesSelect_all_clothes.setChecked(false);
                        clothesSelect_top.setChecked(true);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_under.setChecked(false);
                        clothesSelect_socks.setChecked(false);
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
                            && clothesSelect_under.isChecked() && clothesSelect_socks.isChecked()) {
                        clothesSelect_all_clothes.setChecked(true);
                    }
                } else {
                    if (clothesSelect_all_clothes.isChecked()) {
                        clothesSelect_all_clothes.setChecked(false);
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(true);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_under.setChecked(false);
                        clothesSelect_socks.setChecked(false);
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
                            && clothesSelect_under.isChecked() && clothesSelect_socks.isChecked()) {
                        clothesSelect_all_clothes.setChecked(true);
                    }
                } else {
                    if (clothesSelect_all_clothes.isChecked()) {
                        clothesSelect_all_clothes.setChecked(false);
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(true);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_under.setChecked(false);
                        clothesSelect_socks.setChecked(false);
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
                            && clothesSelect_under.isChecked() && clothesSelect_socks.isChecked()) {
                        clothesSelect_all_clothes.setChecked(true);
                    }
                } else {
                    if (clothesSelect_all_clothes.isChecked()) {
                        clothesSelect_all_clothes.setChecked(false);
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(true);
                        clothesSelect_under.setChecked(false);
                        clothesSelect_socks.setChecked(false);
                    }
                }
            }

        });

        clothesSelect_under.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                            && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                            && clothesSelect_under.isChecked() && clothesSelect_socks.isChecked()) {
                        clothesSelect_all_clothes.setChecked(true);
                    }
                } else {
                    if (clothesSelect_all_clothes.isChecked()) {
                        clothesSelect_all_clothes.setChecked(false);
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_under.setChecked(true);
                        clothesSelect_socks.setChecked(false);
                    }
                }
            }

        });

        clothesSelect_socks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_top.isChecked() && clothesSelect_bottom.isChecked()
                            && clothesSelect_outer.isChecked() && clothesSelect_shoes.isChecked()
                            && clothesSelect_under.isChecked() && clothesSelect_socks.isChecked()) {
                        clothesSelect_all_clothes.setChecked(true);
                    }
                } else {
                    if (clothesSelect_all_clothes.isChecked()) {
                        clothesSelect_all_clothes.setChecked(false);
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_under.setChecked(false);
                        clothesSelect_socks.setChecked(true);
                    }
                }
            }
        });

        clothesSelect_hat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_hat.isChecked() && clothesSelect_access.isChecked()
                            && clothesSelect_bag.isChecked()) {
                        clothesSelect_all_access.setChecked(true);
                    }
                } else {
                    if (clothesSelect_all_access.isChecked()) {
                        clothesSelect_all_access.setChecked(false);
                        clothesSelect_hat.setChecked(true);
                        clothesSelect_access.setChecked(false);
                        clothesSelect_bag.setChecked(false);
                    }
                }
            }
        });

        clothesSelect_access.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_hat.isChecked() && clothesSelect_access.isChecked()
                            && clothesSelect_bag.isChecked()) {
                        clothesSelect_all_access.setChecked(true);
                    }
                } else {
                    if (clothesSelect_all_access.isChecked()) {
                        clothesSelect_all_access.setChecked(false);
                        clothesSelect_hat.setChecked(false);
                        clothesSelect_access.setChecked(true);
                        clothesSelect_bag.setChecked(false);
                    }
                }
            }

        });

        clothesSelect_bag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_hat.isChecked() && clothesSelect_access.isChecked()
                            && clothesSelect_bag.isChecked()) {
                        clothesSelect_all_access.setChecked(true);
                    }
                } else {
                    if (clothesSelect_all_access.isChecked()) {
                        clothesSelect_all_access.setChecked(false);
                        clothesSelect_hat.setChecked(false);
                        clothesSelect_access.setChecked(false);
                        clothesSelect_bag.setChecked(true);
                    }
                }
            }
        });

        clothesSelect_set.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_set.isChecked() && clothesSelect_etc.isChecked()
                            && clothesSelect_input.isChecked()) {
                        clothesSelect_all_etc.setChecked(true);
                    }
                } else {
                    if (clothesSelect_all_etc.isChecked()) {
                        clothesSelect_all_etc.setChecked(false);
                        clothesSelect_set.setChecked(true);
                        clothesSelect_etc.setChecked(false);
                        clothesSelect_input.setChecked(false);
                    }
                }
            }
        });

        clothesSelect_etc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_set.isChecked() && clothesSelect_etc.isChecked()
                            && clothesSelect_input.isChecked()) {
                        clothesSelect_all_etc.setChecked(true);
                    }
                } else {
                    if (clothesSelect_all_etc.isChecked()) {
                        clothesSelect_all_etc.setChecked(false);
                        clothesSelect_set.setChecked(false);
                        clothesSelect_etc.setChecked(true);
                        clothesSelect_input.setChecked(false);
                    }
                }
            }
        });

        clothesSelect_input.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (clothesSelect_set.isChecked() && clothesSelect_etc.isChecked()
                            && clothesSelect_input.isChecked()) {
                        clothesSelect_all_etc.setChecked(true);
                    }
                } else {
                    if (clothesSelect_all_etc.isChecked()) {
                        clothesSelect_all_etc.setChecked(false);
                        clothesSelect_set.setChecked(false);
                        clothesSelect_etc.setChecked(false);
                        clothesSelect_input.setChecked(true);
                    }
                }
            }
        });

        clothesSelect_all_clothes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clothesSelect_top.setChecked(true);
                    clothesSelect_bottom.setChecked(true);
                    clothesSelect_outer.setChecked(true);
                    clothesSelect_shoes.setChecked(true);
                    clothesSelect_under.setChecked(true);
                    clothesSelect_socks.setChecked(true);
                    if (clothesSelect_all_clothes.isChecked() && clothesSelect_all_access.isChecked()
                            && clothesSelect_all_etc.isChecked()) {
                        clothesSelect_all.setChecked(true);
                    }
                } else {
                    if (!clothesSelect_top.isChecked() || !clothesSelect_bottom.isChecked()
                            || !clothesSelect_outer.isChecked() || !clothesSelect_shoes.isChecked()
                            || !clothesSelect_under.isChecked() || !clothesSelect_socks.isChecked()) {
                        clothesSelect_all_clothes.setChecked(false);
                    } else {
                        clothesSelect_top.setChecked(false);
                        clothesSelect_bottom.setChecked(false);
                        clothesSelect_outer.setChecked(false);
                        clothesSelect_shoes.setChecked(false);
                        clothesSelect_under.setChecked(false);
                        clothesSelect_socks.setChecked(false);
                        if(clothesSelect_all.isChecked())
                        {
                            clothesSelect_all.setChecked(false);
                        }
                    }
                }
            }
        });

        clothesSelect_all_access.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clothesSelect_hat.setChecked(true);
                    clothesSelect_access.setChecked(true);
                    clothesSelect_bag.setChecked(true);
                    if (clothesSelect_all_clothes.isChecked() && clothesSelect_all_access.isChecked()
                            && clothesSelect_all_etc.isChecked()) {
                        clothesSelect_all.setChecked(true);
                    }
                } else {
                    if (!clothesSelect_hat.isChecked() || !clothesSelect_access.isChecked()
                            || !clothesSelect_bag.isChecked()) {
                        clothesSelect_all_access.setChecked(false);
                    } else {
                        clothesSelect_hat.setChecked(false);
                        clothesSelect_access.setChecked(false);
                        clothesSelect_bag.setChecked(false);
                        if(clothesSelect_all.isChecked())
                        {
                            clothesSelect_all.setChecked(false);
                        }
                    }
                }
            }
        });

        clothesSelect_all_etc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clothesSelect_set.setChecked(true);
                    clothesSelect_etc.setChecked(true);
                    clothesSelect_input.setChecked(true);
                    if (clothesSelect_all_clothes.isChecked() && clothesSelect_all_access.isChecked()
                            && clothesSelect_all_etc.isChecked()) {
                        clothesSelect_all.setChecked(true);
                    }
                } else {
                    if ( !clothesSelect_set.isChecked() || !clothesSelect_etc.isChecked()
                            || !clothesSelect_input.isChecked()) {
                        clothesSelect_all_etc.setChecked(false);
                    } else {
                        clothesSelect_set.setChecked(false);
                        clothesSelect_etc.setChecked(false);
                        clothesSelect_input.setChecked(false);
                        if(clothesSelect_all.isChecked())
                        {
                            clothesSelect_all.setChecked(false);
                        }
                    }
                }
            }
        });

        // 정렬 버튼(옷 종류 - 모두 선택)
        clothesSelect_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clothesSelect_all.setChecked(true);
                    clothesSelect_all_clothes.setChecked(true);
                    clothesSelect_all_access.setChecked(true);
                    clothesSelect_all_etc.setChecked(true);
                } else {
                    if (!clothesSelect_all_clothes.isChecked() || !clothesSelect_all_access.isChecked()
                            || !clothesSelect_all_etc.isChecked()) {
                        clothesSelect_all.setChecked(false);
                    } else {
                        clothesSelect_all_clothes.setChecked(false);
                        clothesSelect_all_access.setChecked(false);
                        clothesSelect_all_etc.setChecked(false);
                    }
                }
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

package com.example.closetmanagementservicesapp;

import java.util.ArrayList;

public interface WeatherDataCallback {
    void onWeatherDataResult(String sky, String sky_state, float temp); // 날씨 결과 결과를 반환하는 메서드
}

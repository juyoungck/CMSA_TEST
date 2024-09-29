package com.example.closetmanagementservicesapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherData {

    private TextView weatherTextView;
    private ImageView imageViewIcon;
    private WeatherDataCallback callback;

    // 생성자에서 TextView를 전달받도록 수정
    public WeatherData(TextView weatherTextView, ImageView imageViewIcon, WeatherDataCallback callback) {
        this.weatherTextView = weatherTextView;
        this.imageViewIcon = imageViewIcon;
        this.callback = callback;
    }

    // 비동기 작업 클래스
    private class FetchWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String date = params[0];
            String time = params[1];
            String nx = params[2];
            String ny = params[3];

            try {
                return lookUpWeather(date, time, nx, ny);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String weather) {
            if (weather != null) {
                // UI 업데이트 작업은 여기서 처리
                Log.d("WeatherData", "Weather: " + weather);
                String[] weatherarray = weather.split(" ");
                for(int i = 0; i < weatherarray.length; i++) {
                    Log.d("weather = ", i + " " + weatherarray[i]);
                }
                StringBuilder weatherInfo = new StringBuilder();

                // 각 요소를 TextView에 표시
                for (int i = 0; i < weatherarray.length; i++) {
                    weatherInfo.append("Item ").append(i).append(": ").append(weatherarray[i]).append("\n");
                }
                // weatherarray[0]의 값에 따라 이미지 변경
                Log.d("asd",weatherarray[0]);
                switch (weatherarray[0]) {
                    case "맑음":
                        imageViewIcon.setImageResource(R.drawable.sun);
                        break;
                    case "구름많음":
                        imageViewIcon.setImageResource(R.drawable.cloud);
                        break;
                    case "흐림":
                        imageViewIcon.setImageResource(R.drawable.clouds);
                        break;
                    default:
                        break;
                }

                switch (weatherarray[2]) {
                    case "없음":
                        break;
                    case "비":
                        imageViewIcon.setImageResource(R.drawable.rain);
                        break;
                    case "비/눈":
                        imageViewIcon.setImageResource(R.drawable.snow);
                        break;
                    case "눈":
                        imageViewIcon.setImageResource(R.drawable.snow);
                        break;
                    case "소나기":
                        imageViewIcon.setImageResource(R.drawable.rain);
                        break;
                    default:
                        break;
                }

                // xml 설정
                String temp = weatherarray[3] + "°";
                weatherTextView.setText(temp);
                if (callback != null) {
                    callback.onWeatherDataResult(weatherarray[0], weatherarray[2], Float.parseFloat(weatherarray[3]));
                }
            } else {
                Log.e("WeatherData", "날씨 정보 오류");
            }
        }
    }

    public void fetchWeather(String date, String time, String nx, String ny) {
        new FetchWeatherTask().execute(date, time, nx, ny);
    }

    private String sky, sky_state, temperature, wind, rain, snow, humidity;

    public String lookUpWeather(String date, String time, String nx, String ny) throws IOException, JSONException {
        String baseDate = date;
        String baseTime = timeChange(time);
        String apiUrl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
        String serviceKey = "V2S%2FS3EkiyV9qtXme8oDskBE0kRe2jt5QWf2Fdd6DTRlKSvdOTOrZbRwKsCjVA9vs25BI95PoDMaaacZRGThvQ%3D%3D";

        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        Log.d("API URL", "URL: " + url);

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();
        String result = sb.toString();
        Log.d("API Result", result);

        JSONObject jsonObj_1 = new JSONObject(result);
        String response = jsonObj_1.getString("response");
        Log.d("response", response);

        JSONObject jsonObj_2 = new JSONObject(response);
        String body = jsonObj_2.getString("body");
        Log.d("body", body);

        JSONObject jsonObj_3 = new JSONObject(body);
        String items = jsonObj_3.getString("items");
        Log.d("items", items);

        JSONObject jsonObj_4 = new JSONObject(items);
        JSONArray jsonArray = jsonObj_4.getJSONArray("item");

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObj_4 = jsonArray.getJSONObject(i);
            String fcstValue = jsonObj_4.getString("fcstValue");
            String category = jsonObj_4.getString("category");
            Log.d("category", category + ": " + fcstValue);

            if (category.equals("SKY")) {
                sky = fcstValue.equals("1") ? "맑음 " : fcstValue.equals("3") ? "구름많음 " : fcstValue.equals("4") ? "흐림 " : "해당없음 ";
            } else if (category.equals("PTY")) {
                sky_state = fcstValue.equals("0") ? "없음 " : fcstValue.equals("1") ? "비 " : fcstValue.equals("2") ? "비/눈 " : fcstValue.equals("3") ? "눈 " : "소나기 ";
            } else if (category.equals("TMP")) {
                temperature = fcstValue + " ";
            } else if (category.equals("WSD")) {
                wind = fcstValue + "m/s ";
            } else if (category.equals("POP")) {
                rain = fcstValue + "% ";
            } else if (category.equals("SNO")) {
                snow = fcstValue + " ";
            } else if (category.equals("REH")) {
                humidity = fcstValue + "% ";
            }
        }
        Log.d("Sky",sky + rain + sky_state + temperature + wind + snow + humidity);
        return sky + rain + sky_state + temperature + wind + snow + humidity;
    }

    public String timeChange(String time) {
        switch(time) {
            case "0200":
            case "0300":
            case "0400":
                return "0200";
            case "0500":
            case "0600":
            case "0700":
                return "0500";
            case "0800":
            case "0900":
            case "1000":
                return "0800";
            case "1100":
            case "1200":
            case "1300":
                return "1100";
            case "1400":
            case "1500":
            case "1600":
                return "1400";
            case "1700":
            case "1800":
            case "1900":
                return "1700";
            case "2000":
            case "2100":
            case "2200":
                return "2000";
            case "2300":
            case "0000":
            case "0100":
                return "2300";
            default:
                return time;
        }
    }
}
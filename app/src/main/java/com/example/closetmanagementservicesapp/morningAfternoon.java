package com.example.closetmanagementservicesapp;

import android.util.Log;

public class morningAfternoon {
    String timeNow="";
    public morningAfternoon(String timeNow){
        this.timeNow = timeNow;
    }
    public String asd() {
        String[] result = timeNow.split(":");
        String str = result[0] + result[1];
        int num = 0;
        String ma;
        try {
            num = Integer.parseInt(result[0]);
        } catch (NumberFormatException ex) {
            Log.e("오류", "오류");
        }
        if (num >= 12)
        {
            ma ="오후";
            num-=12;
            result[0] = Integer.toString(num);
        }
        else {
            ma= "오전";
        }

            return ma+" "+ result[0] + ":" + result[1];
    }

}

package com.example.closetmanagementservicesapp;

import java.util.ArrayList;

public interface TabSortCallback {
    void onSortResult(ArrayList<Integer> sortedData, String orderBy); // 정렬 결과를 반환하는 메서드
}

package com.amand.Utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class YearUtils {
    public List<String> getYears() {
        List<String> years = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        years.add(Integer.toString(year));
        for (int i = 1; i < 4; i++) {
            years.add(String.valueOf(year - i));
        }
        return years;
    }
}

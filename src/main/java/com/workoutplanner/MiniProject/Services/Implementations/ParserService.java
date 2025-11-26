package com.workoutplanner.MiniProject.Services.Implementations;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ParserService {
    public Double extractDoubleFirstMatch(String text, String... regexes) {
        if (text == null) return null;
        // Duyệt từng regex → giúp fallback khi regex cũ fail.
        for (String r : regexes) {
            // Tạo matcher → nếu tim thấy.
            Matcher m = Pattern.compile(r).matcher(text);
            if (m.find()) {
                // Lấy group 1 → loại bỏ ký tự không phải số.
                // Parse thành số Double.
                String g = m.group(1).replaceAll("[^0-9.\\-]", "");
                try {
                    return Double.parseDouble(g);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return null;
    }

    // Convert Double → int.
    public Integer extractIntFirstMatch(String text, String... regexes) {
        Double d = extractDoubleFirstMatch(text, regexes);
        return d == null ? null : d.intValue();
    }
}

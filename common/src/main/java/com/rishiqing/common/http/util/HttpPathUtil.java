package com.rishiqing.common.http.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wallace Mao
 * Date: 2018-12-22 16:53
 */
public class HttpPathUtil {
    private static Pattern PATTERN_PATH_VARIABLE = Pattern.compile("\\{\\w+}");

    public static String replacePathVariable(String originUrl, Map json) {
        Matcher matcher = PATTERN_PATH_VARIABLE.matcher(originUrl);
        String result = originUrl;
        while (matcher.find()) {
            String groupString = matcher.group();
            String key = groupString.substring(1, groupString.length() - 1);
            result = result.replace(matcher.group(), String.valueOf(json.get(key)));
        }
        return result;
    }
}

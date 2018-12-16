package com.rishiqing.dingtalk.biz.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wallace Mao
 * Date: 2018-12-09 19:28
 */
public class RegExpUtil {
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

    /**
     * arrayString格式为"[123,234,678678]"，当且尽当element为"123","234",678678"时，才返回true
     * @param arrayString
     * @param element
     * @return
     */
    public static Boolean ArrayStringContainsElement(String arrayString, String element) {
        if (arrayString == null) {
            return false;
        }
        String str = arrayString.replace("[", ",").replace("]", ",");
        return str.contains("," + element + ",");
    }

    public static void main(String[] args) {
        // System.out.println("-------------------");
        // Map<String, Object> json = new HashMap<>();
        // json.put("abc", "123");
        // json.put("xyz", "456");
        // String result = replacePathVariable("aaa.com/{abc}/ddd/{xyz}", json);
        // System.out.println(result);
        System.out.println(ArrayStringContainsElement("[123,234,678678]", "12"));
        System.out.println(ArrayStringContainsElement("[123,234,678678]", "123"));
    }
}

package com.rishiqing.common.base;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Wallace Mao
 * Date: 2018-12-22 17:51
 */
public class StringUtil {

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
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

    /**
     * 从spring的org.springframework.util中拷贝过来的方法
     * @param coll
     * @param delim
     * @return
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    private static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix, String suffix) {
        if (isCollectionEmpty(coll)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            Iterator it = coll.iterator();

            while(it.hasNext()) {
                sb.append(prefix).append(it.next()).append(suffix);
                if (it.hasNext()) {
                    sb.append(delim);
                }
            }

            return sb.toString();
        }
    }

    private static boolean isCollectionEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}

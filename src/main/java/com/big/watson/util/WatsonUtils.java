package com.big.watson.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by patrick on 17.07.15.
 */
public class WatsonUtils {

    public static <T> List<T> setToList(Set<T> set) {
        List<T> resultList = new ArrayList<>();
        set.forEach(element -> resultList.add(element));
        return resultList;
    }
}

package com.csqlv.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Damian on 2016-07-20.
 */
public class ObjectCreator {

    public static Stream<Map<String, String>> createTestStream() {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        list.add(new HashMap<String, String>() {{
            put("id", "1");
            put("a", "1");
            put("b", "1");
            put("c", "1");
            put("d", "aaa");
        }});
        list.add(new HashMap<String, String>() {{
            put("id", "2");
            put("a", "2");
            put("b", "2");
            put("c", "2");
            put("d", "bbb");
        }});
        list.add(new HashMap<String, String>() {{
            put("id", "3");
            put("a", "3");
            put("b", "3");
            put("c", "3");
            put("d", "abc");
        }});
        return list.stream();
    }

}

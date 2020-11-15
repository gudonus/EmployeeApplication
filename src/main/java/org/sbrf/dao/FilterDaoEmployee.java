package org.sbrf.dao;

import java.util.HashMap;
import java.util.Map;

public class FilterDaoEmployee implements FilterDao<String> {

    private HashMap<String, String> values;

    public FilterDaoEmployee(String key, String value) {
        values = new HashMap<String, String>();
        add(key, value);
    }

    @Override
    public String getValue(String key) {
        return values.get(key);
    }

    @Override
    public String getKeyCondition(String key) {
        String value = values.get(key);
        if (value != null) {
            StringBuffer buffer = new StringBuffer(key + " = ");
            buffer.append(value);
            return buffer.toString();
        }
        return null;
    }

    @Override
    public String getValidCondition() {
        /*values.forEach((k, v)-> {
            System.out.println("\tKey=" + k + " value=" + v);
        });*/
        for (Map.Entry<String, String> value : values.entrySet()) {
            String condition = getKeyCondition(value.getKey());
            if (condition != null)
                return condition;
        }

        return null;
    }

    public void add(String key, String value) {
        values.put(key, value);
    }
}

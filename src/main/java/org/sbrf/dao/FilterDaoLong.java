package org.sbrf.dao;

import java.util.HashMap;

public class FilterDaoLong implements FilterDao<Long> {

    private HashMap<String, String> values;

    public FilterDaoLong(Long id) {
        values = new HashMap<String, String>();
        values.put("1", id.toString());
    }

    @Override
    public String get() {
//        StringBuffer buffer = new StringBuffer("ID = ");
//        buffer.append(object);
//
//        return buffer.toString();
        return null;
    }

    @Override
    public Long getId() {
        return Long.parseLong(values.get("1"));
    }

    public void add(Long filter) {

    }
}

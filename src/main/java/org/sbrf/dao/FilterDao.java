package org.sbrf.dao;

public interface FilterDao<T> {
    T getValue(String key);

    String getKeyCondition(String key);

    String getValidCondition();
}

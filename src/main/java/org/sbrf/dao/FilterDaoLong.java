package org.sbrf.dao;

public class FilterDaoLong implements FilterDao<Long> {

    private Long id;

    public FilterDaoLong(Long id) {
        this.id = id;
    }

    @Override
    public String get(Long object) {
        StringBuffer buffer = new StringBuffer("ID = ");
        buffer.append(object);

        return buffer.toString();
    }

    @Override
    public Long getId() {
        return id;
    }
}

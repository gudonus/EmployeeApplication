package org.sbrf.dao;

import org.sbrf.enums.StoreTypes;

public final class DaoTypeController {

    private static DaoTypeController instance;

    private final StoreTypes dataStoreType;

    private DaoTypeController(StoreTypes dataStoreType) {
        this.dataStoreType = dataStoreType;
    }

    public static DaoTypeController applyType(StoreTypes dataStoreType) {
        if (instance == null)
            instance = new DaoTypeController(dataStoreType);

        return instance;
    }

    public StoreTypes getType() {
        return dataStoreType;
    }
}

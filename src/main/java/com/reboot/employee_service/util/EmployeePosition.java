package com.reboot.employee_service.util;

import java.util.HashMap;
import java.util.Map;

public enum EmployeePosition {

    CEO("CEO"),
    CMO("CMO"),
    VP("VP"),
    SENIOR_MANAGER("SENIOR MANAGER"),
    MANAGER("MANAGER"),
    ASSOCIATE("ASSOCIATE"),
    INTERN("INTERN");

    private static final Map<String, EmployeePosition> BY_LABEL = new HashMap<>();

    static {
        for (EmployeePosition ep : EmployeePosition.values()) {
            BY_LABEL.put(ep.name(), ep);
        }
    }

    public final String label;

    EmployeePosition(String label){
        this.label = label;
    }

    public static EmployeePosition getByLabel(String label) {
        return BY_LABEL.get(label);
    }

}

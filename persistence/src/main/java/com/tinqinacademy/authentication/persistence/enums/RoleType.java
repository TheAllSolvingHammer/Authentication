package com.tinqinacademy.authentication.persistence.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum RoleType {
    USER("user"),
    ADMIN("admin"),
    UNKNOWN(null);
    private final String val;

    private static final Map<String, RoleType> map = new HashMap<>();

    static {
        Arrays.stream(RoleType.values())
                .filter(b -> b != UNKNOWN)
                .forEach(b -> map.put(b.val, b));
    }

    RoleType(String name) {
        this.val = name;
    }

    @JsonValue
    public String toString() {
        return val;
    }

    @JsonCreator
    public static RoleType getByCode(String code) {
        if (map.containsKey(code)) {
            return map.get(code);
        }
        return RoleType.UNKNOWN;
    }
}

package com.carvalho.solution.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KeyValue {

    private String key;
    private String value;

    public static KeyValue of(String key, String value) {
        return new KeyValue(key, value);
    }   
}

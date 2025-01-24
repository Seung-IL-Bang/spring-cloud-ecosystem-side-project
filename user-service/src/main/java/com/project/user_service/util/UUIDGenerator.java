package com.project.user_service.util;

import java.util.UUID;

public abstract class UUIDGenerator {

    private UUIDGenerator() {}

    public static String get() {
        return UUID.randomUUID().toString();
    }
}
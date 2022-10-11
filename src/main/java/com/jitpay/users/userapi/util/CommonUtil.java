package com.jitpay.users.userapi.util;

import java.util.UUID;

public class CommonUtil {

    public static String uniqueIdGenerator() {
        return UUID.randomUUID().toString();
    }
}

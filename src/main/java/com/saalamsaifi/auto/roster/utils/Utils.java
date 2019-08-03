package com.saalamsaifi.auto.roster.utils;

import org.bson.types.ObjectId;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {
    public static String getObjectId() {
        return ObjectId.get().toHexString();
    }
}

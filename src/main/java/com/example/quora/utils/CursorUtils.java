package com.example.quora.utils;

import java.time.Instant;
import java.time.LocalDateTime;

public class CursorUtils {

    public static boolean isValidCursor(String cursor){
        if(cursor == null || cursor.isEmpty()){
            return false;
        }
        try{
            Instant.parse(normalize(cursor));
            return true;
        }catch (Exception e){
            return false;
        }

    }


    public static Instant parseCursor(String cursor){
        if(!isValidCursor(cursor)){
            throw new IllegalArgumentException(": Invalid Cursor");
        }
        return Instant.parse(normalize(cursor));
    }

    private static String normalize(String cursor) {
        return cursor.trim()
                .replace(" ", "+");   // restore lost '+'
    }
}

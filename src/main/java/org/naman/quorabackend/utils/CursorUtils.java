package org.naman.quorabackend.utils;

import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public class CursorUtils {
    public static Boolean isValidCursor( String cursor) {
        if(cursor==null || cursor.isEmpty())
            return false;
        try{
            LocalDateTime.parse( cursor);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

     public static LocalDateTime ParseCursor( String cursor) {
        if(!isValidCursor(cursor)) {
            throw new IllegalArgumentException("Cursor is not valid");
        }
        return LocalDateTime.parse(cursor);
     }



}

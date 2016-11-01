package com.autodesk.shejijia.shared.components.common.utility;

/**
 * Created by t_panya on 16/11/1.
 */

public class CastUtils {
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object){
        T dest;
         try{
             dest = (T)object;
         }catch (ClassCastException ex){
             return null;
         }
        return dest;
    }
}

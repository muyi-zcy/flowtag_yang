package com.yang.flowtag;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static final String FILE_NAME="share_data";

    public static void setParem(Context context,String key,Object object){
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if ("String".equals(type)){
            editor.putString(key,(String)object);
        }else if ("Boolean".equals(type)){
            editor.putBoolean(key,(Boolean)object);
        }else if ("Integer".equals(type)){
            editor.putInt(key,(Integer)object);
        }else if ("Float".equals(type)){
            editor.putFloat(key,(Float)object);
        }else if ("Long".equals(type)){
            editor.putLong(key,(Long)object);
        }
        editor.commit();
    }

    public static Object getParem(Context context, String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        if ("String".equals(type)){
            return sp.getString(key,(String)defaultObject);
        }else if ("Boolean".equals(type)){
            return sp.getBoolean(key,(Boolean)defaultObject);
        }else if ("Integer".equals(type)){
            return  sp.getInt(key,(Integer)defaultObject);
        }else if ("Float".equals(type)){
            return sp.getFloat(key,(Float)defaultObject);
        }else if ("Long".equals(type)){
            return sp.getLong(key,(Long)defaultObject);
        }
        return null;
    }

    public static void main(String[] args) {
        Object i=0;
        String string=Integer.class.getClass().getSimpleName();
        System.out.println(string);
    }
}

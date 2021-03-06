package com.book.moneywhitebar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.book.moneywhitebar.App;


/**
 * Created by tantan on 2018/4/16.
 */

public class SPUtil {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "xinyuncai";
    private static SharedPreferences sp;

    private static SharedPreferences getSp() {
        if (sp == null) {
            sp = App.getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp;
    }


    /**
     * 存入字符串
     *
     * @param key     字符串的键
     * @param value   字符串的值
     */
    public static void putString(String key, String value) {
        SharedPreferences preferences = getSp();
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 获取字符串
     *
     * @param key     字符串的键
     * @return 得到的字符串
     */
    public static String getString(String key) {
        SharedPreferences preferences = getSp();
        return preferences.getString(key, "");
    }

    /**
     * 获取字符串
     *
     * @param key     字符串的键
     * @param value   字符串的默认值
     * @return 得到的字符串
     */
    public static String getString(String key, String value) {
        SharedPreferences preferences = getSp();
        return preferences.getString(key, value);
    }

    /**
     * 保存布尔值
     *
     * @param key     键
     * @param value   值
     */
    public static void putBoolean(String key, boolean value) {
        SharedPreferences sp     = getSp();
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 获取布尔值
     *
     * @param key      键
     * @param defValue 默认值
     * @return 返回保存的值
     */
    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = getSp();
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存long值
     *
     * @param key     键
     * @param value   值
     */
    public static void putLong(String key, long value) {
        SharedPreferences sp     = getSp();
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 获取long值
     *
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    public static long getLong(String key, long defValue) {
        SharedPreferences sp = getSp();
        return sp.getLong(key, defValue);
    }

    /**
     * 保存int值
     *
     * @param key     键
     * @param value   值
     */
    public static void putInt(String key, int value) {
        SharedPreferences sp     = getSp();
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 获取long值
     *
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    public static int getInt(String key, int defValue) {
        SharedPreferences sp = getSp();
        return sp.getInt(key, defValue);
    }

    /**
     * 保存对象
     *
     * @param key     键
     * @param obj     要保存的对象（Serializable的子类）
     * @param <T>     泛型定义
     */
    public static <T extends Serializable> void putObject(Context context, String key, T obj) {
        try {
            put( key, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象
     *
     * @param context 上下文
     * @param key     键
     * @param <T>     指定泛型
     * @return 泛型对象
     */
    public static <T extends Serializable> T getObject(Context context, String key) {
        try {
            return (T) get( key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储List集合
     * @param context 上下文
     * @param key 存储的键
     * @param list 存储的集合
     */
    public static void putList(Context context, String key, List<? extends Serializable> list) {
        try {
            put( key, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取List集合
     * @param context 上下文
     * @param key 键
     * @param <E> 指定泛型
     * @return List集合
     */
    public static <E extends Serializable> List<E> getList(Context context, String key) {
        try {
            return (List<E>) get( key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储Map集合
     * @param context 上下文
     * @param key 键
     * @param map 存储的集合
     * @param <K> 指定Map的键
     * @param <V> 指定Map的值
     */
    public static <K extends Serializable, V extends Serializable> void putMap(Context context,
                                                                               String key, Map<K, V> map)
    {
        try {
            put(key, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <K extends Serializable, V extends Serializable> Map<K, V> getMap(Context context,
                                                                                    String key)
    {
        try {
            return (Map<K, V>) get( key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**存储对象*/
    private static void put(String key, Object obj)
            throws IOException
    {
        if (obj == null) {//判断对象是否为空
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos  = null;
        oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        baos.close();
        oos.close();

        putString( key, objectStr);
    }

    /**获取对象*/
    private static Object get(String key)
            throws IOException, ClassNotFoundException
    {
        String wordBase64 = getString( key);
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
            return null;
        }
        byte[]               objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais     = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois      = new ObjectInputStream(bais);
        // 将byte数组转换成product对象
        Object obj = ois.readObject();
        bais.close();
        ois.close();
        return obj;
    }

    /**
     * 查询某个key是否已经存在
     * @param key
     * @return
     */
    public static boolean contains( String key) {
        SharedPreferences sp = App.getApp().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp = App.getApp().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove( String key) {
        SharedPreferences sp = App.getApp().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

}

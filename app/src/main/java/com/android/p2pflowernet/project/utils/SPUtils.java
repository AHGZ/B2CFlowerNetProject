package com.android.p2pflowernet.project.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @描述: 数据存储工具类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/9 下午12:57
 * @修改人：zhangpeisen
 * @修改时间：2017/10/9 下午12:57
 * @修改备注：
 * @throws
 */
public class SPUtils {

    public static final String FILE_NAME = "share_data";
    private static final String spFileName = "welcomePage";
    public static final String FIRST_OPEN = "first_open";
    public static final String EVERYDAYOPEN = "every_day_open";
    public static final String ISFRISTEVERYDAY = "iseveryday";
    public static final String PAYCODE = "https://www.huafanwang.com/qrcode/";//店铺付款码

    public static void put(Context context, String key, Object object) {

        if (context == null) {
            return;
        }

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object == null)
            object = "";
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 保存set集合
     *
     * @param context
     * @param key
     * @param set
     */
    public static void putSet(Context context, String key, Set<String> set) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(key, set);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 获得set集合
     *
     * @param context
     * @param key
     * @param defValues
     */
    public static Set<String> getSet(Context context, String key, Set<String> defValues) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getStringSet(key, defValues);
    }

    /**
     * 移除某个key值以及对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author rxy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * @return
         * @Title: findApplyMethod
         * @Description:
         * @return: Method
         */
        @SuppressWarnings({"rawtypes", "unchecked"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

    //    public static void saveUserInfo(Context context, FormalUserInfo netUserInfo) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        // 创建字节输出流
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try {
//            // 创建对象输出流，并封装字节流
//            ObjectOutputStream oos = new ObjectOutputStream(baos);
//            // 将对象写入字节流
//            oos.writeObject(netUserInfo);
//            // 将字节流编码成base64的字符窜
//            String netUserInfo_Base64 = new String(Base64.encodeToString(baos
//                    .toByteArray(), Base64.DEFAULT));
//            editor.putString(Constants.SHARED_PERFERENCE_USERINFO, netUserInfo_Base64);
//            editor.commit();
//        } catch (Exception e) {
//        }
//        LogUtils.i("ok", "存储成功");
//    }
//
//    public static FormalUserInfo getUserInfo(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//        FormalUserInfo netUserInfo = null;
//        String productBase64 = sp.getString(Constants.SHARED_PERFERENCE_USERINFO, "");
//        //读取字节
//        byte[] base64 = Base64.decode(productBase64.getBytes(), Base64.DEFAULT);
//
//        //封装到字节流
//        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
//        try {
//            //再次封装
//            ObjectInputStream bis = new ObjectInputStream(bais);
//            try {
//                //读取对象
//                netUserInfo = (FormalUserInfo) bis.readObject();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        } catch (StreamCorruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return_ticket netUserInfo;
//    }
    public static Boolean getBoolean(Context context, String strKey,
                                     Boolean strDefault) {//strDefault	boolean: Value to return if this preference does not exist.
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, strDefault);
        return result;
    }

    public static void putBoolean(Context context, String strKey,
                                  Boolean strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.commit();
    }
}

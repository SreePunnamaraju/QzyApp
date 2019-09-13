package com.qyz.malls;

import android.content.Context;
import android.content.SharedPreferences;

import com.qyz.malls.restaurants.models.CheckoutCart;

import java.io.File;

public class Preference {

    public static final String CART_SEPARATOR = "xxxxxCartSeparatorxxxxx";
    private static final String DEF_VAL = "";
    public static String CART ="shopping_cart";
    private static String QZY_PREF = "justdialPref";
    private static Boolean DEF_BOOL = false;
    public static CheckoutCart cart;


    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(QZY_PREF, Context.MODE_PRIVATE);
    }

    public static boolean contains(Context context, String key) {
        return getPrefs(context).contains(key);
    }

    public static String getStrPref(Context context, String key) {
        return getPrefs(context).getString(key, DEF_VAL);
    }

    public static String getStrPref(Context context, String key, String defVal) {
        return getPrefs(context).getString(key, defVal);
    }

    public static int getIntPref(Context context, String key) {
        return getPrefs(context).getInt(key, 0);
    }

    public static Long getLongPref(Context context, String key, Long num) {
        return  getPrefs(context).getLong(key, 0L);
    }
    public static Boolean getBoolPref(Context context, String key) {
        return getPrefs(context).getBoolean(key, DEF_BOOL);
    }

    public static Boolean getBoolPref(Context context, String key, Boolean defVal) {
        return getPrefs(context).getBoolean(key, defVal);
    }

    public static void setStrPref(Context context, String key, String value) {

        getPrefs(context).edit().putString(key, value).apply();

    }
    public static void setLongPref(Context context, String key, Long num) {
        getPrefs(context).edit().putLong(key, num).apply();

    }
    public static void setIntPref(Context context, String key, int value) {
        getPrefs(context).edit().putInt(key, value).apply();
    }

    public static void clearLocalStorage(Context context) {
        getPrefs(context).edit().clear().apply();
    }

    public static void setBoolPref(Context context, String key, Boolean value) {
        getPrefs(context).edit().putBoolean(key, value).apply();
    }

    public static void removePref(Context context, String key) {
        getPrefs(context).edit().remove(key).apply();
    }

    public static void clearApplicationData(Context context) {
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib") && !s.equals("shared_prefs")) {
                    deleteDir(new File(appDir, s));
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else {
            return false;
        }

    }

}

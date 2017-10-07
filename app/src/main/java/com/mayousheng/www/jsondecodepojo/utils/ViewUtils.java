package com.mayousheng.www.jsondecodepojo.utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.mayousheng.www.jsondecodepojo.common.ViewDesc;

import java.lang.reflect.Field;

/**
 * Created by ma kai on 2017/10/5.
 */

public class ViewUtils {

    public static void initAllView(Class topClass, Object object, View rootView) {
        if (object == null || rootView == null) {
            return;
        }
        Class clazz = object.getClass();
        while (topClass != clazz) {
            initView(object, rootView, clazz);
            clazz = clazz.getSuperclass();
        }
    }

    private static void initView(Object object, View rootView, Class self) {
        Field[] fields = self.getDeclaredFields();
        if (fields == null) {
            return;
        }
        for (Field field : fields) {
            ViewDesc viewDesc = field.getAnnotation(ViewDesc.class);
            if (viewDesc == null) {
                continue;
            }//以上代码均为健壮性考虑
            int id = viewDesc.viewId();
            try {
                field.set(object, findViewById(rootView, id));
            } catch (Exception e) {
            }
        }
    }

    public static void initAllView(Class topClass, Activity activity) {
        if (activity == null) {
            return;
        }
        Class clazz = activity.getClass();
        while (topClass != clazz) {
            initeView(activity, clazz);
            clazz = clazz.getSuperclass();
        }
    }

    private static void initeView(Activity activity, Class self) {
        Field[] fields = self.getDeclaredFields();
        if (fields == null) {
            return;
        }
        for (Field field : fields) {
            ViewDesc viewDesc = field.getAnnotation(ViewDesc.class);
            if (viewDesc == null) {
                continue;
            }//以上代码均为健壮性考虑
            int id = viewDesc.viewId();
            try {
                field.set(activity, findViewById(activity, id));
            } catch (Exception e) {
            }
        }
    }

    public static View findViewById(View view, int id) {
        View result = null;
        if (view != null) {
            try {
                result = view.findViewById(id);
            } catch (Exception e) {
            }
        }
        return result;
    }

    public static View findViewById(Activity activity, int id) {
        View result = null;
        if (activity != null) {
            try {
                result = activity.findViewById(id);
            } catch (Exception e) {
            }
        }
        return result;
    }

}

package com.shandao.www.baseholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * holder基本抽象类，继承此类后方可利用此类的方法
 * Created by marking on 2017/4/23.
 */

public abstract class BaseHolder<T> {
    private View rootView;

    public abstract void inViewBind(T item);

    private View findViewById(View rootView, int id) {
        View result = null;
        if (rootView != null) {
            try {
                result = rootView.findViewById(id);
            } catch (Exception e) {
            }
        }
        return result;
    }

    public View getRootView() {
        return rootView;
    }

    public View newRootView(Context context, int layoutResource, ViewGroup parent) {
        rootView = LayoutInflater.from(context).inflate(layoutResource, parent, false);
        Class self = this.getClass();
        Field[] fields = self.getDeclaredFields();
        if (fields == null) {
            return rootView;
        }
        for (Field field : fields) {
            ViewDesc viewDesc = field.getAnnotation(ViewDesc.class);
            if (viewDesc == null) {
                continue;
            }//以上代码均为健壮性考虑
            int id = viewDesc.viewId();
            try {
                field.set(this, findViewById(rootView, id));
            } catch (Exception e) {
            }
        }
        return rootView;
    }
}
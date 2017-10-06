package com.mayousheng.www.jsondecodepojo.utils;

import android.widget.TextView;

/**
 * Created by ma kai on 2017/10/5.
 */

public class TextUtils {

    //判断字符是否超出TextView范围
    public static boolean isOverFlowed(TextView textView) {
        return textView == null || textView.getPaint().measureText(textView.getText().toString()) >
                (textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight());
    }

}

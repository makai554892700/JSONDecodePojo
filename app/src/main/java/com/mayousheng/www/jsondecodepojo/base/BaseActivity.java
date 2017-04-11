package com.mayousheng.www.jsondecodepojo.base;

import android.app.Activity;

/**
 * Created by marking on 2017/4/11.
 */

public class BaseActivity extends Activity {

    public  <T> T getViewById(int id) {
        if (id == 0) {
            return null;
        }
        return (T) findViewById(id);
    }

}

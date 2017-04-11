package com.mayousheng.www.jsondecodepojo.utils;

import java.util.ArrayList;

/**
 * Created by marking on 2017/3/22.
 */

public interface ArrayListBack<T> {

    public void onFail(int status, String message);

    public void onResult(ArrayList<T> data);

}

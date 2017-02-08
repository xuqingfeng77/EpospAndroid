package com.eposp.android.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xuqingfeng on 2017/2/8.
 * toast工具
 */

public class T {
    private static Toast toast = null; //Toast的对象！

    public static void showToast(Context mContext, String id) {
        if (toast == null) {
            toast = Toast.makeText(mContext, id, Toast.LENGTH_SHORT);
        }
        else {
            toast.setText(id);
        }
        toast.show();
    }
}

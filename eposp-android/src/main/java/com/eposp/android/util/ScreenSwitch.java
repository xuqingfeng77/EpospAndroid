package com.eposp.android.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.eposp.android.R;


/**
 * Activity界面跳转
 */
public class ScreenSwitch {

    public static void switchActivity(Context context, Class<?> descClass, Bundle bundle) {
        Class<?> mClass = context.getClass();
        if (mClass == descClass) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setClass(context, descClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            ((Activity) context).startActivityForResult(intent, 0);
            ((Activity) context).overridePendingTransition(R.anim.eposp_push_left_in, R.anim.eposp_push_left_out);
        } catch (Exception e) {
        }
    }


    public static void finish(Activity context) {
        context.finish();
        context.overridePendingTransition(R.anim.eposp_push_right_in, R.anim.eposp_push_right_out);
    }

}

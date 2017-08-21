package com.eposp.android.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.eposp.android.R;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 *@author : xqf
 *@date   :2017/8/19 下午5:22
 *@desc   :debug情况下发生异常跳转的界面
 *@update :
 */
public class ErrorActivity extends BaseActivity {
//    @BindView(R.id.tv_crash_info)
//    @BindView(R.id.)//不知道为什么识别不了id,感觉这个butterknife用着还是挺多问题的
    TextView  cashError;
    Button  btnOk;
    public static void show(Context context, String message) {
        if (message == null)
            return;
        Intent intent = new Intent(context, ErrorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("message", message);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
//        return R.layout.activity_error;
        return R.layout.activity_error;
    }

    @Override
    public void initView() {
       cashError=getViewById(R.id.tv_crash_info);
        btnOk=getViewById(R.id.btn_restart);
        cashError.setText(getIntent().getStringExtra("message"));
    }

    @Override
    public void eventOnClick() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

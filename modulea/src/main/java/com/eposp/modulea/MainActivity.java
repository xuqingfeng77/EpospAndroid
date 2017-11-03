package com.eposp.modulea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.eposp.android.log.LogUtils;
import com.eposp.android.ui.BaseApplication;
import com.eposp.android.util.T;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void btnClick(View v){
        LogUtils.d("dasdfasdf");
        T.showToast(MainActivity.this,"helloworld");
    }
}

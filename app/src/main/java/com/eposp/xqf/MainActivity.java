package com.eposp.xqf;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eposp.android.log.LogUtils;
import com.eposp.android.ui.BaseActivity;
import com.eposp.android.util.ABFileUtil;
import com.eposp.android.util.BimpUtil;
import com.eposp.android.util.T;

import java.io.File;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
   private String fileNam="baidu/";
    @BindView(R.id.txt_butter)
    TextView  txtButter;
    @BindView(R.id.btnone)
    Button  btnOne;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void eventOnClick() {

    }


    @OnClick(R.id.btnone)
     void btnone(View v){
        LogUtils.d("调试模式："+BuildConfig.DEBUG);
        txtButter.setText("adsdfasdf");
        btnOne.setText("asdfasdfasd");
       showToast("asdfasdfasdfasdfasdfasd");
        int i=1/0;
//        testFileBitmap();
    }
    @OnClick(R.id.btntwo)
     void OnTxtClick2(View v){
        LogUtils.d("调试模式："+BuildConfig.DEBUG);
//        testFileBitmap();
        changeImage();
    }
    @OnClick(R.id.btn_dialog)
    void onBtnDialog(){
        showWaitDialog();
    }


    private void testFileBitmap(){
        ABFileUtil.creatSDDir(fileNam);
    }
    private void changeImage(){
        File file=ABFileUtil.creatSDDir(fileNam);
        String path=file.getAbsolutePath();
       Bitmap bt= BimpUtil.compressBitmap(path+File.separator +"123.jpg",200,400);
        ABFileUtil.saveBitmap2SDWithCapacity(fileNam,"43.jpg",bt,500);
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        T.show(mContext,"网络变化了");
    }
}

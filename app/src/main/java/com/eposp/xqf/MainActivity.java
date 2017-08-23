package com.eposp.xqf;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eposp.android.log.LogUtils;
import com.eposp.android.ui.BaseToolbarActivity;
import com.eposp.android.util.ABFileUtil;
import com.eposp.android.util.BimpUtil;
import com.eposp.android.util.T;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
/**
 *@author  :xqf
 *@date    :2017/8/21 19:40
 *@desc    :使用Toolbar
 *@update  :
 */
public class MainActivity extends BaseToolbarActivity {
   private String fileNam="baidu/";
    @BindView(R.id.txt_butter)
    TextView  txtButter;
    @BindView(R.id.btnone)
    Button  btnOne;

    TextView txt;
    @BindView(R.id.btn_tabs)
    Button btnTabs;



    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
          setToolbarTitle("返回");
//        toolbar.setSubtitle("Subtitle");//设置子标题

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
    @OnClick(R.id.btn_tabs)
    void onbtnTabs(){
        goActivity(TabsActivity.class);
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

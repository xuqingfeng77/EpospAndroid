package com.eposp.xqf;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eposp.android.ui.BaseActivity;
import com.eposp.xqf.fragment.FragmentOne;
import com.eposp.xqf.fragment.FragmentTwo;
import com.eposp.xqf.fragment.dummy.DummyContent;

/**
 *@author  :xqf
 *@date    :2017/8/22 10:07
 *@desc    : tab页面测试；没有Toolbar
 *@update  :
 */
public class TabsActivity extends BaseActivity  implements FragmentTwo.OnListFragmentInteractionListener {
    private Button[] mTabs;
    private Fragment[] fragments;
    private FragmentOne mFragmentOne;
    private FragmentTwo mFragmentTwo;
    private FragmentOne mFragmentOne2;
    private FragmentTwo mFragmentTwo2;
    private int index;
    private int currentTabIndex;
//    isSetTheme=false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_tabs;
    }

    @Override
    public void initView() {
        mTabs = new Button[4];
        mTabs[0] = getViewById(R.id.tab_btn_sk);
        mTabs[1] = getViewById(R.id.tab_btn_jr);
        mTabs[2] = getViewById(R.id.tab_btn_jl);
        mTabs[3] = getViewById(R.id.tab_btn_wo);
        mFragmentOne=new FragmentOne();
        mFragmentTwo=new FragmentTwo();
        mFragmentOne2=new FragmentOne();
        mFragmentTwo2=new FragmentTwo();
        fragments = new Fragment[] { mFragmentOne, mFragmentTwo,
                mFragmentOne2, mFragmentTwo2 };

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mFragmentOne)
                .add(R.id.fragment_container, mFragmentTwo)
                .add(R.id.fragment_container, mFragmentOne2)
                .add(R.id.fragment_container, mFragmentTwo2)
                .hide(mFragmentTwo).hide(mFragmentOne2)
                .hide(mFragmentTwo2).show(mFragmentOne).commit();
//        setActionBarTitle("疯狂摩尔斯");
    }

    @Override
    public void eventOnClick() {

    }
    /**
     * button点击事件
     *
     * @param view
     */
    public void onTabSelect(View view) {
        switch (view.getId()) {
            case R.id.tab_btn_sk:
                index = 0;
                break;
            case R.id.tab_btn_jr:
                index = 1;
                break;
            case R.id.tab_btn_jl:
                index = 2;
                break;
            case R.id.tab_btn_wo:
                index = 3;
                break;
        }
        onTabSelect(index);
    }
    /**
     * button点击事件
     *
     * @param index
     */
    public void onTabSelect(int index) {
		/*
		 * switch (view.getId()) { case R.id.tab_btn_sk: index = 0; break; case
		 * R.id.tab_btn_jl: index = 1; break; case R.id.tab_btn_wo: index = 2;
		 * break; }
		 */
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}

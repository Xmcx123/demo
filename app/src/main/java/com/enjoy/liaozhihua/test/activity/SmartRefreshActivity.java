package com.enjoy.liaozhihua.test.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Toast;

import com.enjoy.liaozhihua.test.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

public class SmartRefreshActivity extends AppCompatActivity {

    private TwoLevelHeader header;
    private RefreshLayout refreshLayout;
    private WebView webview;
    private ClassicsHeader classics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_refresh);
        initView();
        webview.loadUrl("https://blog.51cto.com/14143040/2424814?source=dra");
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(true);
        header.setEnablePullToCloseTwoLevel(false);
        header.setFloorDuration(3000);
//        refreshLayout.setReboundDuration(0);//回弹动画时长（毫秒）
        classics.setEnableLastTime(false);//是否显示时间
        classics.setFinishDuration(0);//设置刷新完成显示的停留时间（设为0可以关闭停留功能）
//        classics.setDrawableSize(0);//同时设置箭头和图片的大小（dp单位）

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(getApplication(), "上拉加载", Toast.LENGTH_SHORT).show();
                refreshLayout.finishLoadMore(0);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(getApplication(), "下拉刷新", Toast.LENGTH_SHORT).show();
                refreshLayout.finishRefresh(0);
            }

            //            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
            //            @Override
            //            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState
            //            oldState, @NonNull RefreshState newState) {
            //                if (oldState == RefreshState.TwoLevel){
            //                    findViewById(R.id.second_floor_content).animate().alpha(0).setDuration(1000);
            //                }
            //            }
        });

        //        header.setOnTwoLevelListener(new OnTwoLevelListener() {
        //            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
        //            @Override
        //            public boolean onTwoLevel(@NonNull RefreshLayout refreshLayout) {
        //                Toast.makeText(getApplication(),"打开二楼",Toast.LENGTH_SHORT).show();
        //                findViewById(R.id.second_floor_content).animate().alpha(1).setDuration(2000);
        //                return true;
        //            }
        //        });
    }

    private void initView() {
        header = findViewById(R.id.header);
        refreshLayout = findViewById(R.id.refreshLayout);
        webview = findViewById(R.id.webview);
        classics = (ClassicsHeader)findViewById(R.id.classics);
    }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
//            if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
//                webview.goBack();
//                return true;
//            }
            header.finishTwoLevel();
            return true;
//            return super.onKeyDown(keyCode, event);
        }
}

package com.enjoy.liaozhihua.test;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.enjoy.liaozhihua.test.network.request.GetRequest_Interface;
import com.enjoy.liaozhihua.test.network.request.Translation;
import com.enjoy.liaozhihua.test.observer.Observer;
import com.enjoy.liaozhihua.test.proxy.DelegateObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

public class RequestActivity extends AppCompatActivity {

    private WebView webview;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
//        getSupportActionBar().hide();//这种方式默认式亮色主题
//        request();
//        requestTest();
//        observerTest();
//        initWeview();
        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.RED);// SDK21
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().getDecorView().setSystemUiVisibility((SYSTEM_UI_FLAG_FULLSCREEN|SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|SYSTEM_UI_FLAG_HIDE_NAVIGATION));
    }

    private void initWeview() {
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);// 设置支持javascript脚本
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//设置webview缓存模式
        webview.setVerticalScrollBarEnabled(false); // 取消Vertical ScrollBar显示
        webview.setHorizontalScrollBarEnabled(false); // 取消Horizontal ScrollBar显示
        //设置自适应屏幕，两者合用
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAllowFileAccess(true);// 允许访问文件
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        boolean hardwareAccelerated = webview.isHardwareAccelerated();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.setFocusable(false); // 去掉超链接的外边框
        webview.getSettings().setDefaultTextEncodingName("GBK");//设置文本编码（根据页面要求设置： utf-8）
        webview.setWebChromeClient(new MyWebChromeClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        //settings.setPluginsEnabled(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        webview.loadUrl("http://www.axyxt.com/axlive/h5/v3/index.html?reg_source=axyxt011&skintypes=skin_night");
        webview.loadUrl("http://service.spiritsoft.cn/ua.html");
    }
    private void observerTest() {
        Observer<String> stringObserver = new Observer<>();
        stringObserver.create();
    }

    private void requestTest() {
        DelegateObject delegateObject = new DelegateObject();
        delegateObject.getDynamicProxy();
    }

    public void request() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<Translation> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                if (response != null && response.body()!=null) {
                    // 步骤7：处理返回的数据结果
                    response.body().show();
                }
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Translation> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }

    class MyWebChromeClient extends WebChromeClient {

        // 全屏的时候调用
        @Override
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {

            super.onShowCustomView(view, callback);

        }

        // 切换为竖屏的时候调用
        @Override
        public void onHideCustomView() {

            super.onHideCustomView();

        }

        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
            webview.destroy();
            webview = null;

        }
}

package com.example.liaozhihua.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import com.example.liaozhihua.test.network.request.GetRequest_Interface;
import com.example.liaozhihua.test.network.request.Translation;
import com.example.liaozhihua.test.observer.Observer;
import com.example.liaozhihua.test.proxy.DelegateObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        request();
        requestTest();
        observerTest();
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
}

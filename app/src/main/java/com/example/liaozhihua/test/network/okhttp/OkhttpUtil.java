package com.example.liaozhihua.test.network.okhttp;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpUtil {
    private String createOkhttp(String url){
        //新建OKHttpClient客户端
        OkHttpClient client = new OkHttpClient();
        //新建一个Request对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        //Response为OKHttp中的响应
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.isSuccessful()) {
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                throw new IOException("Unexpected code " + response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return url;
    }
}

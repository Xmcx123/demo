package com.enjoy.liaozhihua.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.enjoy.liaozhihua.test.KeepManager;
import com.enjoy.liaozhihua.test.R;

public class KeepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep);
        Window window = getWindow();
        window.setGravity(Gravity.TOP|Gravity.START);
        WindowManager.LayoutParams attributes = window.getAttributes();
        //设置宽高1px
        attributes.width = 1;
        attributes.height = 1;
        //设置坐标
        attributes.x = 0;
        attributes.y = 0;
        window.setAttributes(attributes);
        KeepManager.getManager().setKeepActivity(this);

    }
}

package com.example.liaozhihua.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.liaozhihua.test.data.PieData;
import com.example.liaozhihua.test.view.CheckView;
import com.example.liaozhihua.test.view.PieView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        PieView idPview = findViewById(R.id.id_pview);
        CheckView idcheckView = findViewById(R.id.id_check_view);
        idcheckView.check();
        idcheckView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        idcheckView.setAnimDuration(5000);
        idPview.setStartAngle(0);
        ArrayList<PieData> pieDataList = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            PieData pieData = new PieData("测试" + i, 50 + i * 20);
            pieDataList.add(pieData);
        }
        idPview.setData(pieDataList);
        Intent intent = new Intent(this,RequestActivity.class);
        startActivity(intent);
    }
}

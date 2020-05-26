package com.enjoy.liaozhihua.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.enjoy.liaozhihua.test.activity.SmartRefreshActivity;
import com.enjoy.liaozhihua.test.enjoyfix.EnjoyFixActivity;
import com.enjoy.liaozhihua.test.service.ForegroundService;
import com.enjoy.liaozhihua.test.view.CheckView;
import com.enjoy.liaozhihua.test.view.PieView;
import com.example.annotations.BindView;
import com.example.annotations.MyAnnotation;
import com.example.mylibrary.MyButterknife;

@MyAnnotation("Hello World")
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.id_pview)
    PieView idPview2;

    @BindView(R.id.test)
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MyButterknife.bind(this);
//        test.setText("HHHHH");
//        initView();
//        String s1 = s.toUpperCase();
        boolean empty = TextUtils.isEmpty("");
        boolean empty1 = TextUtils.isEmpty(null);
        boolean addd = "".contains("addd");
        boolean addd2 = "".contains("addd");

//        KeepManager.getManager().registerReceiver(this);
        Intent intent = new Intent(this, ForegroundService.class);
        startService(intent);
    }


    private void initView() {
        PieView idPview = findViewById(R.id.id_pview);
        CheckView idcheckView = findViewById(R.id.id_check_view);
        idcheckView.check();
        idcheckView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        idcheckView.setAnimDuration(5000);
        idPview.setStartAngle(0);
    //        ArrayList<PieData> pieDataList = new ArrayList<>();
    //        for (int i = 0; i <5 ; i++) {
    //            PieData pieData = new PieData("测试" + i, 50 + i * 20);
    //            pieDataList.add(pieData);
    //        }
    //        idPview.setData(pieDataList);
//        Intent intent = new Intent(this,RequestActivity.class);
//        startActivity(intent);
//        Intent intent = new Intent(this, EnjoyFixActivity.class);
        Intent intent = new Intent(this, SmartRefreshActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        KeepManager.getManager().unRegisterReceiver(this);
        Intent intent = new Intent(this, ForegroundService.class);
        stopService(intent);
        MyButterknife.unBind(this);
    }
}

package com.enjoy.liaozhihua.test.enjoyfix;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.Enumeration;

import com.enjoy.liaozhihua.test.R;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

public class EnjoyFixActivity extends AppCompatActivity {

    private int dd;
    @Override
    protected void onStart() {
        super.onStart();
    }

    public EnjoyFixActivity() {
        super();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enjoy_fix);
        ClassLoader classLoader = getClassLoader();
        ClassLoader classLoader1 = this.getClassLoader();
        ClassLoader classLoader2 = Activity.class.getClassLoader();
        System.out.println("getClassLoader:"+classLoader);
        System.out.println("getClassLoader 的父亲 :"+classLoader.getParent());
        System.out.println("getClassLoader :"+classLoader1);
        System.out.println("getClassLoader :"+classLoader2);
//        getClassLoader().loadClass()
        try {
            DexFile dexFile = new DexFile("");
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()) {
                String name = entries.nextElement();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        PathClassLoader pathClassLoader = new PathClassLoader("/sdcard/fix.dex", getClassLoader());
        Class<?> aClass = null;
        try {
            aClass = pathClassLoader.loadClass("com.enjoy.enjoyfix.BugPatch");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(aClass);
         // /data/data/packagename : 私有目录
         // 2: dex优化为odex之后保存的目录，必须是私有目录，不能是sd卡的目录
        //getCodeCacheDir()该目录适合在运行时存放应用产生的编译或者优化的代码
        DexClassLoader dexClassLoader = new DexClassLoader("/sdcard/fix.dex", getCodeCacheDir().getAbsolutePath(),
                null, getClassLoader());
        try {
            Class<?> bClass = dexClassLoader.loadClass("com.enjoy.enjoyfix.BugPatch");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //            System.out.println(bClass);
    }
    public void  ddd(){

    }
}

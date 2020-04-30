package com.enjoy.liaozhihua.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class DynamicProxyCampty implements InvocationHandler {

    private PhoneCampty phoneCampty;

    public DynamicProxyCampty(PhoneCampty phoneCampty) {
        this.phoneCampty = phoneCampty;
    }


    private void after(){
        //购买后
    }

    private void before(){
        //购买前
    }

    public Object getIntenes(Class<?>[] interfaces){
        return Proxy.newProxyInstance(phoneCampty.getClass().getClassLoader(),interfaces,this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(phoneCampty, args);
        after();
        return invoke;
    }
}

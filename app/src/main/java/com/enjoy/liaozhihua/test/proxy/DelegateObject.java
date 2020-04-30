package com.enjoy.liaozhihua.test.proxy;

/**
 * 委托对象
 */
public class DelegateObject {
    public void getProxy() {
        PhoneCampty phoneCampty = new AppleCampty();
        ProxyCampty proxyCampty = new ProxyCampty(phoneCampty);
        proxyCampty.price();
    }

    public void getDynamicProxy(){
        PhoneCampty phoneCampty = new AppleCampty();
        PhoneCampty intenes = (PhoneCampty) new DynamicProxyCampty(phoneCampty).getIntenes(new Class[]{PhoneCampty.class});
        intenes.price();

    }
}

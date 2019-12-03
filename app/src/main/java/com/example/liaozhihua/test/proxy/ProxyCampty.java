package com.example.liaozhihua.test.proxy;

/**
 * 代理类 也就是代购(静态代理类 解耦性弱)
 */
public class ProxyCampty implements PhoneCampty{

    private PhoneCampty phoneCampty;

    public ProxyCampty(PhoneCampty phoneCampty) {
        this.phoneCampty = phoneCampty;
    }

    public PhoneCampty getPhoneCampty() {
        return phoneCampty;
    }

    public void setPhoneCampty(PhoneCampty phoneCampty) {
        this.phoneCampty = phoneCampty;
    }

    private void after(){
        //购买后
    }

    private void before(){
        //购买前
    }

    @Override
    public void price() {
        before();
        phoneCampty.price();
        after();
    }
}

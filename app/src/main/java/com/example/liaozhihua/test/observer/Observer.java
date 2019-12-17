package com.example.liaozhihua.test.observer;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Observer<S> {
    public void create(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e("TAG", "subscribe(): 所在线程为 " + Thread.currentThread().getName());
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onComplete();
            }
        }).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                return Observable.just(s);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("TAG", "onSubscribe(): 所在线程为 " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(String s) {
                Log.e("TAG", "onNext(): 所在线程为 " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onComplete(): 所在线程为 " + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}

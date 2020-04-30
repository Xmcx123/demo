package com.example.mylibrary;

/**
 * UI绑定解绑接口
 *
 * @param
 */
public interface ViewBinder<T> {

    void bindView(T host, Object object, ActivityViewFinder finder);

    void unBindView(T host);
}

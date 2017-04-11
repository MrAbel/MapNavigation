package com.example.mapnavigation.utils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * 封装信息打印
 * Created by zzg on 17-4-10.
 */

public class ToastUtils {

    // 全局上下文环境
    private static Context mContext;

    // -------------构造函数-----------------
    public ToastUtils(Context context){
        mContext = context;
    }


    // ----------------输出信息函数-----------------
    /**
     * 使用Toast长时间输出信息
     * @param info
     */
    public static void showLong(String info) {
        Toast.makeText(mContext, info, Toast.LENGTH_LONG).show();
    }

    /**
     * 使用Toast短时间输出信息
     * @param info
     */
    public static void showShort(String info){
        Toast.makeText(mContext, info, Toast.LENGTH_SHORT).show();
    }

}

package com.example.mapnavigation.utils;

import android.view.View;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * 应用程序帮助类
 * Created by zzg on 17-4-11.
 */

public class AppUtils {

    /**
     *根据传进来的文本框来获取输入的字符串
     * @param textView
     * @return
     */
    public static String getString(TextView textView){

        String s = "";

        if (textView != null){
            s = textView.getText().toString().trim();
        }

        return s;
    }

    /**
     * 判断字符串是否是空串(如果字符串为空或为空串返回false,否则返回ture)
     * @param string
     * @return
     */
    public static boolean checkString(String string){

        if ((string == null) || ("".equals(string))){
            return false;
        }else{
            return true;
        }
    }
}

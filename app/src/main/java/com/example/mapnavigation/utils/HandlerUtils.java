package com.example.mapnavigation.utils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by zzg on 17-4-16.
 */

public class HandlerUtils {

    private Handler mHandler;

    public void handlerMessage(){
        mHandler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        break;
                    default:
                        break;
                }
            }
        };
    }
}

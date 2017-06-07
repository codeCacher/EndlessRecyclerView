package com.cs.endlessrecyclerviewtest;

/**
 * Created by Administrator on 2017/6/5.
 */

public class NetUtils {
    interface CallBack{
        void onSuccess();
        void onFailure();
    }
    public static void simulatePostRequest(final CallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    callBack.onFailure();
                }
                callBack.onSuccess();
            }
        }).start();
    }
}

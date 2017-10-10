package com.example.easyeventbuslib;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Jason Chen on 2017/9/27.
 * 事件总线工具类
 */

public class EventBusManager {
    private static EventBusManager manager;
    private HashMap<String, OnEventMessageListener> listenerHashMap = new HashMap<>();
    private Class c;//指定发送的对象
    private static final int SEND_ALL = 0;  //发送所有
    private static final int SEND_SINGLE = 1;   //发送单个


    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //收到消息给所有注册监听的对象发送消息
            switch (msg.what) {
                case 0:
                    //群发消息
                    Set<String> keys = listenerHashMap.keySet();
                    for (String key : keys) {
                        listenerHashMap.get(key).onMsg(msg.obj.toString());
                    }
                    break;
                case 1:
                    //指定发送
                    if(c != null){
                        listenerHashMap.get(c.getName()).onMsg(msg.obj.toString());
                        c = null;
                    }
                    break;
            }
        }
    };

    public static EventBusManager getInstance() {
        if (manager == null) {
            synchronized (EventBusManager.class) {
                if (manager == null) {
                    manager = new EventBusManager();
                }
            }
        }
        return manager;
    }

    /**
     * 伪装成注册，其实就是添加一个监听，在onCreate中注册
     *
     * @param c        注册的对象，强制性以类为单位注册，也就是让某个类实现监听接口
     * @param listener 监听器
     */
    public void registerEvent(Class c, OnEventMessageListener listener) {
        listenerHashMap.put(c.getName(), listener);
    }

    /**
     * 注销监听，在onDestroy中注销
     *
     * @param listener 监听器
     */
    public void unregisterEvent(Class c, OnEventMessageListener listener) {
        listenerHashMap.remove(c.getName());
    }

    /**
     * 群发消息
     *
     * @param o 发送的内容
     */
    public void sendMsg(String o) {
        handler.sendMessage(handler.obtainMessage(SEND_ALL, o));
    }

    /**
     * 发送指定对象
     *
     * @param c 需要发送的对象
     * @param o 发送的内容
     */
    public void sendMsg(Class c, String o) {
        this.c = c;
        handler.sendMessage(handler.obtainMessage(SEND_SINGLE, o));
    }

    public interface OnEventMessageListener {
        void onMsg(String s);
    }
}

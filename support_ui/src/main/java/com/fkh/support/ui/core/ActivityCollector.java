package com.fkh.support.ui.core;

import android.app.Activity;

import java.util.Stack;

public class ActivityCollector {

    private static ActivityCollector instance;
    private Stack<Activity> activityStack;//activity栈

    private ActivityCollector() {
    }

    //单例模式
    public static ActivityCollector getInstance() {
        if (instance == null) {
            instance = new ActivityCollector();
        }
        return instance;
    }

    /**
     * 清空stack
     */
    public void clearStack(){
        if(activityStack != null){
            activityStack.clear();
        }
    }

    //把一个activity压入栈中
    public void pushOneActivity(Activity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(actvity);
    }

    //获取栈顶的activity，先进后出原则
    public Activity getLastActivity() {
        return activityStack.lastElement();
    }

    //移除一个activity
    public void popOneActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                if (!activity.isFinishing() && !activity.isDestroyed()){
                    activity.finish();
                }
                activityStack.remove(activity);
            }
        }
    }

    //退出所有activity
    public void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivity();
                if (activity == null) break;
                popOneActivity(activity);
            }
        }
    }


}


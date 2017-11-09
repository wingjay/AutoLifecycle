package com.wingjay.jayandroid.autolifecycle;

import android.util.Log;

/**
 * IKnowLifecycle
 *
 * @author wingjay
 * @date 2017/10/22
 */
public class IKnowLifecycle {

    public IKnowLifecycle(ILifecycleProvider lifecycleProvider) {
        AutoLifecycle.getInstance().init(this, lifecycleProvider);
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.CREATE)
    private void onCreate() {
        Log.e("jaydebug", "IKnowLifecycle onCreate");
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.START)
    private void onSTART() {
        Log.e("jaydebug", "IKnowLifecycle onSTART");
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.RESUME)
    private void onRESUME() {
        Log.e("jaydebug", "IKnowLifecycle onRESUME");
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.PAUSE)
    private void onPAUSE() {
        Log.e("jaydebug", "IKnowLifecycle onPAUSE");
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.STOP)
    private void onSTOP() {
        Log.e("jaydebug", "IKnowLifecycle onSTOP");
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.DESTROY)
    private void onDESTROY() {
        Log.e("jaydebug", "IKnowLifecycle onDESTROY");
    }
}

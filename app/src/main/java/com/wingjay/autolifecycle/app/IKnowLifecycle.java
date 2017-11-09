package com.wingjay.autolifecycle.app;

import com.wingjay.autolifecycle.library.ALog;
import com.wingjay.autolifecycle.library.AutoLifecycle;
import com.wingjay.autolifecycle.library.AutoLifecycleEvent;
import com.wingjay.autolifecycle.library.ILifecycleProvider;
import com.wingjay.autolifecycle.library.lifecycle.ActivityLifecycle;

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
        ALog.e("IKnowLifecycle onCreate");
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.START)
    private void onStart() {
        ALog.e("IKnowLifecycle onSTART");
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.RESUME)
    private void onResume() {
        ALog.e("IKnowLifecycle onRESUME");
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.PAUSE)
    private void onPause() {
        ALog.e("IKnowLifecycle onPAUSE");
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.STOP)
    private void onStop() {
        ALog.e("IKnowLifecycle onSTOP");
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.DESTROY)
    private void onDestroy() {
        ALog.e("IKnowLifecycle onDESTROY");
    }
}

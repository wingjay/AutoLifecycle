package com.wingjay.autolifecycle.app;

import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import com.wingjay.autolifecycle.library.ALog;
import com.wingjay.autolifecycle.library.lifecycle.ActivityLifecycle;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * `adb logcat | grep 'AutoLifecycle'` to see log.
 * author: wingjay
 * http://wingjay.com
 */
public class MainActivity extends BaseActivity {
    private IKnowLifecycle mIKnowLifecycle = new IKnowLifecycle(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.interval(1, TimeUnit.SECONDS)
            .compose(this.<Long>bindUntilEvent(ActivityLifecycle.STOP))
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    ALog.e("auto-stop when Activity onDestroy: interval " + aLong);
                }
            });

        Observable loadDataObservable = Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                return Observable.just(null).map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {
                        ALog.e("auto-execute when Activity RESUME: loadData()");
                        return null;
                    }
                });
            }
        });
        executeWhen(loadDataObservable, ActivityLifecycle.RESUME);

        setContentView(R.layout.activity_main);
    }
}

package com.wingjay.autolifecycle.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
                    Log.e(TAG, "auto-stop when Activity onDestroy: interval " + aLong);
                }
            });

        Observable loadDataObservable = Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                return Observable.just(null).map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {
                        Log.e(TAG, "auto-execute when Activity PRE_INFLATE: loadData()");
                        return null;
                    }
                });
            }
        });
        executeWhen(loadDataObservable, ActivityLifecycle.PRE_INFLATE);

        setContentView(R.layout.activity_test);
    }
}

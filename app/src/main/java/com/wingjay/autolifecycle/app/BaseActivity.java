package com.wingjay.autolifecycle.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.wingjay.autolifecycle.library.ALog;
import com.wingjay.autolifecycle.library.IContextLifecycle;
import com.wingjay.autolifecycle.library.ILifecycleProvider;
import com.wingjay.autolifecycle.library.LifecycleProviderDelegate;
import com.wingjay.autolifecycle.library.lifecycle.ActivityLifecycle;
import rx.Observable;
import rx.Observable.Transformer;
import rx.subjects.PublishSubject;

/**
 * author: wingjay
 * http://wingjay.com
 */
public class BaseActivity extends AppCompatActivity implements ILifecycleProvider {

    protected final PublishSubject<IContextLifecycle> lifecycleSubject = PublishSubject.create();
    private LifecycleProviderDelegate lifecycleProviderDelegate = new LifecycleProviderDelegate();
    protected String TAG = "jayDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ALog.i("onCreate");
        lifecycleSubject.onNext(ActivityLifecycle.CREATE);
    }

    @Override
    public void setContentView(int layoutResID) {
        lifecycleSubject.onNext(ActivityLifecycle.PRE_INFLATE);
        ALog.i("setContentView");
        super.setContentView(layoutResID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ALog.i("onStart");
        lifecycleSubject.onNext(ActivityLifecycle.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ALog.i("onResume");
        lifecycleSubject.onNext(ActivityLifecycle.RESUME);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        ALog.i("onPostResume");
    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityLifecycle.PAUSE);
        super.onPause();
        ALog.i("onPause");
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityLifecycle.STOP);
        super.onStop();
        ALog.i("onStop");
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityLifecycle.DESTROY);
        super.onDestroy();
        ALog.i("onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ALog.i("onSaveInstanceState");
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        ALog.i("onResumeFragments");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ALog.i("onRestoreInstanceState");
    }

    @Override
    public <T> Transformer<T, T> bindUntilEvent(@NonNull IContextLifecycle event) {
        return lifecycleProviderDelegate.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    public <T> Transformer<T, T> bindDefault() {
        return lifecycleProviderDelegate.bindUntilEvent(lifecycleSubject, ActivityLifecycle.DESTROY);
    }

    @Override
    public void executeWhen(@NonNull Observable observable, @NonNull IContextLifecycle event) {
        lifecycleProviderDelegate.executeWhen(lifecycleSubject, observable, event);
    }
}

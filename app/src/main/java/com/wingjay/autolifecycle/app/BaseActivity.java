package com.wingjay.jayandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import butterknife.ButterKnife;
import com.wingjay.jayandroid.autolifecycle.ActivityLifecycle;
import com.wingjay.jayandroid.autolifecycle.IContextLifecycle;
import com.wingjay.jayandroid.autolifecycle.ILifecycleProvider;
import com.wingjay.jayandroid.autolifecycle.LifecycleProviderDelegate;
import rx.Observable;
import rx.Observable.Transformer;
import rx.subjects.PublishSubject;

public class BaseActivity extends AppCompatActivity implements ILifecycleProvider {

    protected final PublishSubject<IContextLifecycle> lifecycleSubject = PublishSubject.create();
    private LifecycleProviderDelegate lifecycleProviderDelegate = new LifecycleProviderDelegate();
    protected String TAG = "jayDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        lifecycleSubject.onNext(ActivityLifecycle.CREATE);
    }

    @Override
    public void setContentView(int layoutResID) {
        lifecycleSubject.onNext(ActivityLifecycle.PRE_INFLATE);
        Log.i(TAG, "setContentView");
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
        lifecycleSubject.onNext(ActivityLifecycle.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        lifecycleSubject.onNext(ActivityLifecycle.RESUME);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i(TAG, "onPostResume");
    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityLifecycle.PAUSE);
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityLifecycle.STOP);
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityLifecycle.DESTROY);
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Log.i(TAG, "onResumeFragments");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
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

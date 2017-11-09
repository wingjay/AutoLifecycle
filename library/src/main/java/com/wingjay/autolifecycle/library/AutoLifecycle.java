package com.wingjay.autolifecycle.library;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.wingjay.autolifecycle.library.lifecycle.ActivityLifecycle;
import com.wingjay.autolifecycle.library.lifecycle.FragmentLifecycle;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Func0;

/**
 * AutoLifecycle
 *
 * @author wingjay
 * @date 2017/10/22
 */
public class AutoLifecycle {

    private static AutoLifecycle instance;
    private AutoLifecycle() {}
    public synchronized static AutoLifecycle getInstance() {
        if (instance == null) {
            instance = new AutoLifecycle();
        }

        return instance;
    }

    public void init(@NonNull final Object object, @NonNull ILifecycleProvider lifecycleProvider) {
        Class clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (final Method m : methods) {
            AutoLifecycleEvent event = m.getAnnotation(AutoLifecycleEvent.class);
            if (event == null) {
                continue;
            }
            m.setAccessible(true);

            registerFunctionOnLifecycle(lifecycleProvider,
                new OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {
                        try {
                            m.invoke(object);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                },
                event.activity(),
                event.fragment());
        }
    }

    private void registerFunctionOnLifecycle(ILifecycleProvider lifecycleProvider,
                                             final OnSubscribe<Object> executable,
                                             ActivityLifecycle activityLifecycle,
                                             FragmentLifecycle fragmentLifecycle) {
        Observable wrapper = Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                return Observable.create(executable);
            }
        });

        if (lifecycleProvider instanceof AppCompatActivity
            && activityLifecycle != ActivityLifecycle.NULL) {
            lifecycleProvider.executeWhen(wrapper, activityLifecycle);
        } else if (lifecycleProvider instanceof Fragment
            && fragmentLifecycle != FragmentLifecycle.NULL) {
            lifecycleProvider.executeWhen(wrapper, fragmentLifecycle);
        }
    }
}

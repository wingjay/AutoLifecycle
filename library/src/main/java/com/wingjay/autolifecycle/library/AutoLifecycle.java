package com.wingjay.jayandroid.autolifecycle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.support.annotation.NonNull;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.BaseDialogFragment;
import com.wingjay.jayandroid.BaseFragment;
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
                event.fragment(),
                event.dialog());
        }
    }

    private void registerFunctionOnLifecycle(ILifecycleProvider lifecycleProvider,
                                             final OnSubscribe<Object> executable,
                                             ActivityLifecycle activityLifecycle,
                                             FragmentLifecycle fragmentLifecycle,
                                             DialogFragmentLifecycle dialogFragmentLifecycle) {
        Observable wrapper = Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                return Observable.create(executable);
            }
        });

        if (lifecycleProvider instanceof BaseActivity
            && activityLifecycle != ActivityLifecycle.NULL) {
            lifecycleProvider.executeWhen(wrapper, activityLifecycle);
        } else if (lifecycleProvider instanceof BaseFragment
            && fragmentLifecycle != FragmentLifecycle.NULL) {
            lifecycleProvider.executeWhen(wrapper, fragmentLifecycle);
        } else if (lifecycleProvider instanceof BaseDialogFragment
            && dialogFragmentLifecycle != DialogFragmentLifecycle.NULL) {
            lifecycleProvider.executeWhen(wrapper, dialogFragmentLifecycle);
        }
    }
}

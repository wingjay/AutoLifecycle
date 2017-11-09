package com.wingjay.jayandroid.autolifecycle;

import android.support.annotation.NonNull;
import rx.Observable;

/**
 * ILifecycleProvider
 *
 * @author wingjay
 * @date 2017/08/09
 */
public interface ILifecycleProvider {
    /**
     * Binds a source until a specific event occurs.
     *
     * @param event the event that triggers unsubscription
     */
    <T> Observable.Transformer<T, T> bindUntilEvent(@NonNull IContextLifecycle event);

    /**
     * Binds a default event, normally DESTROY event
     */
    <T> Observable.Transformer<T, T> bindDefault();

    /**
     * Execute an Observable once event appears.
     */
    void executeWhen(@NonNull Observable observable, @NonNull IContextLifecycle event);
}

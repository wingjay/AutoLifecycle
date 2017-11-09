package com.wingjay.jayandroid.autolifecycle;

import android.support.annotation.NonNull;
import rx.Observable;
import rx.Observable.Transformer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * LifecycleProviderDelegate
 *
 * @author wingjay
 * @date 2017/10/17
 */
public class LifecycleProviderDelegate {

    public <T> Transformer<T, T> bindUntilEvent(@NonNull final PublishSubject<IContextLifecycle> lifecycleSubject,
                                                @NonNull final IContextLifecycle event) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> sourceObservable) {
                Observable<IContextLifecycle> o =
                    lifecycleSubject.takeFirst(new Func1<IContextLifecycle, Boolean>() {
                        @Override
                        public Boolean call(IContextLifecycle contextLifecycle) {
                            return contextLifecycle.equals(event);
                        }
                    });
                return sourceObservable.takeUntil(o);
            }
        };
    }

    public void executeWhen(@NonNull final PublishSubject<IContextLifecycle> lifecycleSubject,
                            @NonNull final Observable observable,
                            @NonNull final IContextLifecycle event) {
        lifecycleSubject.takeFirst(new Func1<IContextLifecycle, Boolean>() {
            @Override
            public Boolean call(IContextLifecycle contextLifecycle) {
                return contextLifecycle.equals(event);
            }
        }).subscribe(new Subscriber<IContextLifecycle>() {
            @Override
            public void onCompleted() {
                observable.subscribe();
            }
            @Override
            public void onError(Throwable e) {}
            @Override
            public void onNext(IContextLifecycle iContextLifecycle) {}
        });
    }
}

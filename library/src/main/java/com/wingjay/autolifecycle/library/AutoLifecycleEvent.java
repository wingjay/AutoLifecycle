package com.wingjay.jayandroid.autolifecycle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AutoLifecycleEvent
 *
 * @author wingjay
 * @date 2017/10/22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoLifecycleEvent {
    ActivityLifecycle activity() default ActivityLifecycle.NULL;
    FragmentLifecycle fragment() default FragmentLifecycle.NULL;
    DialogFragmentLifecycle dialog() default DialogFragmentLifecycle.NULL;
}

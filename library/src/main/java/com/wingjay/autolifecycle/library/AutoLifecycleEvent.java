package com.wingjay.autolifecycle.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wingjay.autolifecycle.library.lifecycle.ActivityLifecycle;
import com.wingjay.autolifecycle.library.lifecycle.CommonLifecycle;
import com.wingjay.autolifecycle.library.lifecycle.FragmentLifecycle;

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
    CommonLifecycle common() default CommonLifecycle.NULL;
}

package com.wingjay.autolifecycle.library.lifecycle;

import com.wingjay.autolifecycle.library.IContextLifecycle;

/**
 * ActivityLifecycle
 *
 * @author wingjay
 * @date 2017/08/09
 */
public enum ActivityLifecycle implements IContextLifecycle {
    CREATE,
    PRE_INFLATE,
    START,
    RESUME,
    PAUSE,
    STOP,
    DESTROY,
    NULL,
}

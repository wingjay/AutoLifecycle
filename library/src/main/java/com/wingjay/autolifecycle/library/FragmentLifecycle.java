package com.wingjay.jayandroid.autolifecycle;

/**
 * FragmentLifecycle
 *
 * @author wingjay
 * @date 2017/08/09
 */
public enum FragmentLifecycle implements IContextLifecycle {
    ATTACH,
    CREATE,
    PRE_INFLATE,
    VIEW_CREATED,
    START,
    RESUME,
    PAUSE,
    STOP,
    DESTROY_VIEW,
    DESTROY,
    DETACH,
    NULL,
}

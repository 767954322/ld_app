package com.autodesk.shejijia.enterprise;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wenhulin on 10/27/16.
 */

public class LibUtils {
    public final static void installLeakCanary(Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(application);
    }
}

package com.autodesk.shejijia.consumer;

import com.autodesk.shejijia.shared.components.im.IWorkflowDelegate;
import com.autodesk.shejijia.shared.framework.AdskApplication;

public class ConsumerApplication extends AdskApplication
{
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IWorkflowDelegate getIMWorkflowDelegate()
    {
        return mDesignPlatformDelegate;
    }

    private DesignPlatformDelegate mDesignPlatformDelegate = new DesignPlatformDelegate();

}

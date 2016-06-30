package com.autodesk.shejijia.shared.components.common.uielements.reusewheel.libs;

/**
 * Created by yaoxuehua on 16-6-17.
 */
final class OnItemSelectedRunnable implements Runnable {
    final WheelView loopView;

    OnItemSelectedRunnable(WheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getCurrentItem());
    }
}

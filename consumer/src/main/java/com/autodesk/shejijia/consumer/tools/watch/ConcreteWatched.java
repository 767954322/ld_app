package com.autodesk.shejijia.consumer.tools.watch;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.FiltrateContentBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ConcreteWatched implements Watched {
    // 定义一个List来封装Watcher
    private ArrayList <Watcher> list = new ArrayList<Watcher>();
    @Override
    public void add(Watcher watcher) {
        list.add(watcher);
    }
    @Override
    public void remove(Watcher watcher) {
        list.remove(watcher);
    }
    @Override
    public void notifyWatcher(FiltrateContentBean content) {
        for (Watcher watcher : list) {
            watcher.updateNotify(content);
        }
    }
}

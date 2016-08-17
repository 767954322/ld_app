package com.autodesk.shejijia.consumer.tools.watch;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.FiltrateContentBean;
/**
 * Created by Administrator on 2016/4/7.
 */
public interface Watched {
    //在其接口中定义一个用来增加观察者的方法
    void add(Watcher watcher);
    //再定义一个用来删除观察者权利的方法
    void remove(Watcher watcher);
    //再定义一个可以实现行为变现并向观察者传输信息的方法
    void notifyWatcher(FiltrateContentBean content);
}

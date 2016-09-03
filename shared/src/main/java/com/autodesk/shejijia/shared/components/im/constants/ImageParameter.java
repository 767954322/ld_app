package com.autodesk.shejijia.shared.components.im.constants;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

/**
 * Created by Administrator on 2016/4/17 0017.
 */
public class ImageParameter {

    public final static int highQuality = 100;
    public final static int normalQuality = 60;
    public final static int lowQuality = 40;

    public final static String tempPath = ImageLoader.getInstance().getDiskCache().getDirectory().getPath();
    //    public final static String tempImageName = "temp.jpg";
    public final static String tempImageFilePath = tempPath + File.separator + "temp.jpg";


}

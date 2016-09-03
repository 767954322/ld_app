/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.cloopen.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.autodesk.shejijia.shared.components.common.utility;

import android.content.Context;
import android.os.Environment;

import com.autodesk.shejijia.shared.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author   luchongbin .
 * @version  v1.0 .
 * @date       2016-6-13 .
 * @file          ConfigProperties.java .
 * @brief       管理properties配置文件的类 .
 */
public final class ConfigProperties {


	private static Properties properties;

	public static String LOCAL_PATH = getExternalStorePath() + "/config.properties";
	/**
	 *  oldUrl
	 */
	public static String OLDURL = "";

	/**
	 *  newUrl
	 */
	public static String NEWURL = "";

	/**
	 * 初始化属性
	 */
	public static boolean initProperties(Context context) {

		if (properties == null) {
			properties = new Properties();
		}

		if (isExistExternalStore()) {
			String content = readContentByFile(LOCAL_PATH);
			if (content != null) {
				try {
					properties.load(new FileInputStream(LOCAL_PATH));
					return loadConfigByProperties();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			} 
		}
		try {
			properties.load(context.getResources().openRawResource(R.raw.config));
			return loadConfigByProperties();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private ConfigProperties() {

	}

	public static String readContentByFile(String path) {
		BufferedReader reader = null;
		String line = null;
		try {
			File file = new File(path);
			if (file.exists()) {
				StringBuilder sb = new StringBuilder();
				reader = new BufferedReader(new FileReader(file));
				while ((line = reader.readLine()) != null) {
					sb.append(line.trim());
				}
				return sb.toString().trim();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}


	/**
	 * load config info from config.properties
	 */
	private static boolean loadConfigByProperties() {
		OLDURL = properties.getProperty("oldUrl");
		NEWURL = properties.getProperty("newUrl");
		return check();
	}


	public static boolean check() {

		return OLDURL != null && !OLDURL.equals("") && NEWURL != null && !NEWURL.equals("");
	}

	public static String getNewUrl(String url){
		return (url!=null&&url.length() > 0)?url.replace(OLDURL,NEWURL):url;
	}

	public static void release() {
		OLDURL = null;
		NEWURL = null;
	}
	/**
	 * /sdcard
	 *
	 * @return
	 */
	public static String getExternalStorePath() {
		if (isExistExternalStore()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}
	/**
	 * 是否有外存卡
	 *
	 * @return
	 */
	public static boolean isExistExternalStore() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}
}

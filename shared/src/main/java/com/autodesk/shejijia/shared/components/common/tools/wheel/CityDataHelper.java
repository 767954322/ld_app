package com.autodesk.shejijia.shared.components.common.tools.wheel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.autodesk.shejijia.shared.components.common.tools.wheel.model.CityModel;
import com.autodesk.shejijia.shared.components.common.tools.wheel.model.DistrictModel;
import com.autodesk.shejijia.shared.components.common.tools.wheel.model.ProvinceModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-8 .
 * @file CityDataHelper.java .
 * @brief 省市区联动, 数据库帮助类 .
 */
public class CityDataHelper {

    private CityDataHelper(Context context) {
        DATABASES_DIR = "/data/data/" + context.getPackageName() + "/databases/";
    }

    public static CityDataHelper getInstance(Context context) {
        if (dataHelper == null) {
            dataHelper = new CityDataHelper(context);
        }
        return dataHelper;
    }

    /**
     * 复制数据库操作
     *
     * @param inStream 文件输入流
     * @param fileNme  文件名字
     * @param newPath  要复制的文件路径
     */
    public void copyFile(InputStream inStream, String fileNme, String newPath) {
        try {
            int byteSum = 0;
            int byteRead = 0;

            File file = new File(newPath);
            //保证文件夹存在
            if (!file.exists()) {
                file.mkdir();
            }
            //如果文件存在覆盖
            File newFile = new File(newPath + File.separator + fileNme);
            if (newFile.exists()) {
                newFile.delete();
                newFile.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024 * 2];
            int length;
            while ((byteRead = inStream.read(buffer)) != -1) {
                byteSum += byteRead; //字节数 文件大小
                System.out.println(byteSum);
                fs.write(buffer, 0, byteRead);
            }
            inStream.close();
            fs.close();
        } catch (Exception e) {
            Log.d("CopyError", "复制文件操作出错");
            e.printStackTrace();

        }
    }

    /**
     * 打开数据库文件
     *
     * @return 数据库
     */
    public SQLiteDatabase openDataBase() {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(
                DATABASES_DIR + DATABASE_NAME, null);
        return sqLiteDatabase;
    }

    /**
     * 查询所有的省
     *
     * @param db 数据库
     * @return 所有符合条件的省
     */
    public List<ProvinceModel> getProvince(SQLiteDatabase db) {
        String sql = "SELECT * FROM t_address_province ORDER BY code";
        Cursor cursor = db.rawQuery(sql, null);
        List<ProvinceModel> list = new ArrayList<ProvinceModel>();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ProvinceModel provinceModel = new ProvinceModel();
                provinceModel.ID = cursor.getString(cursor.getColumnIndex("id"));
                provinceModel.NAME = cursor.getString(cursor.getColumnIndex("region_name"));
                provinceModel.CODE = cursor.getString(cursor.getColumnIndex("code"));
                list.add(provinceModel);
            }
        }
        return list;
    }

    /**
     * 根据省code查询所有的市
     *
     * @param db   数据库
     * @param code 字段编码
     * @return 市的父id
     */
    public List<CityModel> getCityByParentId(SQLiteDatabase db, String code) {
        String sql = "SELECT * FROM t_address_city WHERE provinceCode=? ORDER BY code";
        Cursor cursor = db.rawQuery(sql, new String[]{code});
        List<CityModel> list = new ArrayList<CityModel>();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                CityModel cityModel = new CityModel();
                cityModel.ID = cursor.getString(cursor.getColumnIndex("id"));
                cityModel.NAME = cursor.getString(cursor.getColumnIndex("region_name"));
                cityModel.CODE = cursor.getString(cursor.getColumnIndex("code"));
                list.add(cityModel);
            }
        }
        return list;
    }

    /**
     * 根据市code查询所有的区
     *
     * @param db   数据库
     * @param code 编码
     * @return 区的父id
     */
    public List<DistrictModel> getDistrictById(SQLiteDatabase db, String code) {
        String sql = "SELECT * FROM t_address_town WHERE cityCode=? ORDER BY code ";
        Cursor cursor = db.rawQuery(sql, new String[]{code});
        List<DistrictModel> list = new ArrayList<DistrictModel>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                DistrictModel districtModel = new DistrictModel();
                districtModel.ID = cursor.getString(cursor.getColumnIndex("id"));
                districtModel.NAME = cursor.getString(cursor.getColumnIndex("region_name"));
                districtModel.CODE = cursor.getString(cursor.getColumnIndex("code"));
                list.add(districtModel);
            }
        }
        return list;
    }

    /**
     * 根据省code查询省名称
     *
     * @param db   数据库
     * @param code 字段编码
     * @return 省的name
     */
    public String getProvinceName(SQLiteDatabase db, String code) {
        String sql = "select * from t_address_province  where code = " + code;
        Cursor cursor = db.rawQuery(sql, null);
        String name = "";
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex("region_name"));
            }
        }
        cursor.close();
        return name;
    }

    /**
     * 根据市code查询市
     *
     * @param db   数据库
     * @param code 字段编码
     * @return 市的name
     */
    public String getCityName(SQLiteDatabase db, String code) {
        String sql = "select * from t_address_city  where code = " + code;
        Cursor cursor = db.rawQuery(sql, null);
        String name = "";
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex("region_name"));
            }
        }
        cursor.close();
        return name;
    }

    /**
     * 根据省code查询区
     *
     * @param db   数据库
     * @param code 字段编码
     * @return 区的name
     */
    public String getDistrictName(SQLiteDatabase db, String code) {
        String sql = "select * from t_address_town  where code = " + code;
        Cursor cursor = db.rawQuery(sql, null);
        String name = "";
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex("region_name"));
            }
        }
        cursor.close();
        return name;
    }

    public static String DATABASES_DIR;//数据库目录路径
    public static String DATABASE_NAME = "province.db";//要复制的数据库名
    private static CityDataHelper dataHelper;
}

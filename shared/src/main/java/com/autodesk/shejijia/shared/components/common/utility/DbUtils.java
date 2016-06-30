package com.autodesk.shejijia.shared.components.common.utility;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.tools.wheel.CityDataHelper;

/**
 * @author   he.liu .
 * @version  v1.0 .
 * @date       2016-6-12 .
 * @file          DbUtils.java .
 * @brief       省市区数据库操作的工具类 .
 */
public class DbUtils {
    private static CityDataHelper cityDataHelper;
    private static SQLiteDatabase db;

    private DbUtils() {
    }

    /**
     * @param tag  省市区，province,city,district
     *             Constant.DbTag.PROVINCE
     *             Constant.DbTag.CITY
     *             Constant.DbTag.DISTRICT
     * @param code 对应的code
     * @return
     */
    public static String getCodeName(Context context, String tag, String code) {
        cityDataHelper = CityDataHelper.getInstance(context);
        db = cityDataHelper.openDataBase();
        String tableName = null;
        String codeName = null;
        switch (tag) {
            case Constant.DbTag.PROVINCE:
                tableName = Constant.DbTag.PROVINCE_TABLE_NAME;
                break;
            case Constant.DbTag.CITY:
                tableName = Constant.DbTag.CITY_TABLE_NAME;
                break;
            case Constant.DbTag.DISTRICT:
                tableName = Constant.DbTag.DISTRICT_TABLE_NAME;
                break;
        }
        String sql = "SELECT * FROM " + tableName + " WHERE code=" + code;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                codeName = cursor.getString(cursor.getColumnIndex(Constant.DbTag.CODE_NAME));
            }
        }
        cursor.close();
        db.close();
        if (TextUtils.isEmpty(codeName)) {
            return code;
        }
        return codeName;
    }
}

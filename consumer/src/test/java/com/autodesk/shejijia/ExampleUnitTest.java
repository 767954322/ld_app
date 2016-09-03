package com.autodesk.shejijia;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;


import com.autodesk.shejijia.consumer.utils.AppDataFormatValidator.MPDesignFormatValidator;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        MPDesignFormatValidator Validator=new MPDesignFormatValidator();

        String a,b,c;
        a="省市區";
        b="省市區";
        c="省市區";
        String str= Validator.getStrProvinceCityDistrict(a,b,c);



        return;
    }
}
package com.autodesk.shejijia.shared.components.form.common.entity.microBean;

import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.entity.microbean.File;
import com.autodesk.shejijia.shared.components.common.entity.microbean.SHFile;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_panya on 16/11/1.
 */

public class FormFeedBack implements Serializable {
    private String comment;
    private File audio;
    private List<File> images;
    private Integer currentActionIndex = 0;
    private Integer currentCheckIndex = -1;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public File getAudio() {
        return audio;
    }

    public void setAudio(File audio) {
        this.audio = audio;
    }

    public List<File> getImages() {
        return images;
    }

    public void setImages(List<File> images) {
        this.images = images;
    }

    public Integer getCurrentActionIndex() {
        return currentActionIndex;
    }

    public void setCurrentActionIndex(Integer currentActionIndex) {
        this.currentActionIndex = currentActionIndex;
    }

    public Integer getCurrentCheckIndex() {
        return currentCheckIndex;
    }

    public void setCurrentCheckIndex(Integer currentCheckIndex) {
        this.currentCheckIndex = currentCheckIndex;
    }

    public FormFeedBack(List<Map> values){
        initWithList(values);
    }

    private void initWithList(List<Map> values){
        this.comment = "";
        this.audio = new File();
        this.images = new ArrayList<File>();
        for(Map valueMap : values){
            if(valueMap instanceof HashMap){
                if("text".equals(valueMap.get("type"))){
                    comment = (String) valueMap.get("value");
                }

                if("audio".equals(valueMap.get("type"))){
                    audio.setPublicUrl((String) valueMap.get("value"));
                    if(valueMap.get("id") == null){
                        audio.setFileId("");
                    }else {
                        audio.setFileId(valueMap.get("id").toString());
                    }
                }

                if("image".equals(valueMap.get("type"))){
                    File image = new File();
                    image.setPublicUrl((String) valueMap.get("value"));
                    image.setThumbnailUrl((String) valueMap.get("thumbnail_url"));
                    if(valueMap.get("id") == null){
                        image.setFileId("");
                    }else {
                        image.setFileId(valueMap.get("id").toString());
                    }
                    images.add(image);
                }
            }
        }
        onInit(values);

    }

    private void onInit(List<Map> values){
        for(Map valueMap : values){
            if(valueMap instanceof HashMap){
                if("check_result".equals(valueMap.get("type"))){
                    this.currentCheckIndex = Integer.parseInt((String) valueMap.get("value"));
                }
                if("action_result".equals(valueMap.get("type"))){
                    this.currentActionIndex = Integer.parseInt((String) valueMap.get("value"));
                }
            }
        }
    }

    public void applyInitData(List<Map> values){
        initWithList(values);
    }

    public List combineUpdateData(){
        List<Map> feedBackDataList = new ArrayList<>();
        Map<String,Object> textType = new HashMap<>();
        textType.put("type","text");
        textType.put("value",this.comment == null?"":this.comment);
        LogUtils.d("FormFeedBack", "comment == " + this.comment);
        feedBackDataList.add(textType);
        if(!TextUtils.isEmpty(this.audio.getPublicUrl())){
            LogUtils.d("FormFeedBack", "file" + this.audio.toString());
            Map<String,Object> audio = new HashMap<>();
            audio.put("type","audio");
            audio.put("value",this.audio.getPublicUrl());
            audio.put("id",this.audio.getFileId());
            feedBackDataList.add(audio);
        }
        if(images != null && images.size() != 0){
            LogUtils.d("FormFeedBack", "images.size == " + images.size());
            for(File file : images){
                LogUtils.d("FormFeedBack", "file" + file.toString());
                if(!TextUtils.isEmpty(file.getPublicUrl())){
                    LogUtils.d("FormFeedBack", "file.getPublicUrl() == " + file.getPublicUrl());
                    Map<String,Object> image = new HashMap<>();
                    image.put("type","image");
                    image.put("value",file.getPublicUrl());
                    image.put("id",file.getFileId());
                    image.put("thumbnail_url",file.getThumbnailUrl());
                    feedBackDataList.add(image);
                }
            }
        }
        feedBackDataList.addAll(onCombineUpdateData());
        return feedBackDataList;
    }

    private List onCombineUpdateData(){
        List<Map> feedBackDataList = new ArrayList<>();
        Map<String,Object> checkMap = new HashMap<>();
        checkMap.put("type","check_result");
        checkMap.put("value",this.currentCheckIndex.toString());
        feedBackDataList.add(checkMap);
        Map<String,Object> actionMap = new HashMap<>();
        actionMap.put("type","action_result");
        actionMap.put("value",this.currentActionIndex.toString());
        feedBackDataList.add(actionMap);
        return feedBackDataList;
    }

}

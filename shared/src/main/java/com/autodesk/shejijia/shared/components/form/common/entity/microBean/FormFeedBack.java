package com.autodesk.shejijia.shared.components.form.common.entity.microBean;

import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.form.common.entity.FormFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_panya on 16/11/1.
 */

public class FormFeedBack {
    private String comment;
    private FormFile audio;
    private List<FormFile> images;
    private Integer currentActionIndex;
    private Integer currentCheckIndex;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public FormFile getAudio() {
        return audio;
    }

    public void setAudio(FormFile audio) {
        this.audio = audio;
    }

    public List<FormFile> getImages() {
        return images;
    }

    public void setImages(List<FormFile> images) {
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
        this.audio = new FormFile();
        this.images = new ArrayList<FormFile>();
        for(Map valueMap : values){
            if(valueMap instanceof HashMap){
                if("text".equals(valueMap.get("type"))){
                    comment = (String) valueMap.get("value");
                }

                if("audio".equals(valueMap.get("type"))){
                    audio.setPicture_url((String) valueMap.get("value"));
                    if(valueMap.get("id") == null){
                        audio.setFileId("");
                    }else {
                        audio.setFileId(valueMap.get("id").toString());
                    }
                }

                if("image".equals(valueMap.get("type"))){
                    FormFile image = new FormFile();
                    image.setPicture_url((String) valueMap.get("value"));
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
        feedBackDataList.add(textType);
        if(!TextUtils.isEmpty(this.audio.getPicture_url())){
            Map<String,Object> audio = new HashMap<>();
            audio.put("type","audio");
            audio.put("value",this.audio.getPicture_url());
            audio.put("id",this.audio.getFileId());
            feedBackDataList.add(audio);
        }
        if(images != null && images.size() != 0){
            for(FormFile file : images){
                if(!TextUtils.isEmpty(file.getPicture_url())){
                    Map<String,Object> image = new HashMap<>();
                    image.put("type","image");
                    image.put("value",file.getPicture_url());
                    image.put("id",file.getFileId());
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
        checkMap.put("value",this.currentCheckIndex);
        feedBackDataList.add(checkMap);
        Map<String,Object> actionMap = new HashMap<>();
        actionMap.put("type","action_result");
        actionMap.put("value",this.currentActionIndex);
        feedBackDataList.add(actionMap);
        return feedBackDataList;
    }

}

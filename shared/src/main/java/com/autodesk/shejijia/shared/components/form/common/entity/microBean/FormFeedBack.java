package com.autodesk.shejijia.shared.components.form.common.entity.microBean;

import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.entity.microbean.SHFile;

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
    private SHFile audio;
    private List<SHFile> images;
    private Integer currentActionIndex = -1;
    private Integer currentCheckIndex = 0;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public SHFile getAudio() {
        return audio;
    }

    public void setAudio(SHFile audio) {
        this.audio = audio;
    }

    public List<SHFile> getImages() {
        return images;
    }

    public void setImages(List<SHFile> images) {
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
        this.audio = new SHFile();
        this.images = new ArrayList<SHFile>();
        for(Map valueMap : values){
            if(valueMap instanceof HashMap){
                if("text".equals(valueMap.get("type"))){
                    comment = (String) valueMap.get("value");
                }

                if("audio".equals(valueMap.get("type"))){
                    audio.setPictureUrl((String) valueMap.get("value"));
                    if(valueMap.get("id") == null){
                        audio.setFileId("");
                    }else {
                        audio.setFileId(valueMap.get("id").toString());
                    }
                }

                if("image".equals(valueMap.get("type"))){
                    SHFile image = new SHFile();
                    image.setPictureUrl((String) valueMap.get("value"));
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
        if(!TextUtils.isEmpty(this.audio.getPictureUrl())){
            Map<String,Object> audio = new HashMap<>();
            audio.put("type","audio");
            audio.put("value",this.audio.getPictureUrl());
            audio.put("id",this.audio.getFileId());
            feedBackDataList.add(audio);
        }
        if(images != null && images.size() != 0){
            for(SHFile file : images){
                if(!TextUtils.isEmpty(file.getPictureUrl())){
                    Map<String,Object> image = new HashMap<>();
                    image.put("type","image");
                    image.put("value",file.getPictureUrl());
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

package com.autodesk.shejijia.shared.components.common.entity.microbean;

import android.widget.Switch;

import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by t_xuz on 11/14/16.
 */

public class MileStone implements Serializable {

    @SerializedName("milestone_id")
    private String milestoneId;
    @SerializedName("milestone_name")
    private String milestoneName;

    public String getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(String milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public static class MileStoneTypeAdapter extends TypeAdapter<MileStone> {

        public MileStone read(JsonReader reader) throws IOException {
            MileStone mileStone = null;
            switch (reader.peek()) {
                case BEGIN_OBJECT:
                    mileStone = new MileStone();
                    reader.beginObject();
                    while (reader.hasNext()) {
                        switch (reader.nextName()) {
                            case "milestone_id":
                                mileStone.setMilestoneId(reader.nextString());
                                break;
                            case "milestone_name":
                                mileStone.setMilestoneName(reader.nextString());
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case STRING:
                    mileStone = new MileStone();
                    mileStone.setMilestoneId(reader.nextString());
                    break;
                case NULL:
                    reader.nextNull();
                    break;
                default:
                    break;
            }

            return mileStone;
        }

        public void write(JsonWriter writer, MileStone rated) throws IOException {
            if(rated == null) {
                writer.nullValue();
            } else {
                writer.beginObject();
                writer.name("milestone_id").value(rated.getMilestoneId());
                writer.name("milestone_name").value(rated.getMilestoneName());
                writer.endObject();
            }
        }
    }
}

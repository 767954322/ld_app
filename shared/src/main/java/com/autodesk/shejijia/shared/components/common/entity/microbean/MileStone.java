package com.autodesk.shejijia.shared.components.common.entity.microbean;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by t_xuz on 11/14/16.
 */

public class MileStone implements Serializable {
    private final static String FIELD_MILESTONE_ID = "milestone_id";
    private final static String FIELD_MILESTONE_NAME = "milestone_name";

    @SerializedName(FIELD_MILESTONE_ID)
    private String milestoneId;
    @SerializedName(FIELD_MILESTONE_NAME)
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
                            case FIELD_MILESTONE_ID:
                                mileStone.setMilestoneId(reader.nextString());
                                break;
                            case FIELD_MILESTONE_NAME:
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
                writer.name(FIELD_MILESTONE_ID).value(rated.getMilestoneId());
                writer.name(FIELD_MILESTONE_NAME).value(rated.getMilestoneName());
                writer.endObject();
            }
        }
    }
}

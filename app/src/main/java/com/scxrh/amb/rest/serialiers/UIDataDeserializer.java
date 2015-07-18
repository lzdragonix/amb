package com.scxrh.amb.rest.serialiers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.scxrh.amb.model.UIData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UIDataDeserializer implements JsonDeserializer<List<UIData>>
{
    @Override
    public List<UIData> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        List<UIData> data = new ArrayList<>();
        Gson gson = new Gson();
        json = json.getAsJsonObject().get("data");
        data.add(gson.fromJson(json.getAsJsonObject().get("buy"), UIData.class));
        data.add(gson.fromJson(json.getAsJsonObject().get("hot"), UIData.class));
        data.add(gson.fromJson(json.getAsJsonObject().get("ad"), UIData.class));
        data.add(gson.fromJson(json.getAsJsonObject().get("finance"), UIData.class));
        data.add(gson.fromJson(json.getAsJsonObject().get("lottery"), UIData.class));
        data.add(gson.fromJson(json.getAsJsonObject().get("discount"), UIData.class));
        return data;
    }
}

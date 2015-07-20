package com.scxrh.amb.rest.serialiers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.scxrh.amb.model.UIData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UIDataDeserializer implements JsonDeserializer<Map<String, UIData>>
{
    @Override
    public Map<String, UIData> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        Map<String, UIData> data = new HashMap<>();
        Gson gson = new Gson();
        json = json.getAsJsonObject().get("data");
        data.put("buy", gson.fromJson(json.getAsJsonObject().get("buy"), UIData.class));
        data.put("hot", gson.fromJson(json.getAsJsonObject().get("hot"), UIData.class));
        data.put("ad", gson.fromJson(json.getAsJsonObject().get("ad"), UIData.class));
        data.put("finance", gson.fromJson(json.getAsJsonObject().get("finance"), UIData.class));
        data.put("lottery", gson.fromJson(json.getAsJsonObject().get("lottery"), UIData.class));
        data.put("discount", gson.fromJson(json.getAsJsonObject().get("discount"), UIData.class));
        return data;
    }
}

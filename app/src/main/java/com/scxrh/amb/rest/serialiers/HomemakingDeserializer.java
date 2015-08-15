package com.scxrh.amb.rest.serialiers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.scxrh.amb.model.Homemaking;

import java.lang.reflect.Type;
import java.util.List;

public class HomemakingDeserializer implements JsonDeserializer<List<Homemaking>>
{
    @Override
    public List<Homemaking> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        Gson gson = new Gson();
        json = json.getAsJsonObject().get("data");
        return gson.fromJson(json, typeOfT);
    }
}

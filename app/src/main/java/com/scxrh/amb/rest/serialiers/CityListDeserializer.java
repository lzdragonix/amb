package com.scxrh.amb.rest.serialiers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.scxrh.amb.model.City;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CityListDeserializer implements JsonDeserializer<List<List<City>>>
{
    @Override
    public List<List<City>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        List<List<City>> data = new ArrayList<>();
        JsonObject obj = json.getAsJsonObject().get("data").getAsJsonObject();
        Gson gson = new Gson();
        Type type = new TypeToken<List<City>>() { }.getType();
        data.add(gson.fromJson(obj.get("hot"), type));
        data.add(gson.fromJson(obj.get("normal"), type));
        return data;
    }
}

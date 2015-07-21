package com.scxrh.amb.rest.serialiers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class JSONObjectDeserializer implements JsonDeserializer<JSONObject>
{
    @Override
    public JSONObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        try
        {
            return new JSONObject(json.toString());
        }
        catch (JSONException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }
}

package com.scxrh.amb.rest.serialiers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.scxrh.amb.Const;
import com.scxrh.amb.model.Order;

import java.lang.reflect.Type;
import java.util.List;

public class OrderDeserializer implements JsonDeserializer<List<Order>>
{
    @Override
    public List<Order> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        Gson gson = new Gson();
        String code= json.getAsJsonObject().get(Const.KEY_CODE).getAsString();
        if(Const.RETURNCODE_0001.equals(code))
        {
            throw new RuntimeException("un-login");
        }
        json = json.getAsJsonObject().get("data");
        return gson.fromJson(json, typeOfT);
    }
}

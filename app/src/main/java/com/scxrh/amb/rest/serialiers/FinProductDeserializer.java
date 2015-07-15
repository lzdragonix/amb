package com.scxrh.amb.rest.serialiers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.scxrh.amb.model.FinancialProduct;

import java.lang.reflect.Type;
import java.util.List;

public class FinProductDeserializer implements JsonDeserializer<List<FinancialProduct>>
{
    @Override
    public List<FinancialProduct> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        Gson gson = new Gson();
        json = json.getAsJsonObject().get("data");
        return gson.fromJson(json, typeOfT);
    }
}

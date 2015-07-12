package com.scxrh.amb.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.scxrh.amb.model.City;

import java.io.IOException;
import java.util.List;

public class CityItemAdapterFactory implements TypeAdapterFactory
{
    @Override
    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type)
    {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
        return new TypeAdapter<T>()
        {
            @Override
            public void write(JsonWriter out, T value) throws IOException
            {
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException
            {
                JsonElement jsonElement = elementAdapter.read(in);
                if (type.getRawType() == City.class) { return adaptJsonToCity(jsonElement, type); }
                if (type.getRawType() == List.class) { return adaptJsonToList(jsonElement, type); }
                return delegate.fromJsonTree(jsonElement);
            }
        }.nullSafe();
    }

    private <T> T adaptJsonToCity(JsonElement jsonElement, TypeToken<T> type)
    {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("data"))
        {
            JsonElement marvelDataElement = jsonObject.get("data");
            JsonObject marvelDataObject = marvelDataElement.getAsJsonObject();
            if (marvelDataObject.get("count").getAsInt() == 1)
            {
                JsonArray marvelResults = marvelDataObject.get("results").getAsJsonArray();
                JsonElement finalElement = marvelResults.get(0);
                return new Gson().fromJson(finalElement, type.getType());
            }
        }
        return null;
    }

    private <T> T adaptJsonToList(JsonElement jsonElement, TypeToken<T> type)
    {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("data"))
        {
            JsonElement data = jsonObject.get("data");
            JsonElement element = data.getAsJsonObject().get("normal");
            return new Gson().fromJson(element, type.getType());
        }
        return null;
    }
}

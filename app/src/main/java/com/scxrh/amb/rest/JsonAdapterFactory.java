package com.scxrh.amb.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.scxrh.amb.Const;

import java.io.IOException;
import java.util.List;

public class JsonAdapterFactory implements TypeAdapterFactory
{
    private Gson mGson = new Gson();

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
                String code = jsonElement.getAsJsonObject().get(Const.KEY_CODE).getAsString();
                if (Const.RETURNCODE_0000.equals(code))
                {
                    if (type.getRawType() == List.class)
                    {
                        return adaptJsonToList(jsonElement, type);
                    }
                    else
                    {
                        return adaptJsonToModel(jsonElement, type);
                    }
                }
                else if (Const.RETURNCODE_0001.equals(code))
                {
                    throw new RuntimeException("un-login");
                }
                else
                {
                    throw new RuntimeException(jsonElement.getAsString());
                }
            }
        }.nullSafe();
    }

    private <T> T adaptJsonToModel(JsonElement jsonElement, TypeToken<T> type)
    {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("data"))
        {
            return mGson.fromJson(jsonObject.get("data"), type.getType());
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
            return mGson.fromJson(element, type.getType());
        }
        return null;
    }
}

package com.marshaldevs.marshalmaterialsmaker;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarshalServiceProvider {

    // TODO
    private static final String MARSHAL_BASE_URL = "http://marshalweb.azurewebsites.net/api/";
    public static final String POST_MATERIAL = MARSHAL_BASE_URL + "materials/";

    private static IMarshalService service;
    
    public static IMarshalService getInstance() {
        if (service != null) {
            return service;
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MARSHAL_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .registerTypeAdapter(Date.class, new JsonDateSerializer())
                            .registerTypeAdapter(Date.class, new JsonDateDeserializer())
                            .excludeFieldsWithoutExposeAnnotation()
                            .create()))
                    .build();

            service = retrofit.create(IMarshalService.class);
            return service;
        }
    }

    public static class JsonDateDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String s = json.getAsJsonPrimitive().getAsString();
//            long l = Long.parseLong(s.substring(6, s.length() - 2));
            Date d = new Date(Long.valueOf(s.substring(6, s.length() - 2)));
            return d;
        }
    }

    public static class JsonDateSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive("/Date("+ src.getTime() + ")/");
        }
    }
}

package com.diaudrimes.productapp.api.serializers;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.diaudrimes.productapp.persistence.entities.Product;
import com.diaudrimes.productapp.services.LogService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

@Component
@Scope(value = "singleton")
public class ProductSerializer extends EntitySerializer<Product> {

  public ProductSerializer(LogService logService) {
    super(logService);
  }

  @Override
  public JsonElement serialize(Product product, Type type) {
    JsonElement jsonElement = new Gson().toJsonTree(product, Product.class);
    return jsonElement;
  }

  @Override
  public Product deserialize(JsonElement jsonElement, Type type) throws JsonParseException {
    // "2018-09-09T21:50:04-03:00"
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<ZonedDateTime>() {
          @Override
          public ZonedDateTime deserialize(JsonElement json, Type type,
              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Instant instant;
            try {
              instant = Instant
                  .ofEpochMilli(sdf.parse(json.getAsJsonPrimitive().getAsString()).getTime());
              return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
            } catch (ParseException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            return null;
          }
        }).create();
    Product product = gson.fromJson(jsonElement, Product.class);
    return product;
  }



}

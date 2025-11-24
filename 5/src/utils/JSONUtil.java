package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.Flower;
import model.Lily;
import model.Rose;
import model.Tulip;

import java.io.IOException;

public class JSONUtil {
    
    private static final Gson gson = createGson();
    
    private static Gson createGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Flower.class, new FlowerTypeAdapter())
                .registerTypeAdapter(storage.Bouquet.class, new BouquetTypeAdapter())
                .create();
    }
    
    /**
     * Deserialize JSON string to an object of the specified class
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
    
    /**
     * Serialize an object to JSON string
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
    
    /**
     * Custom TypeAdapter for polymorphic Flower deserialization
     */
    private static class FlowerTypeAdapter extends TypeAdapter<Flower> {
        @Override
        public void write(JsonWriter out, Flower flower) throws IOException {
            if (flower == null) {
                out.nullValue();
                return;
            }
            
            out.beginObject();
            out.name("type").value(flower.getClass().getSimpleName());
            out.name("name").value(flower.name);
            out.name("color").value(flower.color);
            out.name("freshness").value(flower.getFreshness());
            out.name("stemLength").value(flower.getStemLength());
            out.name("price").value(flower.getPrice());
            
            // Type-specific fields
            if (flower instanceof Rose) {
                out.name("hasThorns").value(((Rose) flower).hasThorns);
            } else if (flower instanceof Lily) {
                out.name("bloomLevel").value(((Lily) flower).bloomLevel);
            } else if (flower instanceof Tulip) {
                out.name("leavesNumber").value(((Tulip) flower).leavesNumber);
            }
            
            out.endObject();
        }
        
        @Override
        public Flower read(JsonReader in) throws IOException {
            if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            
            String type = null;
            String name = null;
            String color = null;
            float freshness = 0;
            float stemLength = 0;
            float price = 0;
            boolean hasThorns = false;
            float bloomLevel = 0;
            int leavesNumber = 0;
            
            in.beginObject();
            while (in.hasNext()) {
                String fieldName = in.nextName();
                switch (fieldName) {
                    case "type":
                        type = in.nextString();
                        break;
                    case "name":
                        name = in.nextString();
                        break;
                    case "color":
                        color = in.nextString();
                        break;
                    case "freshness":
                        freshness = (float) in.nextDouble();
                        break;
                    case "stemLength":
                        stemLength = (float) in.nextDouble();
                        break;
                    case "price":
                        price = (float) in.nextDouble();
                        break;
                    case "hasThorns":
                        hasThorns = in.nextBoolean();
                        break;
                    case "bloomLevel":
                        bloomLevel = (float) in.nextDouble();
                        break;
                    case "leavesNumber":
                        leavesNumber = in.nextInt();
                        break;
                    default:
                        in.skipValue();
                        break;
                }
            }
            in.endObject();
            
            Flower flower = null;
            if ("Rose".equals(type)) {
                Rose rose = new Rose();
                rose.hasThorns = hasThorns;
                flower = rose;
            } else if ("Lily".equals(type)) {
                Lily lily = new Lily();
                lily.bloomLevel = bloomLevel;
                flower = lily;
            } else if ("Tulip".equals(type)) {
                Tulip tulip = new Tulip();
                tulip.leavesNumber = leavesNumber;
                flower = tulip;
            }
            
            if (flower != null) {
                flower.name = name;
                flower.color = color;
                flower.setFreshness(freshness);
                flower.setStemLength(stemLength);
                flower.setPrice(price);
            }
            
            return flower;
        }
    }
    
    /**
     * Custom TypeAdapter for Bouquet to handle accessories properly
     */
    private static class BouquetTypeAdapter extends TypeAdapter<storage.Bouquet> {
        @Override
        public void write(JsonWriter out, storage.Bouquet bouquet) throws IOException {
            if (bouquet == null) {
                out.nullValue();
                return;
            }
            
            out.beginObject();
            
            // Write flowers array
            out.name("flowers");
            out.beginArray();
            for (Flower flower : bouquet.getFlowers()) {
                new FlowerTypeAdapter().write(out, flower);
            }
            out.endArray();
            
            // Write accessories as objects with name and price
            out.name("accessories");
            out.beginArray();
            java.util.List<String> accessoryNames = bouquet.getAccessories();
            java.util.List<Float> accessoryPrices = bouquet.getAccessoryPrices();
            for (int i = 0; i < accessoryNames.size(); i++) {
                out.beginObject();
                out.name("name").value(accessoryNames.get(i));
                out.name("price").value(accessoryPrices.get(i));
                out.endObject();
            }
            out.endArray();
            
            out.endObject();
        }
        
        @Override
        public storage.Bouquet read(JsonReader in) throws IOException {
            if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            
            storage.Bouquet bouquet = new storage.Bouquet();
            
            in.beginObject();
            while (in.hasNext()) {
                String fieldName = in.nextName();
                if ("flowers".equals(fieldName)) {
                    in.beginArray();
                    while (in.hasNext()) {
                        Flower flower = new FlowerTypeAdapter().read(in);
                        if (flower != null) {
                            bouquet.addFlower(flower);
                        }
                    }
                    in.endArray();
                } else if ("accessories".equals(fieldName)) {
                    in.beginArray();
                    while (in.hasNext()) {
                        String name = null;
                        float price = 0;
                        
                        in.beginObject();
                        while (in.hasNext()) {
                            String accessoryField = in.nextName();
                            if ("name".equals(accessoryField)) {
                                name = in.nextString();
                            } else if ("price".equals(accessoryField)) {
                                price = (float) in.nextDouble();
                            } else {
                                in.skipValue();
                            }
                        }
                        in.endObject();
                        
                        if (name != null) {
                            bouquet.addAccessory(name, price);
                        }
                    }
                    in.endArray();
                } else {
                    in.skipValue();
                }
            }
            in.endObject();
            
            return bouquet;
        }
    }
}

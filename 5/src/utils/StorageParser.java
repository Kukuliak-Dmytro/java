package utils;

import model.Flower;
import storage.Bouquet;
import storage.Storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StorageParser {
    
    public static void parseStorageFromJson(Storage storage, String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            // Read the entire file as a string first
            StringBuilder jsonBuilder = new StringBuilder();
            int ch;
            while ((ch = reader.read()) != -1) {
                jsonBuilder.append((char) ch);
            }
            String json = jsonBuilder.toString();
            
            // Use JSONUtil's Gson instance which has the Flower TypeAdapter
            StorageData data = JSONUtil.fromJson(json, StorageData.class);
            
            if (data == null) {
                return;
            }
            
            storage.getFlowersInStorage().clear();
            storage.getBouquetsInStorage().clear();
            
            if (data.flowers != null) {
                for (Flower flower : data.flowers) {
                    if (flower != null) {
                        storage.addFlower(flower);
                    }
                }
            }
            
            if (data.bouquets != null) {
                for (Bouquet bouquet : data.bouquets) {
                    if (bouquet != null) {
                        storage.addBouquet(bouquet);
                    }
                }
            }
        } catch (IOException e) {
            // File doesn't exist or can't be read - this is OK for first run
        } catch (Exception e) {
            // JSON parsing error
            e.printStackTrace();
        }
    }
    
    public static void serializeStorageToJson(Storage storage, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            StorageData data = new StorageData();
            data.flowers = storage.getFlowersInStorage();
            data.bouquets = storage.getBouquetsInStorage();
            
            // Use JSONUtil's Gson instance which has the Flower TypeAdapter
            String json = JSONUtil.toJson(data);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Helper class to wrap storage data
    public static class StorageData {
        public List<Flower> flowers;
        public List<Bouquet> bouquets;
    }
}

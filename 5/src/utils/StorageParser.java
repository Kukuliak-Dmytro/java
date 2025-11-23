package utils;

import model.Flower;
import model.Rose;
import model.Lily;
import model.Tulip;
import storage.Bouquet;
import storage.Storage;

import java.util.List;

public class StorageParser {
    
    public static void parseStorageFromJson(Storage storage, String filePath) {
        String json = JSONUtil.loadFromJSON(filePath);
        if (json == null || json.trim().isEmpty()) {
            return; // No file or empty file
        }
        
        storage.getFlowersInStorage().clear();
        storage.getBouquetsInStorage().clear();
        
        // Parse flowers
        int flowersStart = json.indexOf("\"flowers\"");
        if (flowersStart != -1) {
            int arrayStart = json.indexOf("[", flowersStart);
            if (arrayStart != -1) {
                int arrayEnd = findMatchingBracket(json, arrayStart);
                if (arrayEnd != -1) {
                    String flowersJson = json.substring(arrayStart + 1, arrayEnd);
                    parseFlowers(storage, flowersJson);
                }
            }
        }
        
        // Parse bouquets
        int bouquetsStart = json.indexOf("\"bouquets\"");
        if (bouquetsStart != -1) {
            int arrayStart = json.indexOf("[", bouquetsStart);
            if (arrayStart != -1) {
                int arrayEnd = findMatchingBracket(json, arrayStart);
                if (arrayEnd != -1) {
                    String bouquetsJson = json.substring(arrayStart + 1, arrayEnd);
                    parseBouquets(storage, bouquetsJson);
                }
            }
        }
    }
    
    private static void parseFlowers(Storage storage, String flowersJson) {
        int pos = 0;
        while (pos < flowersJson.length()) {
            int flowerStart = flowersJson.indexOf("{", pos);
            if (flowerStart == -1) break;
            
            int flowerEnd = JSONUtil.findMatchingBrace(flowersJson, flowerStart);
            if (flowerEnd == -1) break;
            
            String flowerJson = flowersJson.substring(flowerStart, flowerEnd + 1);
            Flower flower = parseFlower(flowerJson);
            if (flower != null) {
                storage.addFlower(flower);
            }
            
            pos = flowerEnd + 1;
        }
    }
    
    private static Flower parseFlower(String flowerJson) {
        String type = JSONUtil.getString(flowerJson, "type");
        String name = JSONUtil.getString(flowerJson, "name");
        String color = JSONUtil.getString(flowerJson, "color");
        float freshness = JSONUtil.getFloat(flowerJson, "freshness");
        float stemLength = JSONUtil.getFloat(flowerJson, "stemLength");
        float price = JSONUtil.getFloat(flowerJson, "price");
        
        Flower flower = null;
        if (type.equals("Rose")) {
            Rose rose = new Rose();
            rose.hasThorns = JSONUtil.getBoolean(flowerJson, "hasThorns");
            flower = rose;
        } else if (type.equals("Lily")) {
            Lily lily = new Lily();
            lily.bloomLevel = JSONUtil.getFloat(flowerJson, "bloomLevel");
            flower = lily;
        } else if (type.equals("Tulip")) {
            Tulip tulip = new Tulip();
            tulip.leavesNumber = JSONUtil.getInt(flowerJson, "leavesNumber");
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
    
    private static void parseBouquets(Storage storage, String bouquetsJson) {
        int pos = 0;
        while (pos < bouquetsJson.length()) {
            int bouquetStart = bouquetsJson.indexOf("{", pos);
            if (bouquetStart == -1) break;
            
            int bouquetEnd = JSONUtil.findMatchingBrace(bouquetsJson, bouquetStart);
            if (bouquetEnd == -1) break;
            
            String bouquetJson = bouquetsJson.substring(bouquetStart, bouquetEnd + 1);
            Bouquet bouquet = parseBouquet(bouquetJson);
            if (bouquet != null) {
                storage.addBouquet(bouquet);
            }
            
            pos = bouquetEnd + 1;
        }
    }
    
    private static Bouquet parseBouquet(String bouquetJson) {
        Bouquet bouquet = new Bouquet();
        
        // Parse flowers (full data, not indices)
        int flowersStart = bouquetJson.indexOf("\"flowers\"");
        if (flowersStart != -1) {
            int arrayStart = bouquetJson.indexOf("[", flowersStart);
            if (arrayStart != -1) {
                int arrayEnd = findMatchingBracket(bouquetJson, arrayStart);
                if (arrayEnd != -1) {
                    String flowersJson = bouquetJson.substring(arrayStart + 1, arrayEnd);
                    parseBouquetFlowers(bouquet, flowersJson);
                }
            }
        }
        
        // Parse accessories
        int accessoriesStart = bouquetJson.indexOf("\"accessories\"");
        if (accessoriesStart != -1) {
            int arrayStart = bouquetJson.indexOf("[", accessoriesStart);
            if (arrayStart != -1) {
                int arrayEnd = findMatchingBracket(bouquetJson, arrayStart);
                if (arrayEnd != -1) {
                    String accessoriesJson = bouquetJson.substring(arrayStart + 1, arrayEnd);
                    parseAccessories(bouquet, accessoriesJson);
                }
            }
        }
        
        bouquet.calculatePrice();
        return bouquet;
    }
    
    private static void parseBouquetFlowers(Bouquet bouquet, String flowersJson) {
        int pos = 0;
        while (pos < flowersJson.length()) {
            int flowerStart = flowersJson.indexOf("{", pos);
            if (flowerStart == -1) break;
            
            int flowerEnd = JSONUtil.findMatchingBrace(flowersJson, flowerStart);
            if (flowerEnd == -1) break;
            
            String flowerJson = flowersJson.substring(flowerStart, flowerEnd + 1);
            Flower flower = parseFlower(flowerJson);
            if (flower != null) {
                bouquet.addFlower(flower);
            }
            
            pos = flowerEnd + 1;
        }
    }
    
    private static void parseAccessories(Bouquet bouquet, String accessoriesJson) {
        int pos = 0;
        while (pos < accessoriesJson.length()) {
            int accessoryStart = accessoriesJson.indexOf("{", pos);
            if (accessoryStart == -1) break;
            
            int accessoryEnd = JSONUtil.findMatchingBrace(accessoriesJson, accessoryStart);
            if (accessoryEnd == -1) break;
            
            String accessoryJson = accessoriesJson.substring(accessoryStart, accessoryEnd + 1);
            String name = JSONUtil.getString(accessoryJson, "name");
            float price = JSONUtil.getFloat(accessoryJson, "price");
            bouquet.addAccessory(name, price);
            
            pos = accessoryEnd + 1;
        }
    }
    
    public static void serializeStorageToJson(Storage storage, String filePath) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        
        // Write flowers
        json.append("  \"flowers\": [\n");
        List<Flower> flowers = storage.getFlowersInStorage();
        for (int i = 0; i < flowers.size(); i++) {
            Flower flower = flowers.get(i);
            json.append(serializeFlower(flower, "    "));
            if (i < flowers.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("  ],\n");
        
        // Write bouquets
        json.append("  \"bouquets\": [\n");
        List<Bouquet> bouquets = storage.getBouquetsInStorage();
        for (int i = 0; i < bouquets.size(); i++) {
            Bouquet bouquet = bouquets.get(i);
            json.append(serializeBouquet(bouquet, "    "));
            if (i < bouquets.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("  ]\n");
        json.append("}\n");
        
        JSONUtil.writeToJSON(filePath, json.toString());
    }
    
    private static String serializeFlower(Flower flower, String indent) {
        StringBuilder json = new StringBuilder();
        json.append(indent).append("{\n");
        json.append(indent).append("  \"type\": \"").append(flower.getName()).append("\",\n");
        json.append(indent).append("  \"name\": \"").append(flower.name != null ? flower.name : "").append("\",\n");
        json.append(indent).append("  \"color\": \"").append(flower.color != null ? flower.color : "").append("\",\n");
        json.append(indent).append("  \"freshness\": ").append(flower.getFreshness()).append(",\n");
        json.append(indent).append("  \"stemLength\": ").append(flower.getStemLength()).append(",\n");
        json.append(indent).append("  \"price\": ").append(flower.getPrice());
        
        // Type-specific fields
        if (flower instanceof Rose) {
            json.append(",\n").append(indent).append("  \"hasThorns\": ").append(((Rose) flower).hasThorns);
        } else if (flower instanceof Lily) {
            json.append(",\n").append(indent).append("  \"bloomLevel\": ").append(((Lily) flower).bloomLevel);
        } else if (flower instanceof Tulip) {
            json.append(",\n").append(indent).append("  \"leavesNumber\": ").append(((Tulip) flower).leavesNumber);
        }
        
        json.append("\n").append(indent).append("}");
        return json.toString();
    }
    
    private static String serializeBouquet(Bouquet bouquet, String indent) {
        StringBuilder json = new StringBuilder();
        json.append(indent).append("{\n");
        
        // Write flowers
        json.append(indent).append("  \"flowers\": [\n");
        List<Flower> bouquetFlowers = bouquet.getFlowers();
        for (int j = 0; j < bouquetFlowers.size(); j++) {
            Flower flower = bouquetFlowers.get(j);
            json.append(serializeFlower(flower, indent + "    "));
            if (j < bouquetFlowers.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append(indent).append("  ],\n");
        
        // Write accessories
        json.append(indent).append("  \"accessories\": [\n");
        List<String> accessories = bouquet.getAccessories();
        List<Float> accessoryPrices = bouquet.getAccessoryPrices();
        for (int j = 0; j < accessories.size(); j++) {
            json.append(indent).append("    {\n");
            json.append(indent).append("      \"name\": \"").append(accessories.get(j)).append("\",\n");
            json.append(indent).append("      \"price\": ").append(accessoryPrices.get(j)).append("\n");
            json.append(indent).append("    }");
            if (j < accessories.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append(indent).append("  ]\n");
        json.append(indent).append("}");
        
        return json.toString();
    }
    
    // Helper method to find matching bracket for arrays
    private static int findMatchingBracket(String json, int start) {
        int depth = 0;
        for (int i = start; i < json.length(); i++) {
            if (json.charAt(i) == '[') depth++;
            if (json.charAt(i) == ']') {
                depth--;
                if (depth == 0) return i;
            }
        }
        return -1;
    }
}


package storage;

import entities.Flower;
import utils.JSONUtil;

public class Storage {

//    the actual instance of the storage
    private static final Storage instance = new Storage();

    private Flower[] flowersInStorage;
    private Bouquet[] bouquetsInStorage;

    public Flower[] getFlowersInStorage() {
        return flowersInStorage != null ? flowersInStorage : new Flower[0];
    }
    
    public Bouquet[] getBouquetsInStorage(){
        return bouquetsInStorage != null ? bouquetsInStorage : new Bouquet[0];
    }
    
//    we make this constructor private
//    singleton pattern
    private Storage(){
        flowersInStorage = new Flower[0];
        bouquetsInStorage = new Bouquet[0];
        readFromJson();
    }

//    returns the singleton Storage
    public static Storage getInstance(){return instance;}

    public void addFlower(Flower flower){
        if (flowersInStorage == null) {
            flowersInStorage = new Flower[0];
        }
        Flower[] newArray = new Flower[flowersInStorage.length + 1];
        System.arraycopy(flowersInStorage, 0, newArray, 0, flowersInStorage.length);
        newArray[flowersInStorage.length] = flower;
        flowersInStorage = newArray;
    }
    
    public void removeFlower(Flower flower){
        if (flowersInStorage == null || flowersInStorage.length == 0) {
            return;
        }
        int index = -1;
        for (int i = 0; i < flowersInStorage.length; i++) {
            if (flowersInStorage[i] == flower) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            Flower[] newArray = new Flower[flowersInStorage.length - 1];
            System.arraycopy(flowersInStorage, 0, newArray, 0, index);
            System.arraycopy(flowersInStorage, index + 1, newArray, index, flowersInStorage.length - index - 1);
            flowersInStorage = newArray;
        }
    }
    
    private void readFromJson(){
        // Placeholder for JSON reading
    }
    
    private void writeToJson(){
        // Placeholder for JSON writing
    }
}

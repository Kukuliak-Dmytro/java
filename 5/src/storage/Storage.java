package storage;

import entities.Flower;

public class Storage {

//    the actual instance of the storage
    private static final Storage instance = new Storage();

    private Flower[] flowersInStorage;
    private Bouquet[] bouquetsInStorage;

    public Flower[] getFlowersInStorage() {};
    public Bouquet[] getBouquetsInStorage(){};
//    we make this constructor private
//    singleton pattern
    private Storage(){readFromJson();};

//    returns the singleton Storage
    public static Storage getInstance(){return instance;}

    public  void addFlower(Flower flower){}
    public void removeFlower(Flower flower){}
    private void readFromJson(){}
    private void writeToJson(){}
}

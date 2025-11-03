package entities;

public abstract class Flower {
    public String name;
    public String color;
//    freshness is measured in hours since being harvested
    private float freshness;
    private float stemLength;
//    price is measured in UAH
    private float price;

//    constructors
    public Flower(){};
    public Flower(String name, String color, float freshness, float stemLength){};

//    methods
    public float getFreshness() { };
    public void setFreshness(float freshness) { };

    public float getStemLength(){}
    public void setStemLength(float length){}

    public float getPrice(){}
    public void setPrice(float price){}



}

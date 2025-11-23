package model;

public abstract class Flower {
    public String name;
    public String color;
//    freshness is measured in hours since being harvested
    private float freshness;
    private float stemLength;
//    price is measured in UAH
    private float price;

//    constructors
    public Flower(){
        this.name = "";
        this.color = "";
        this.freshness = 0.0f;
        this.stemLength = 0.0f;
        this.price = 0.0f;
    }
    
    public Flower(String name, String color, float freshness, float stemLength){
        this.name = name;
        this.color = color;
        this.freshness = freshness;
        this.stemLength = stemLength;
        this.price = 0.0f;
    }

//    methods
    public float getFreshness() {
        return freshness;
    }
    
    public void setFreshness(float freshness) {
        this.freshness = freshness;
    }

    public float getStemLength(){
        return stemLength;
    }
    
    public void setStemLength(float length){
        this.stemLength = length;
    }

    public float getPrice(){
        return price;
    }
    
    public void setPrice(float price){
        this.price = price;
    }
    
    public String getName(){
        return name;
    }
    
    public void display(){
        System.out.print(getName() + 
                         " - Name: " + (name != null ? name : "N/A") +
                         ", Color: " + (color != null ? color : "N/A") +
                         ", Freshness: " + freshness + " hours" +
                         ", Stem Length: " + stemLength + " cm" +
                         ", Price: " + price + " UAH");
    }
}

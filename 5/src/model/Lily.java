package model;

public class Lily extends Flower{

//    bloom level measured from 0 to 1
    public float bloomLevel;

    @Override
    public void display(){
        super.display();
        System.out.println(", Bloom Level: " + bloomLevel);
    }
}

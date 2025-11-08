package entities;

public class Rose extends Flower{

    public boolean hasThorns;

    public void removeThorns(){};
    
    @Override
    public void display(){
        super.display();
        System.out.println(", Has Thorns: " + hasThorns);
    }
}

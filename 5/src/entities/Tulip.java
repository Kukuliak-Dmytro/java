package entities;

public class Tulip extends Flower{

    public int leavesNumber;

    public void trimLeaves(){}
    
    @Override
    public void display(){
        super.display();
        System.out.println(", Leaves Number: " + leavesNumber);
    }
}

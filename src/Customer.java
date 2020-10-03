import java.io.Serializable;

public class Customer implements Serializable {

    private final int serialVersionUID = 1337;

    private String name;
    private Island destination;
    private Island currentLocation;

    public Customer(Island currentLocation){
        this.currentLocation = currentLocation;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Island getDestination() {
        return destination;
    }

    public void setDestination(Island destination) {
        this.destination = destination;
    }

    public Island getCurrentLocation(){
        return currentLocation;
    }
}

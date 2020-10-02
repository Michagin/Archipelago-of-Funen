import java.io.Serializable;

public class Customer implements Serializable {

    private final int serialVersionUID = 1337;

    private String name;
    private Island destination;

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
}

import java.io.Serializable;

public class Ticket implements Serializable {
    final transient int serialVersionUID = 1337;

    private double price;
    Island to;
    Island from;

    public Ticket(){

    }

    public Ticket(Island to, Island from) {
        this.to = to;
        this.from = from;
    }
}

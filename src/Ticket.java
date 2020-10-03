import java.io.Serializable;

public class Ticket implements Serializable {
    final transient int serialVersionUID = 1337;

    private double price;

    private Island to;
    private Island from;
    private Customer customer;

    public Ticket(){

    }

    public Ticket(Customer customer, Island to, Island from) {
        this.customer = customer;
        this.to = to;
        this.from = from;
    }
}

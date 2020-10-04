import java.io.Serializable;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1337L;

    private double price;

    private Island to;
    private Island from;
    private String customerName;
    private String creationTime;

    public Ticket(String name, Island to, Island from) {
        customerName = name;
        this.to = to;
        this.from = from;
    }


    public Island getTo() {
        return to;
    }

    public Island getFrom() {
        return from;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

}

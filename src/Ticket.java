import java.io.Serializable;

public class Ticket implements Serializable {           //Ticket must implement the Serializable interface to tell the compiler that objects from this class are going to be saved to a file.
    private static final long serialVersionUID = 1337L; //unique ID used for serialization (saving and loading files).

    private String price;                               //used String instead of float due to localization. (, instead of .)
    private final Island to;
    private final Island from;
    private final String customerName;
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


    public String getCustomerName() {
        return customerName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

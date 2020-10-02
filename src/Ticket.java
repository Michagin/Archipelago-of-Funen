import java.io.Serializable;

public class Ticket implements Serializable {
    final transient int serialVersionUID = 1337;

    private double price;
    Island to;
    Island from;

    public Ticket(Island to, Island from) {
        this.to = to;
        this.from = from;
    }

    @Override
    public String toString(){
        return "Denne billet er gyldig fra " + from + " til " + to;
    }
}

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Archipelago archipelago = new Archipelago();
        Customer customer = new Customer();
        IOHandler io = new IOHandler();
        Clerk clerk = new Clerk(customer, archipelago, io);
    }
}

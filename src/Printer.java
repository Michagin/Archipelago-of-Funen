import java.util.Scanner;

public class Printer {
    private final Customer customer;
    private final Scanner nameScanner = new Scanner(System.in);
    private final Scanner destinationScanner = new Scanner(System.in);
    String destination;

    public Printer(Customer customer) {
        this.customer = customer;
    }

    private void printTicket(Ticket ticket){

        System.out.println(ticket.toString());
    }

    public void makeTicketInfo(){
        System.out.println("Please enter your full name");
        customer.setName(nameScanner.nextLine());

        System.out.println("Greetings " + customer.getName() + ".");

        System.out.println("Where would you like to go today?");
        destination = destinationScanner.nextLine();
    }
}


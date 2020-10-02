import java.util.Scanner;

public class Printer {
    Customer customer;
    Scanner nameScanner = new Scanner(System.in);

    public Printer(Customer customer){
        this.customer = customer;
    }

    private void printTicket(Ticket ticket){

        System.out.println(ticket.toString());
    }

    public void getTicketInfo(){
        System.out.println("Please enter your full name");
        customer.setName(nameScanner.nextLine());

        System.out.println("Greetings " + customer.getName() + ".\nWhere would you like to go today?");
    }
}


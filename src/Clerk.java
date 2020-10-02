import java.util.Scanner;

public class Clerk {
    private final Customer customer;
    private final Scanner nameScanner = new Scanner(System.in);
    private final Scanner destinationScanner = new Scanner(System.in);
    private final Archipelago archipelago;
    private String customerDestination;

    public Clerk(Customer customer, Archipelago archipelago) {
        this.customer = customer;
        this.archipelago = archipelago;
    }

    public void bookTicket(){
        getNameFromCustomer();
        System.out.println("Greetings " + customer.getName() + ".");
        System.out.println("Where would you like to go today?");
        System.out.println("Please select one of our incredible up-scale high-end fantastic destinations:");
        getTicketInfoFromCustomer();
    }

    private void getNameFromCustomer(){
        System.out.println("Please enter your full name");
        customer.setName(nameScanner.nextLine());
    }

    private void getTicketInfoFromCustomer(){
        for(int i = 0; i<archipelago.getArchipelago().size();i++){
            System.out.printf("%2d. %s%n", i+1, archipelago.getArchipelago().get(i).getName());
        }

        customerDestination = destinationScanner.nextLine();

        if(confirmDestination(customerDestination) != null){
            customer.setDestination(confirmDestination(customerDestination));
            System.out.println(customer.getDestination().getName() + "? Great choice!");
        } else {
            System.out.println("I am afraid " + customerDestination + " is not in our charters. Please select from the menu:");
            getTicketInfoFromCustomer(); //while loop more efficient in terms of machine code instructions?
        }
    }

    private int answerAsNumber(String answer){
        try {
            return Integer.parseInt(customerDestination);
        } catch(NumberFormatException e){
            System.err.println(e);
            return -1;
        }
    }

    private Island confirmDestination(String destination){
        if(answerAsNumber(destination)>=0){
            for(int i = 0; i<archipelago.getArchipelago().size();i++){
                if(i+1 == answerAsNumber(destination)){
                    return archipelago.getArchipelago().get(i);
                }
            }
        } else{
            for(Island island : archipelago.getArchipelago()){
                if (island.getName().equals(customerDestination))
                    return island;
            }
        }
        return null;
    }

}

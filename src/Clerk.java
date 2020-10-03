import java.io.IOException;
import java.util.Scanner;

public class Clerk {
    private final IOHandler io;
    private final Customer customer;
    private final Scanner optionScanner = new Scanner(System.in);
    private final Scanner nameScanner = new Scanner(System.in);
    private final Scanner destinationScanner = new Scanner(System.in);
    private final Archipelago archipelago;
    private String customerDestination;
    private boolean hasSelectedMenuItem = false;

    public Clerk(Customer customer, Archipelago archipelago, IOHandler io) {
        this.io = io;
        this.customer = customer;
        this.archipelago = archipelago;

        getNameFromCustomer();
        System.out.println("Greetings " + customer.getName() + ".");
        do{
            System.out.println("What would you like to do today?");
            System.out.printf("%2d %s%n",1, "Book a ticket.");
            System.out.printf("%2d %s%n",2, "Activate a pre-purchased ticket.");
            System.out.printf("%2d %s%n",3, "Get info about destinations (only in danish).");

            String selectedOption = optionScanner.nextLine();
            {
                if(selectedOption.matches("[0-9]")){
                    switch (answerAsNumber(selectedOption)) {
                        case 1 :
                            bookTicket();
                            hasSelectedMenuItem = true;
                        break;
                        case 2 :
                            handleTicket(io, io.loadTicketFileAsBin());
                            hasSelectedMenuItem = true;
                        break;
                        case 3 :
                            System.out.println("You have selected \"Get info about island\". What island would you like info about?");
                            prettyPrintIslands();
                            String selectedIsland = destinationScanner.nextLine();
                            if(confirmDestination(selectedIsland)!= null){
                                System.out.println(confirmDestination(selectedIsland).getDescription()+"\n");
                            }
                            break;
                        default : System.out.println("Your menu choice was not recorded. Please select a valid menu item.");
                    }
                } else {
                        System.out.println("I did not understand that. To select an item from the menu, please use the corresponding numbers.");
                }
            }
        } while(!hasSelectedMenuItem);

    }

    public void bookTicket(){
        System.out.println("Where would you like to go today?");
        System.out.println("Please select one of our incredible up-scale high-end fantastic destinations:");
        getTicketInfoFromCustomer();

        System.out.println("Here is your ticket.");
        try {
            io.saveToDisk(new Ticket(customer, customer.getDestination(),customer.getCurrentLocation()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getNameFromCustomer(){
        System.out.println("Please enter your full name");
        customer.setName(nameScanner.nextLine());
    }

    private void getTicketInfoFromCustomer(){
        prettyPrintIslands();

        customerDestination = destinationScanner.nextLine();

        if(confirmDestination(customerDestination) != null){
            customer.setDestination(confirmDestination(customerDestination));
            System.out.println(customer.getDestination().getName() + "? Great choice, " + customer.getName() + "!");
        } else {
            System.out.println("I am afraid " + customerDestination + " is not in our charters. Please select from the menu:");
            getTicketInfoFromCustomer(); //while loop more efficient in terms of machine code instructions?
        }
    }

    private int answerAsNumber(String answer){
        try {
            return Integer.parseInt(answer);
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
        } else {
            return archipelago.getIslandFromName(destination);
        }
        return null;
    }

    private void handleTicket(IOHandler io, Ticket ticket){

    }

    private void prettyPrintIslands(){
        for(int i = 0; i<archipelago.getArchipelago().size();i++){
            System.out.printf("%2d. %s%n", i+1, archipelago.getArchipelago().get(i).getName());
        }
    }

}

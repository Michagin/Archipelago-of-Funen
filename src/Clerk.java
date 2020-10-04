import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Clerk {
    private final Island here;
    private final IOHandler io;
    private final Scanner optionScanner = new Scanner(System.in);
    private final Scanner nameScanner = new Scanner(System.in);
    private final Scanner destinationScanner = new Scanner(System.in);
    private final Archipelago archipelago;
    private String customerName;
    private String customerDestination;
    private boolean hasSelectedMenuItem = false;

    public Clerk(Archipelago archipelago, IOHandler io) {
        this.io = io;
        this.archipelago = archipelago;

        here = archipelago.getRandomIsland();

        intro();
        questionFlow();
    }

    private void intro(){
        System.out.println("I am F.A.T.S - Funen's Automated Ticket System");
        System.out.println("What is your name?");
        customerName = nameScanner.nextLine();
        System.out.println("Greetings " + customerName + ".");
        System.out.printf("\nYour are currently at %s.", here.getName());
        System.out.println();
    }

    private void questionFlow(){
        while (!hasSelectedMenuItem) {
            System.out.println("What would you like to do today?");
            System.out.printf("%2d %s%n", 1, "Book a ticket from here, to another exotic island.");
            System.out.printf("%2d %s%n", 2, "Activate a pre-purchased ticket.");
            System.out.printf("%2d %s%n", 3, "Get info about locations (danish).");

            String selectedOption = optionScanner.nextLine();
            if (selectedOption.matches("[0-9]")) {
                switch (answerAsNumber(selectedOption)) {
                    case 1:
                        bookTicket();
                        hasSelectedMenuItem = true;
                        break;
                    case 2:
                        handleTicket();
                        hasSelectedMenuItem = true;
                        break;
                    case 3:
                        System.out.println("You have selected \"Get info about island\". What island would you like info about?");
                        prettyPrintIslands();
                        String selectedIsland = destinationScanner.nextLine();
                        if (confirmDestination(selectedIsland) != null) {
                            System.out.println(Objects.requireNonNull(confirmDestination(selectedIsland)).getDescription() + "\n");
                        }
                        break;
                    default:
                        System.out.println("Your menu choice was not recorded. Please select a valid menu item.");
                }
            } else {
                System.out.println("I did not understand that. To select an item from the menu, please use the corresponding numbers.");
            }
        }
    }



    public void bookTicket() {
        System.out.println("Where would you like to go today?");
        System.out.println("Please select one of our incredible up-scale high-end fantastic destinations:");
        getTicketInfoFromCustomer();
        Ticket ticket = createTicket();
        if(ticket == null){
            System.out.println("The island you had selected appears to have gone missing. Please stand by while I investigate the situation.");
        } else {
            ticket.setCreationTime(getDateAsString());
            System.out.println("Here is your ticket.");
            try {
                io.saveToDisk(ticket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getTicketInfoFromCustomer() {
        prettyPrintIslands();

        customerDestination = destinationScanner.nextLine();
        Island confirmedIsland = confirmDestination(customerDestination);

        if (confirmedIsland != null) {
            if(confirmedIsland == here){
                System.out.println(
                        "The destination you have selected is: " + confirmedIsland.getName() +
                        "\nHowever, you are already at " + here.getName() + "." +
                        "\nWouldn't you like to go somewhere?..."
                );
                getTicketInfoFromCustomer();
            } else {
            System.out.println(confirmedIsland.getName() + "? Great choice, " + customerName + "!");
            }
        } else {
            //if island is null....
            System.out.println("I am afraid the selected island is not in our charters. Please select from the menu:");
            getTicketInfoFromCustomer(); //while loop vs recursion?
        }
    }

    private int answerAsNumber(String answer) {
        try {
            return Integer.parseInt(answer);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private Island confirmDestination(String destination) {
        if (answerAsNumber(destination) >= 0) {
            for (int i = 0; i < archipelago.getArchipelago().size(); i++) {
                if (i + 1 == answerAsNumber(destination)) {
                    return archipelago.getArchipelago().get(i);
                }
            }
        } else {
            return archipelago.getIslandFromName(destination);
        }
        return null;
    }

    private void handleTicket() {
        Ticket ticket = io.loadTicketFileFromBin();
        if(ticket != null) {

            if (!customerName.toLowerCase().equals(ticket.getCustomerName().toLowerCase())) {
                System.out.println("We seem to have a problem with the ticket.");
                System.out.println("This ticket was issued to " + ticket.getCustomerName() + ".");
                System.out.println("However, your name is: " + ticket.getCustomerName());
                System.out.println("Tickets are only valid for whomever it was originally issued.");
                System.out.println("Please provide your own ticket. If you do not have a valid ticket, you can get one here.");
                questionFlow();
            } else {
                System.out.println(getTicketInfo(ticket));
            }
        } else {
            questionFlow();
        }

    }

    private String getTicketInfo(Ticket ticket) {
        String createTime = ticket.getCreationTime();
        String name = ticket.getCustomerName();
        String destination = ticket.getTo().getName();
        String departure = ticket.getFrom().getName();

        return "This ticket was issued at " + createTime + ".\n" +
               "It was issued to: " + name + ".\n" +
               "From: " + departure + "\n" +
               "To: " + destination;
    }

    private void prettyPrintIslands() {
        for (int i = 0; i < archipelago.getArchipelago().size(); i++) {
            if(!here.getName().equals(archipelago.getArchipelago().get(i).getName())){
                System.out.printf("%2d. %s%n", i + 1, archipelago.getArchipelago().get(i).getName());
            }
        }
    }

    private Ticket createTicket() {
        if(confirmDestination(customerDestination)!= null){
            return new Ticket(customerName, confirmDestination(customerDestination), here);
        } else {
            System.out.println("Island is not in our charter.");
        }
        return null;
    }

    private String getDateAsString(){
        long millis = System.currentTimeMillis();
        return new Date(millis).toString();
    }
}

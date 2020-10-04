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
    private boolean hasSelectedMenuItem = false;    //used for breaking main loop, in case user has selected an item from the menu.

    public Clerk(Archipelago archipelago, IOHandler io) {
        this.io = io;
        this.archipelago = archipelago;

        here = archipelago.getRandomIsland();       //'spawns' the program at a random island.

        intro();                                    //greetings...
        questionFlow();                             //main flow of interaction.
    }

    /**
     * Introduction of the program to the user.
     */
    private void intro(){
        System.out.println("I am F.A.T.S - Funen's Automated Ticket System");
        System.out.println("What is your name?");
        customerName = nameScanner.nextLine();
        System.out.println("Greetings " + customerName + ".");
        System.out.printf("\nYour are currently at %s.", here.getName());
        System.out.println();
    }

    /**
     * Main flow of program. This is where the program interrogates the user about what they want to do. Acts like a menu system.
     */
    private void questionFlow(){
        while (!hasSelectedMenuItem) {
            System.out.println("What would you like to do today?");
            System.out.printf("%2d %s%n", 1, "Book a ticket from here, to another exotic island.");
            System.out.printf("%2d %s%n", 2, "Activate a pre-purchased ticket.");
            System.out.printf("%2d %s%n", 3, "Get info about locations (danish).");

            String selectedOption = optionScanner.nextLine();
                if (selectedOption.matches("[0-9]")) {                                                                              //this regular expression matches only a single number.
                switch (answerAsNumber(selectedOption)) {
                        case 1:                                          /* Case 1: Book a ticket */
                        bookTicket();
                        hasSelectedMenuItem = true;
                        break;
                        case 2:                                          /* Case 2: Activate (load) a ticket */
                        handleTicket();
                        hasSelectedMenuItem = true;
                        break;
                        case 3:                                          /* Case 3: Get info about islands */
                        System.out.println("You have selected \"Get info about island\". What island would you like info about?");
                        prettyPrintIslands();
                        String selectedIsland = destinationScanner.nextLine();                                                           //asks user about which island user wants to get info about.
                        if (confirmDestination(selectedIsland) != null) {                                                                //if the island actually exists, give info about it...
                            System.out.println(Objects.requireNonNull(confirmDestination(selectedIsland)).getDescription() + "\n");      //Objects.requireNonNull is used to assert that the island-object does actually exists.
                        }
                        break;
                    default:
                        System.out.println("Your menu choice was not recorded. Please select a valid menu item.");                       //User gave number above 3
                }
            } else {
                System.out.println("I did not understand that. To select an item from the menu, please use the corresponding numbers."); //user gave something else than a number fromm 0-9.
            }
        }
    }


    /**
     * In case the user wants to get a new ticket.
     * Uses both {@getTicketInfoFromCustomer} and {@createTicket} to populate an empty ticket with information from the user.
     */
    public void bookTicket() {
        System.out.println("Where would you like to go today?");
        System.out.println("Please select one of our incredible up-scale high-end fantastic destinations:");
        getTicketInfoFromCustomer();
        Ticket ticket = createTicket();
        if(ticket == null){                                                                                                             //Should not be null, but could technically be.
            System.out.println("The island you had selected appears to have gone missing. Please stand by while I investigate the situation.");
        } else {
            ticket.setCreationTime(getDateAsString());                                                                                  //Sets the date and time for when the ticket was made (could have been taken from meta data, but meta data changes on modification.
            ticket.setPrice(archipelago.makePrice());                                                                                   //sets the ticket price post ticket creation. Could have been moved to constructor.
            System.out.println("Here is your ticket.");
            System.out.println("That will be, " + ticket.getPrice() + " DKK. Thank you.");
            try {
                io.saveToDisk(ticket);                                                                                                  //Tries to write ticket to disk.

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Asks the user where the users wishes to travel to. If user wishes to travel to current location (even though the option is not visually provided),
     * the program informs the user about the mistake and the method recursively calls itself to start over.
     * Uses {@confirmDestination} to confirm that the desired destination actually is a valid island.
     * @see Clerk#confirmDestination(String)
     */
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
        } else {    /*if island-object is null */

            System.out.println("I am afraid the selected island is not in our charters. Please select from the menu:");
            getTicketInfoFromCustomer();                                                                                                //Recursive call of method, in case the user tries to go to an island which is not in the collection.
                                                                                                                                        //Could have been a while-loop. Perhaps would be faster, if it was a while-loop.
        }
    }

    /**
     * Method used to check whether a user input is a number.
     * @param answer String with a possible number (integer).
     * @return -1 if not a number. Otherwise returns the number (integer)
     */
    private int answerAsNumber(String answer) {
        try {
            return Integer.parseInt(answer);
        } catch (NumberFormatException e) {                                                                                             //Dirty code: uses exceptions for logic.
            return -1;
        }
    }

    /**
     * Tries to confirm whether the desired destination is a valid destination in the archipelago.
     * It does so by first checking whether the user input is a number by running it through {@answerAsNumber}.
     * If it is a number, it goes through the island collection and checks whether the current
     * index + 1 (because the list of islands printed to user start with 1, while the collection's index starts with 0)
     * is equal to the one desired by user. The desired destination is provided as string, so {@answerAsNumber} is used
     * to make it into a number. If the index match the number provided by the user. The island is returned.
     *
     * If the input provided by the user is *not* a number, but the actual name of the Island, the method tries to find
     * the island by name instead of number.
     *
     * If no island was found, this method returns null.
     * @param destination either a number or a name of the island (case sensitive).
     * @return a valid island from  the collection or null in case no island was found.
     */
    private Island confirmDestination(String destination) {
        if (answerAsNumber(destination) >= 0) {                                     /* User provided a number */
            for (int i = 0; i < archipelago.getArchipelago().size(); i++) {
                if (i + 1 == answerAsNumber(destination)) {
                    return archipelago.getArchipelago().get(i);
                }
            }
        } else {                                                                    /* User provided text */
            return archipelago.getIslandFromName(destination);
        }
        return null;
    }

    /**
     * Used when loading a ticket.
     * Prints out the information of the ticket back to the user. Simulating a machine verifying the ticket.
     */
    private void handleTicket() {
        Ticket ticket = io.loadTicketFileFromBin();
        if(ticket != null) {                                                                                                            //ticket exists.

            if (!customerName.toLowerCase().equals(ticket.getCustomerName().toLowerCase())) {                                           //matches the name in the ticket, with the name provided at the beginning of the program.
                System.out.println("We seem to have a problem with the ticket.");
                System.out.println("This ticket was issued to " + ticket.getCustomerName() + ".");
                System.out.println("However, your name is: " + ticket.getCustomerName());
                System.out.println("Tickets are only valid for whomever it was originally issued.");
                System.out.println("Please provide your own ticket. If you do not have a valid ticket, you can get one here.");
                questionFlow();                                                                                                         //in case user tries to use someone else's ticket, the program informs the user of this and restarts the menu flow.
            } else {
                System.out.println("Your ticket has been accepted:\n");             /* ticket accepted */
                System.out.println(getTicketInfo(ticket));
                System.out.println("Please, enjoy the ride.");
            }
        } else {
            questionFlow();                                                                                                            //if ticket is null -- restart (this can happen if the user closes the Open dialog window before selecting a file).
        }
    }

    /**
     * Gets the data from the ticket and puts it into a string.
     *
     * @param ticket the ticket that needs to be read.
     * @return A string with the data from the ticket.
     */
    private String getTicketInfo(Ticket ticket) {
        String createTime = ticket.getCreationTime();
        String name = ticket.getCustomerName();
        String destination = ticket.getTo().getName();
        String departure = ticket.getFrom().getName();
        String price = ticket.getPrice();

        return "This ticket was issued at " + createTime + ".\n" +
               "It was issued to: " + name + ".\n" +
               "From: " + departure + "\n" +
               "To: " + destination + "\n" +
               "Price: " + price + " DKK.";
    }

    /**
     * Uses {@printf} to print out a list of islands aligned properly.
     */
    private void prettyPrintIslands() {
        for (int i = 0; i < archipelago.getArchipelago().size(); i++) {
            if(!here.getName().equals(archipelago.getArchipelago().get(i).getName())){
                System.out.printf("%2d. %s%n", i + 1, archipelago.getArchipelago().get(i).getName());
            }
        }
    }

    /**
     * Creates a new ticket, with the info given from the user.
     * @return a Ticket object with the info, or null if the desired destination could not be confirmed to be valid.
     */
    private Ticket createTicket() {
        if(confirmDestination(customerDestination)!= null){
            return new Ticket(customerName, confirmDestination(customerDestination), here);
        } else {
            System.out.println("Island is not in our charter.");
        }
        return null;
    }

    /**
     * Gets the current system date, used for data in ticket.
     * @return current date and time as a string.
     */
    private String getDateAsString(){
        long millis = System.currentTimeMillis();
        return new Date(millis).toString();
    }
}

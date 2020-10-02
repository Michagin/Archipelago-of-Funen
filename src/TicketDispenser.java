import java.util.Scanner;
import java.util.regex.Pattern;

public class TicketDispenser {
    private String greetings = "Where would you like to go today?";
    Scanner input;
    String regex = "";
    Pattern answerPattern;


    public TicketDispenser(){
        input = new Scanner(System.in);
        answerPattern = Pattern.compile(regex);

        System.out.println(greetings);


    }

    private Ticket dispenseTicket(Island to, Island from){
        return new Ticket(to, from);
    }
}

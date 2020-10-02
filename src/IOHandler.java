import java.io.*;

public class IOHandler {

    Ticket ticket;
    File out;

    public IOHandler(Ticket ticket){
        this.ticket = ticket;
    }

    private String getOS(){
        return System.getProperty("os.name");
    }

    public void saveToDisk(String filename) throws IOException {

        if(!getOS().equals("Windows")){
            filename += ".ticket";
        }

        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
        oos.writeObject(ticket);
        oos.close();

    }
}

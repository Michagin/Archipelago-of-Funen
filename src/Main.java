public class Main {
    public static void main(String[] args) {
        Archipelago archipelago = new Archipelago();
        new Clerk(archipelago, new IOHandler());
    }
}

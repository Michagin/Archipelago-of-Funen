public class Main {
    public static void main(String[] args) {
        Archipelago archipelago = new Archipelago();
        Clerk clerk = new Clerk(new Customer(archipelago.getRandomIsland()), archipelago, new IOHandler());
    }
}

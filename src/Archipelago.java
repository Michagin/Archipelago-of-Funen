import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Archipelago {

    ArrayList<Island> archipelago;

    public Archipelago(){
        archipelago = new ArrayList<Island>();
        archipelago.addAll(Arrays.asList(
                new Island("Avernakø"),
                new Island("Bjørnø"),
                new Island("Bågø"),
                new Island("Drejø"),
                new Island("Hjortø"),
                new Island("Langeland"),
                new Island("Lyø"),
                new Island("Skarø"),
                new Island("Strynø"),
                new Island("Thurø"),
                new Island("Tåsinge"),
                new Island("Ærø")
                ));
    }

    public ArrayList<Island> getArchipelago(){
        return archipelago;
    }

}

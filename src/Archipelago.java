import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Archipelago {

    List<Island> archipelago;

    private List<Island> makeArchipelago(){

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

        return archipelago;
    }

}

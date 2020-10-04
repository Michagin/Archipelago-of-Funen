import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Archipelago {

    ArrayList<Island> archipelago;
    Random random = new Random();

    /**
     * This constructor creates a collection of islands as an {@ArrayList}.
     */
    public Archipelago(){
        archipelago = new ArrayList<>();
        archipelago.addAll(Arrays.asList(
                new Island("Avernakø", "Tag til Avernakø og oplev øens fantastiske natur. Tag fiskestangen med og se, om du kan være heldig at fange en fisk eller to."),
                new Island("Bjørnø", "Gå hele Bjørnø rundt på et par timer eller bliv hængende lidt længere, hvis du vil helt tæt på naturen. Øen er nemlig perfekt til vandreture."),
                new Island("Bågø", "På Bågø kan du opleve et rigt fugleliv og smuk natur. Du kan gå en tur på en af de fastlagte ruter eller gå dine egne veje"),
                new Island("Drejø", "Drejø er hjemsted for de smukke hvide dådyr. Øen byder på smuk natur, naturskønne vandrestiger, hyggelig landsbystemning og gode fiskemuligheder."),
                new Island("Hjortø", "Tag til Hjortø, hvis du vil have unikke naturoplevelser og bare vil helt ned i gear. Der er ingen butikker eller spisesteder på øen. Det er primitivt, men det er netop dette, der er charmen."),
                new Island("Langeland", "Langeland byder på mange spændende naturoplevelser, store som små. Hvis du er på jagt efter nye, spændende naturoplevelser, er det helt oplagt at besøge denne perle i det Sydfynske Øhav."),
                new Island("Lyø", "Tag en spadseretur rundt i ægte landsbyidyl, besøg Klokkestenen og fang en fisk til aftensmaden. Lyø byder på mange spændende oplevelser."),
                new Island("Skarø", "Kom helt tæt på naturen, når du overnatter i et arkitekttegnet shelter i den smukke natur på Skarø."),
                new Island("Strynø", "Oplev fællesskabet i øsamfundet på Strynø. Tag cyklen rundt eller spænd vandreskoene og udforsk den lille ø midt i det Sydfynske Øhav."),
                new Island("Thurø", "Øst for Tåsinge og syd for Svendborg ligger en lille ø. Thurø er blot 7,5 km2 stor, men gemmer alligevel på nogle fine og unikke naturoplevelser."),
                new Island("Tåsinge", "Tåsinge er en lille, charmerende ø i det Sydfynske Øhav. Måske har du allerede besøgt øen på vej til Langeland. Tåsinge har meget at byde på, så hvorfor ikke gøre stop på den lille ø?"),
                new Island("Ærø", "Naturen på Ærø har et nyt ansigt hver dag året rundt: Blide strandlinjer mod det Sydfynske Øhav - og vilde og forrevne kyststrækninger mod Østersøen med bølgende bakker, de inddæmmede nor og den imponerende flora. Ærøs natur er kort sagt unik.")
                ));
    }

    public ArrayList<Island> getArchipelago(){
        return archipelago;
    }

    /**
     * Goes through the collection and returns an island, if matched with the name provided.
     * @param name the name of the island to be searched for.
     * @return If island is found, the island is returns, else null is returned.
     */
    public Island getIslandFromName(String name){
        for(Island island : archipelago){
            if(island.getName().equals(name)){
                return island;
            }
        }
        return null;
    }

    /**
     * Gets a random island from the collection of islands.
     * @return a random island-object.
     */
    public Island getRandomIsland(){
        return archipelago.get(random.nextInt(archipelago.size()));
    }

    /**
     * This methode generates a random floating point number and rounds it up to two decimal points.
     * The use of {@DecimalFormat} converts the floating point number into a string.
     * @return A float number from 1 - 100 as a string, formatted with only two decimal points.
     */
    public String makePrice(){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(random.nextFloat()*100);
    }
}

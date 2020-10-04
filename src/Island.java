import java.io.Serializable;

public class Island implements Serializable {           //Island must implement the Serializable interface to tell the compiler that objects from this class are going to be saved to a file.
    private static final long serialVersionUID = 1337L; //unique ID used for serialization

    private String name;
    private String description;

    public Island(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

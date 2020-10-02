import java.io.Serializable;

public class Island implements Serializable {
    final transient int serialVersionUID = 1337;

    private String name;

    public Island(String name) {
        this.name = name;
    }
}

import java.io.Serializable;

public class Island implements Serializable {
    final transient int serialVersionUID = 1337;

    private String name;
    private String info;
    private String description;

    public Island(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public String getDescription() {
        return description;
    }
}

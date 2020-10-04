import java.io.Serializable;

public class Island implements Serializable {
    private static final long serialVersionUID = 1337L;

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

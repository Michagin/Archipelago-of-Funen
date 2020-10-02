import java.io.Serializable;

public class Island implements Serializable {
    final transient int serialVersionUID = 1337;

    private String name;
    private String info;

    public Island(String name) {
        this.name = name;
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
}

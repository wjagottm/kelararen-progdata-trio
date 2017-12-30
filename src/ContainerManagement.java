import java.awt.*;
import java.util.HashMap;

public class ContainerManagement {

    private HashMap<String, Container> containers;
    private Container currentContainer;

    public ContainerManagement() {
        this.containers = new HashMap<String, Container>();
        this.currentContainer = null;
    }

    public void add(String key, Container container) {
        this.containers.put(key, container);
    }

    public Container get(String key) {
        if(this.containers.containsKey(key)) {
            return this.containers.get(key);
        } else {
            return null;
        }
    }

    public void placeCurrentContainer(Container container) {
        this.currentContainer = container;
    }

    public Container grabCurrentContainer() {
        return this.currentContainer;
    }
}

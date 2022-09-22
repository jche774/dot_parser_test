import org.jgrapht.nio.Attribute;

import java.util.Map;

public class TaskNode {
    String name;
    long duration;

    public TaskNode(String name, Map<String, Attribute> stringAttributeMap) {
        this.name = name;
        this.duration = Long.parseLong(stringAttributeMap.get("Weight").getValue());
    }

    @Override
    public String toString() {
        return name+" with duration of"+duration;
    }
}

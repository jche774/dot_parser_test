import org.jgrapht.nio.Attribute;

import java.util.Map;

public class TaskNode{
    private final String name;
    private final long duration;

    public TaskNode(String name, Map<String, Attribute> stringAttributeMap) {
        this.name = name;
        this.duration = Long.parseLong(stringAttributeMap.get("Weight").getValue());
    }

    public String getName() {
        return name;
    }

    public long getDuration() {
        return duration;
    }

    public String toDot(){
        return name+" [Weight="+duration+"]";
    }

    @Override
    public String toString() {
        return name+"("+duration+")";
    }
}

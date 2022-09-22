import org.jgrapht.nio.Attribute;

import java.util.Map;

/**
 * We might not need edges for
 */
public class TaskDependency{

    private TaskNode source;
    private TaskNode target;
    private long duration;

    public TaskDependency(Map<String, Attribute> stringAttributeMap) {
        super();
        this.duration = Long.parseLong(stringAttributeMap.get("Weight").getValue());
    }

    public void setSource(TaskNode source) {
        this.source = source;
    }

    public void setTarget(TaskNode target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "("+source+"->"+target+")("+duration+")";
    }
}

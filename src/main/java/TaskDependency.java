import org.jgrapht.nio.Attribute;

import java.util.Map;

/**
 * We might not need edges for
 */
public class TaskDependency{

    private TaskNode source;
    private TaskNode target;
    private final long duration;

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

    public long getDuration() {
        return duration;
    }

    public String toDot(){
        return source.getName()+"->"+target.getName()+" [Weight="+duration+"]";
    }

    @Override
    public String toString() {
        return "("+source+"->"+target+")("+duration+")";
    }
}

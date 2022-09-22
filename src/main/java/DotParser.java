import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.nio.dot.DOTImporter;

import java.io.FileReader;
import java.io.IOException;

public class DotParser {
    private static final String DOT_FILE = "./test.dot";

    public static void main(String[] args) throws IOException {
        System.out.println(parseFromDot(DOT_FILE));
    }

    public static Graph<TaskNode, TaskDependency> parseFromDot(String dotFile) throws IOException {
        FileReader fr = new FileReader(dotFile);
        DOTImporter<TaskNode, TaskDependency> importer = new DOTImporter<>();
        Graph<TaskNode, TaskDependency> g = new DefaultDirectedGraph<>(TaskDependency.class);

        importer.setVertexWithAttributesFactory(TaskNode::new);
        importer.setEdgeWithAttributesFactory(TaskDependency::new);
        importer.importGraph(g, fr);

        /* It's ugly but this is the only way I can come up with to retrieve source, target and the weight of the edge
         at the same time */
        g.edgeSet().forEach(e -> e.setSource(g.getEdgeSource(e)));
        g.edgeSet().forEach(e -> e.setTarget(g.getEdgeTarget(e)));

        fr.close();
        return g;
    }
}
